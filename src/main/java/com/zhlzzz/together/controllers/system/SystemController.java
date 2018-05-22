package com.zhlzzz.together.controllers.system;


import com.zhlzzz.together.controllers.ApiAuthentication;
import com.zhlzzz.together.controllers.ApiExceptions;
import com.zhlzzz.together.system.AboutEntity;
import com.zhlzzz.together.system.AboutService;
import com.zhlzzz.together.user.User;
import com.zhlzzz.together.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/system", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(description = "系统", tags = {"System"})
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SystemController {

    private final AboutService aboutService;
    private final UserService userService;

    @PutMapping(path = "/about")
    @ApiOperation(value = "更新小程序介绍")
    @ResponseBody
    public AboutView updateAdvert(@RequestParam String company, @RequestParam String logo, @RequestParam String introduction, ApiAuthentication auth) {
        User admin = userService.getUserById(auth.requireUserId()).filter(u -> u.isAdmin()).orElse(null);
        if (admin == null) {
            throw ApiExceptions.noPrivilege();
        }
        return new AboutView(aboutService.updateAbout(company, logo, introduction));
    }

    @GetMapping(path = "/about")
    @ApiOperation(value = "获取小程序简介")
    @ResponseBody
    public AboutView getAboutCompany() {
        AboutEntity aboutEntity = aboutService.getAbout();
        return new AboutView(aboutEntity);
    }
}
