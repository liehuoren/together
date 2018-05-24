package com.zhlzzz.together.controllers.user;

import com.zhlzzz.together.controllers.ApiAuthentication;
import com.zhlzzz.together.controllers.ApiExceptions;
import com.zhlzzz.together.user.user_game_config.UserGameConfigEntity;
import com.zhlzzz.together.user.user_game_config.UserGameConfigParam;
import com.zhlzzz.together.user.user_game_config.UserGameConfigService;
import com.zhlzzz.together.user.user_match_config.UserMatchConfig;
import com.zhlzzz.together.user.user_match_config.UserMatchConfigParam;
import com.zhlzzz.together.utils.CollectionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(description = "用户游戏配置", tags = {"UserGameConfig"})
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserGameConfigController {

    private final UserGameConfigService userGameConfigService;

    @PutMapping(path = "/{userId:\\d+}/game-type/{gameTypeId:\\d+}/game-configs")
    @ApiOperation(value = "更新用户游戏配置表")
    @ResponseBody
    public UserGameConfigView updateUserGameConfig(@PathVariable Long userId, @PathVariable Integer gameTypeId, @RequestBody UserGameConfigParam userGameConfigParam, ApiAuthentication auth) {
        if (!auth.requireUserId().equals(userId)) {
            throw ApiExceptions.noPrivilege();
        }
        UserGameConfigEntity userGameConfigEntity = userGameConfigService.updateUserGameConfig(userId, gameTypeId, userGameConfigParam);
        return new UserGameConfigView(userGameConfigEntity,null);
    }

    @GetMapping(path = "/{userId:\\d+}/game-type/{gameTypeId:\\d+}/game-configs")
    @ApiOperation(value = "获取用户游戏配置表")
    @ResponseBody
    public UserGameConfigView getUserGameConfig(@PathVariable Long userId, @PathVariable Integer gameTypeId, ApiAuthentication auth) {
        if (!auth.requireUserId().equals(userId)) {
            throw ApiExceptions.noPrivilege();
        }
        UserGameConfigEntity userGameConfigEntity = userGameConfigService.getUserGameConfigByUserAndGameType(userId, gameTypeId).orElseThrow(() -> ApiExceptions.notFound("没有相关配置"));
        return new UserGameConfigView(userGameConfigEntity,null);
    }

    @PutMapping(path = "/{userId:\\d+}/game-type/{gameTypeId:\\d+}/game-match-configs")
    @ApiOperation(value = "更新用户游戏匹配配置表")
    @ResponseBody
    public List<UserMatchConfigView> updateUserMatchConfig(@PathVariable Long userId, @PathVariable Integer gameTypeId, @RequestBody List<UserMatchConfigParam> params, ApiAuthentication auth) {
        if (!auth.requireUserId().equals(userId)) {
            throw ApiExceptions.noPrivilege();
        }
        UserGameConfigEntity userGameConfigEntity = userGameConfigService.getUserGameConfigByUserAndGameType(userId, gameTypeId).orElseThrow(() -> ApiExceptions.notFound("没有相关配置"));
        List<? extends UserMatchConfig> userMatchConfigs = userGameConfigService.updateUserMatchConfig(userGameConfigEntity.getId(), params);
        return CollectionUtils.map(userMatchConfigs, (r) -> new UserMatchConfigView(r));
    }

    @GetMapping(path = "/{userId:\\d+}/game-type/{gameTypeId:\\d+}/game-match-configs")
    @ApiOperation(value = "获取用户游戏匹配配置表")
    @ResponseBody
    public List<UserMatchConfigView> getUserMatchConfig(@PathVariable Long userId, @PathVariable Integer gameTypeId, ApiAuthentication auth) {
        if (!auth.requireUserId().equals(userId)) {
            throw ApiExceptions.noPrivilege();
        }
        UserGameConfigEntity userGameConfigEntity = userGameConfigService.getUserGameConfigByUserAndGameType(userId, gameTypeId).orElseThrow(() -> ApiExceptions.notFound("没有相关配置"));
        List<? extends UserMatchConfig> userMatchConfigs = userGameConfigService.getUserMatchConfigByUserGameConfigId(userGameConfigEntity.getId());
        return CollectionUtils.map(userMatchConfigs, (r) ->  new UserMatchConfigView(r));
    }
}
