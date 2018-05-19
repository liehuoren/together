package com.zhlzzz.together.controllers.system;

import com.zhlzzz.together.controllers.ApiAuthentication;
import com.zhlzzz.together.controllers.ApiExceptions;
import com.zhlzzz.together.user.User;
import com.zhlzzz.together.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/system", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(description = "系统", tags = {"System"})
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SystemController {

    @GetMapping(path = "/about")
    @ApiOperation(value = "小程序介绍")
    @ResponseBody
    public AboutView getAboutCompany(ApiAuthentication auth) {
        if (auth.requireUserId() == null) {
            throw ApiExceptions.badRequest("无权限访问");
        }
        return new AboutView();
    }
}
