package com.zhlzzz.together.controllers.system;

<<<<<<< HEAD
import com.zhlzzz.together.controllers.ApiAuthentication;
import com.zhlzzz.together.controllers.ApiExceptions;
import com.zhlzzz.together.user.User;
import com.zhlzzz.together.user.UserService;
=======
import com.zhlzzz.together.controllers.ApiExceptions;
import com.zhlzzz.together.system.AboutEntity;
import com.zhlzzz.together.system.AboutParam;
import com.zhlzzz.together.system.AboutService;
>>>>>>> b863ce7c50dc68fc86f028f9ae4ad5178e9a4b99
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/system", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(description = "系统", tags = {"System"})
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SystemController {

    @Autowired
    private final AboutService aboutService;

    @PutMapping(path = "/about")
    @ApiOperation(value = "更新小程序介绍")
    @ResponseBody
    public AboutView updateAdvert(@Valid @RequestBody AboutParam aboutParam, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream().map((e)->e.toString()).collect(Collectors.joining(";\n"));
            throw ApiExceptions.badRequest(errors);
        }
        return new AboutView(aboutService.updateAbout(aboutParam));
    }

    @GetMapping(path = "/about")
    @ApiOperation(value = "获取小程序简介")
    @ResponseBody
<<<<<<< HEAD
    public AboutView getAboutCompany(ApiAuthentication auth) {
        if (auth.requireUserId() == null) {
            throw ApiExceptions.badRequest("无权限访问");
        }
        return new AboutView();
=======
    public AboutView getArticle() {
        AboutEntity aboutEntity = aboutService.findAbout().orElseThrow(() -> ApiExceptions.notFound("不存小程序简介。"));
        return new AboutView(aboutEntity);
>>>>>>> b863ce7c50dc68fc86f028f9ae4ad5178e9a4b99
    }
}
