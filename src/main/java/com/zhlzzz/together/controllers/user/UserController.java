package com.zhlzzz.together.controllers.user;

import com.zhlzzz.together.controllers.ApiExceptions;
import com.zhlzzz.together.user.User;
import com.zhlzzz.together.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(description = "用户", tags = {"User"})
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    @GetMapping(path = "/{userId:\\d+}")
    @ApiOperation(value = "获取指定用户的信息")
    @ResponseBody
    public UserView getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId).orElseThrow(() -> ApiExceptions.notFound("不存在此人信息。"));
        return new UserView(user);
    }
}
