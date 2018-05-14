package com.zhlzzz.together.controllers.user;

import com.zhlzzz.together.controllers.ApiExceptions;
import com.zhlzzz.together.user.user_label.UserLabelEntity;
import com.zhlzzz.together.user.user_label.UserLabelService;
import com.zhlzzz.together.utils.CollectionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(path = "/users/{userId:\\d+}/labels", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(description = "用户标签", tags = {"UserLabel"})
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserLabelController {

    private final UserLabelService userLabelService;

    @GetMapping
    @ApiOperation(value = "获取用户标签")
    @ResponseBody
    public Set<UserLabelView> updateLabel(@PathVariable Long userId) {
        Set<UserLabelEntity> userLabels = userLabelService.getUserLabelsByUserId(userId);

        return CollectionUtils.map(userLabels, (r) -> { return new UserLabelView(r); } );
    }

    @PostMapping
    @ApiOperation(value = "新建用户标签")
    @ResponseBody
    public UserLabelView addLabel(@RequestParam String label, @PathVariable Long userId) {
        UserLabelEntity userLabelEntity =  userLabelService.addUserLabel(label, userId);
        return new UserLabelView(userLabelEntity);
    }

    @PutMapping(path = "/{id:\\d+}")
    @ApiOperation(value = "更新用户标签")
    @ResponseBody
    public UserLabelView updateLabel(@PathVariable Long id, @RequestParam String label, @PathVariable Long userId) {
        UserLabelEntity userLabel =  userLabelService.getByIdAndUserId(id, userId).orElseThrow(() ->
                ApiExceptions.notFound("没有相关用户标签"));

        return new UserLabelView(userLabelService.updateUserLabel(userLabel.getId(), label));
    }

    @DeleteMapping(path = "/{id:\\d+}")
    @ApiOperation(value = "删除用户标签")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Long id, @RequestParam String label, @PathVariable Long userId) {
        UserLabelEntity userLabel =  userLabelService.getByIdAndUserId(id, userId).orElseThrow(() ->
                ApiExceptions.notFound("没有相关用户标签"));

        userLabelService.delete(userLabel.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
