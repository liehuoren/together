package com.zhlzzz.together.controllers.user;

import com.zhlzzz.together.controllers.ApiAuthentication;
import com.zhlzzz.together.controllers.ApiExceptions;
import com.zhlzzz.together.data.Slice;
import com.zhlzzz.together.data.SliceIndicator;
import com.zhlzzz.together.user.User;
import com.zhlzzz.together.user.UserService;
import com.zhlzzz.together.user.user_game_config.UserGameConfigEntity;
import com.zhlzzz.together.user.user_game_config.UserGameConfigService;
import com.zhlzzz.together.user.user_label.UserLabelEntity;
import com.zhlzzz.together.user.user_label.UserLabelService;
import com.zhlzzz.together.user.user_relation.UserRelation;
import com.zhlzzz.together.user.user_relation.UserRelationEntity;
import com.zhlzzz.together.user.user_relation.UserRelationService;
import com.zhlzzz.together.utils.CollectionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(description = "用户", tags = {"User"})
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;
    private final UserLabelService userLabelService;
    private final UserRelationService userRelationService;
    private final UserGameConfigService userGameConfigService;

    @GetMapping
    @ApiOperation(value = "用户列表")
    @ResponseBody
    public Slice<? extends UserView, Integer> getUserList(SliceIndicator<Integer> indicator, ApiAuthentication auth) {
        if (auth.requireUserId() != 1) {
            throw ApiExceptions.badRequest("无权限访问");
        }
        val users = userService.getUsers(indicator);
        return users.mapAll(items -> buildArticlesView(items));
    }

    private List<UserView> buildArticlesView(List<? extends User> users) {
        return CollectionUtils.map(users, (r) ->  new UserView(r,null) );
    }

    @GetMapping(path = "/{userId:\\d+}")
    @ApiOperation(value = "获取指定用户的信息")
    @ResponseBody
    public UserView getUserById(@PathVariable Long userId, ApiAuthentication auth) {
        if (auth.requireUserId() == null) {
            throw ApiExceptions.badRequest("无权限访问");
        }
        User user = userService.getUserById(userId).orElseThrow(() -> ApiExceptions.notFound("不存在此人信息。"));
        List<UserLabelEntity> userLabelEntity = userLabelService.getUserLabelsByUserId(user.getId());
        return new UserView(user, userLabelEntity);
    }

    @GetMapping(path = "/{userId:\\d+}/relations/{relation}")
    @ApiOperation(value = "获取用户关系列表（好友或黑名单）")
    @ResponseBody
    public Slice<? extends UserListView, Integer> getUserRelationById(@PathVariable Long userId,
                                                                  @PathVariable UserRelation.Relation relation,
                                                                  @RequestParam(required = false) Integer gameTypeId,
                                                                  SliceIndicator<Integer> indicator) {
        User user = userService.getUserById(userId).orElseThrow(() -> ApiExceptions.notFound("不存在此人信息。"));
        Slice<? extends UserRelation, Integer> userRelations = userRelationService.getUserRelationsByRelation(indicator, user.getId(), relation);

        return userRelations.mapAll(items -> buildUserViews(items, gameTypeId));
    }

    private List<UserListView> buildUserViews(List<? extends UserRelation> userRelations, Integer gameTypeId) {
        List<Long> ids = new ArrayList<>();
        List<Long> result = new ArrayList<>();
        Map<Long, UserGameConfigEntity> userGameConfigMap = new HashMap<>();
        for (UserRelation userRelation : userRelations) {
            ids.add(userRelation.getToUserId());
        }

        if (gameTypeId != null) {
            List<UserGameConfigEntity> userGameConfigEntityList = userGameConfigService.getUserGameConfigsByUserIds(ids, gameTypeId);
            for (UserGameConfigEntity userGameConfigEntity : userGameConfigEntityList) {
                result.add(userGameConfigEntity.getUserId());
                userGameConfigMap.put(userGameConfigEntity.getUserId(), userGameConfigEntity);
            }
            ids.retainAll(result);
        }

        return CollectionUtils.map(ids, (r) -> buildUserView(r, userGameConfigMap.get(r)));
    }

    private UserListView buildUserView(Long userId, UserGameConfigEntity userGameConfig) {
        User user = userService.getUserById(userId).orElse(null);
        List<UserLabelEntity> userLabels = userLabelService.getUserLabelsByUserId(userId);
        return new UserListView(user, userLabels, userGameConfig);
    }

    @PutMapping(path = "/{userId:\\d+}/relations")
    @ApiOperation(value = "更新好友关系（拉黑或取消拉黑或修改备注名或删除好友）")
    @ResponseBody
    public ResponseEntity<String> updateRelation(@PathVariable Long userId, @RequestParam Long toUserId,
                                                 @RequestParam(required = false) String remark,
                                                 @RequestParam UserRelation.Relation relation, ApiAuthentication auth) {
        if (!auth.requireUserId().equals(userId)) {
            throw ApiExceptions.noPrivilege();
        }
        User toUser = userService.getUserById(toUserId).orElseThrow(() -> ApiExceptions.notFound("没有相关用户"));
        requireNonNull(relation,"relation");
        userRelationService.updateUserRelation(userId, toUser.getId(), remark ,relation);
        if (relation.equals(UserRelation.Relation.nofriend)) {
            userService.reduceScore(toUser.getId(), 1);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private void requireNonNull(Object value, String name) {
        if (value == null) {
            throw ApiExceptions.missingParameter(name);
        }
    }

}
