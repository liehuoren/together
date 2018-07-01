package com.lawoba.together.controllers.wx;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.lawoba.together.auth.password.UserPasswordService;
import com.lawoba.together.config.ConstantQiniu;
import com.lawoba.together.controllers.ApiAuthentication;
import com.lawoba.together.controllers.ApiExceptions;
import com.lawoba.together.qiniu.QiNiuService;
import com.lawoba.together.user.User;
import com.lawoba.together.user.UserParam;
import com.lawoba.together.user.UserService;
import com.lawoba.together.user.WxParam;
import com.lawoba.together.user.user_label.UserLabelEntity;
import com.lawoba.together.user.user_label.UserLabelService;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping(path = "/wx", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(description = "微信登录", tags = {"Wx"})
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WxAuthController {


    private final WxMaService wxMaService;
    private final UserService userService;
    private final UserLabelService userLabelService;
    private final UserPasswordService userPasswordService;
    private final ConstantQiniu constantQiniu;

    @PostMapping(path = "/login")
    @ApiOperation(value = "小程序登录前注册并获取用户信息")
    @ResponseBody
    public UserselfView login(@RequestBody WxParam wxParam) {
        try {
            WxMaJscode2SessionResult result = wxMaService.getUserService().getSessionInfo(wxParam.getCode());

            User user = userService.getUserByOpenId(result.getOpenid()).orElse(null);

            if (user != null) {
                List<UserLabelEntity> userLabelEntitys = userLabelService.getUserLabelsByUserId(user.getId());
                return new UserselfView(user, userLabelEntitys);
            } else {
                if (!wxMaService.getUserService().checkUserInfo(result.getSessionKey(), wxParam.getRawData(), wxParam.getSignature())) {
                    throw ApiExceptions.invalidParameter("rawData");
                }
                WxMaUserInfo userInfo = wxMaService.getUserService().getUserInfo(result.getSessionKey(), wxParam.getEncryptedData(), wxParam.getIv());
                user = addUser(userInfo);
                userPasswordService.updateUserPassword(user.getId(),user.getOpenId());

                String qRCode = getUserQRCode(user.getId());
                UserParam userParam = new UserParam();
                userParam.setQRCode(qRCode);
                userService.updateUser(user.getId(), userParam);

                return new UserselfView(user, null);
            }
        } catch (WxErrorException e) {
            throw ApiExceptions.invalidParameter("code");
        }

    }

    private User addUser(WxMaUserInfo userInfo) {
        UserParam userParam = new UserParam();
        if (!Strings.isNullOrEmpty(userInfo.getNickName())) {
            userParam.setNickName(userInfo.getNickName());
        }
        if (!Strings.isNullOrEmpty(userInfo.getAvatarUrl())) {
            userParam.setAvatarUrl(userInfo.getAvatarUrl());
        }
        if (!Strings.isNullOrEmpty(userInfo.getGender())) {
            userParam.setGender(Integer.parseInt(userInfo.getGender()));
        }
        if (!Strings.isNullOrEmpty(userInfo.getOpenId())) {
            userParam.setOpenId(userInfo.getOpenId());
        }
        if (!Strings.isNullOrEmpty(userInfo.getUnionId())) {
            userParam.setUnionId(userInfo.getUnionId());
        }
        userParam.setRole(User.Role.user);
        return userService.addUser(userParam);
    }

    private User updateUser(Long userId, WxMaUserInfo userInfo) {
        UserParam userParam = new UserParam();
        if (!Strings.isNullOrEmpty(userInfo.getNickName())) {
            userParam.setNickName(userInfo.getNickName());
        }
        if (!Strings.isNullOrEmpty(userInfo.getAvatarUrl())) {
            userParam.setAvatarUrl(userInfo.getAvatarUrl());
        }
        if (!Strings.isNullOrEmpty(userInfo.getGender())) {
            userParam.setGender(Integer.parseInt(userInfo.getGender()));
        }

        return userService.updateUser(userId, userParam);
    }

    private String getUserQRCode(Long userId) {
        try {
            File file = wxMaService.getQrcodeService().createWxCodeLimit(userId + "", "pages/add-friend/add-friend");
            String fileName = file.getName();
            String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
            String projectPath = new File("").getAbsolutePath();
            File dest = new File(projectPath + File.separator + "images" + File.separator + "qrcode" + File.separator + newFileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            file.renameTo(dest);
            return "https://api.lawoba.com/images/qrcode/" + newFileName;
        } catch (WxErrorException e) {
            throw ApiExceptions.invalidParameter("userId");
        } catch (Exception e) {
            throw ApiExceptions.badRequest("上传错误");
        }
    }


    @GetMapping(path = "/qrcode")
    @ApiOperation(value = "获取用户微信二维码")
    @ResponseBody
    public ResponseEntity<String> getCode(Long userId) {
        User user = userService.getUserById(userId).orElseThrow(() -> ApiExceptions.notFound("没有相关用户信息"));
        if (!Strings.isNullOrEmpty(user.getQRCode())) {
            return ResponseEntity.ok(user.getQRCode());
        }
        String qRCode = getUserQRCode(user.getId());
        UserParam userParam = new UserParam();
        userParam.setQRCode(qRCode);
        userService.updateUser(user.getId(), userParam);
        return ResponseEntity.ok(qRCode);
    }

    public DefaultPutRet upload(byte[] file, String key) throws Exception {
        Auth auth = Auth.create(constantQiniu.getAccessKey(), constantQiniu.getSecretKey());
        //第二种方式: 自动识别要上传的空间(bucket)的存储区域是华东、华北、华南。
        Zone z = Zone.autoZone();
        Configuration c = new Configuration(z);
        //创建上传对象
        UploadManager uploadManager = new UploadManager(c);
        Response res = uploadManager.put(file, key, getUpToken(auth, constantQiniu.getBucket()));
        //打印返回的信息
        System.out.println(res.bodyString());
        //解析上传成功的结果
        DefaultPutRet putRet = new Gson().fromJson(res.bodyString(), DefaultPutRet.class);
        return putRet;
    }

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken(Auth auth, String bucketname) {
        return auth.uploadToken(bucketname);
    }

    public static byte[] fileToBytes(String filePath) {
        byte[] buffer = null;
        File file = new File(filePath);

        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;

        try {
            fis = new FileInputStream(file);
            bos = new ByteArrayOutputStream();

            byte[] b = new byte[1024];

            int n;

            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }

            buffer = bos.toByteArray();
        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        } finally {
            try {
                if (null != bos) {
                    bos.close();
                }
            } catch (IOException ex) {

            } finally{
                try {
                    if(null!=fis){
                        fis.close();
                    }
                } catch (IOException ex) {

                }
            }
        }

        return buffer;
    }
}
