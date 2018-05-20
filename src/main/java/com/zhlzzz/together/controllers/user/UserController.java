package com.zhlzzz.together.controllers.user;

import com.zhlzzz.together.controllers.ApiAuthentication;
import com.zhlzzz.together.controllers.ApiExceptions;
import com.zhlzzz.together.user.User;
import com.zhlzzz.together.user.UserService;
import com.zhlzzz.together.user.user_relation.UserRelation;
import com.zhlzzz.together.user.user_relation.UserRelationEntity;
import com.zhlzzz.together.user.user_relation.UserRelationService;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(description = "用户", tags = {"User"})
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;
    private final UserRelationService userRelationService;

    @GetMapping(path = "/{userId:\\d+}")
    @ApiOperation(value = "获取指定用户的信息")
    @ResponseBody
    public UserView getUserById(@PathVariable Long userId, ApiAuthentication auth) {
        if (auth.requireUserId() == null) {
            throw ApiExceptions.badRequest("无权限访问");
        }
        User user = userService.getUserById(userId).orElseThrow(() -> ApiExceptions.notFound("不存在此人信息。"));
        return new UserView(user);
    }

    @GetMapping(path = "/{userId:\\d+}/relations/{relation:\\s+}")
    @ApiOperation(value = "获取用户关系列表（好友或黑名单）")
    @ResponseBody
    public List<UserView> getUserRelationById(@PathVariable Long userId, @PathVariable UserRelation.Relation relation) {
        User user = userService.getUserById(userId).orElseThrow(() -> ApiExceptions.notFound("不存在此人信息。"));
        List<? extends UserRelation> userRelations = userRelationService.getUserRelationsByUserIdAndRelation(user.getId(), relation);
        Set<Long> userIds = new HashSet<>();
        for (UserRelation userRelation : userRelations) {
            userIds.add(userRelation.getId());
        }
        List<? extends User> users = userService.getUsersByIds(userIds);
        return CollectionUtils.map(users, (u) -> { return new UserView(u); });
    }

    @PostMapping(path = "/{userId:\\d+}/relations/{toUserId:\\d+}")
    @ApiOperation(value = "更新关系")
    @ResponseBody
    public ResponseEntity<String> updateRelation(@PathVariable Long userId, @PathVariable Long toUserId, @RequestParam String remark, @RequestParam UserRelation.Relation relation) {

        userRelationService.updateUserRelation(userId, toUserId, remark ,relation);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
