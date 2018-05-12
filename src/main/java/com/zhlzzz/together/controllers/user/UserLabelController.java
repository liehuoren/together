package com.zhlzzz.together.controllers.user;

import com.zhlzzz.together.user.UserService;
import com.zhlzzz.together.user.user_label.UserLabelEntity;
import com.zhlzzz.together.user.user_label.UserLabelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users/label", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(description = "用户标签", tags = {"User"})
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserLabelController {

    private final UserLabelService userLabelService;

    @PostMapping
    @ApiOperation(value = "新建用户标签")
    @ResponseBody
    public UserLabelView addLabel(@RequestParam String label, @RequestParam Long userId) {
        UserLabelEntity userLabelEntity =  userLabelService.addUserLabel(label, userId);
        return new UserLabelView(userLabelEntity);
    }
}
