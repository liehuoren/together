package com.lawoba.together.controllers.user;

import com.lawoba.together.controllers.ApiAuthentication;
import com.lawoba.together.controllers.ApiExceptions;
import com.lawoba.together.game.GameType;
import com.lawoba.together.game.GameTypeService;
import com.lawoba.together.rank.Rank;
import com.lawoba.together.rank.RankService;
import com.lawoba.together.user.user_game_config.UserGameConfigEntity;
import com.lawoba.together.user.user_game_config.UserGameConfigParam;
import com.lawoba.together.user.user_game_config.UserGameConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(description = "用户游戏配置", tags = {"UserGameConfig"})
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserGameConfigController {

    private final UserGameConfigService userGameConfigService;
    private final GameTypeService gameTypeService;
    private final RankService rankService;

    @PutMapping(path = "/{userId:\\d+}/game-type/{gameTypeId:\\d+}/game-configs")
    @ApiOperation(value = "更新用户游戏配置表")
    @ResponseBody
    public UserGameConfigView updateUserGameConfig(@PathVariable Long userId, @PathVariable Integer gameTypeId, @RequestBody UserGameConfigParam userGameConfigParam, ApiAuthentication auth) {
        if (!auth.requireUserId().equals(userId)) {
            throw ApiExceptions.noPrivilege();
        }
        GameType gameType = gameTypeService.getGameTypeById(gameTypeId).orElseThrow(()-> ApiExceptions.notFound("不存在此游戏类型"));
        UserGameConfigEntity userGameConfigEntity = userGameConfigService.updateUserGameConfig(userId, gameType.getId(), userGameConfigParam);
        if (gameType.getId() == 1) {
            Rank rank = rankService.findByUserId(userId).orElse(null);
            if (rank == null) {
                rankService.add(userId, userGameConfigEntity.getNickname(), userGameConfigParam.getArea());
            } else {
                rankService.updateBasic(userId, userGameConfigEntity.getNickname(), userGameConfigParam.getArea());
            }
        }

        return new UserGameConfigView(userGameConfigEntity);
    }

    @GetMapping(path = "/{userId:\\d+}/game-type/{gameTypeId:\\d+}/game-configs")
    @ApiOperation(value = "获取用户游戏配置表")
    @ResponseBody
    public UserGameConfigView getUserGameConfig(@PathVariable Long userId, @PathVariable Integer gameTypeId, ApiAuthentication auth) {
        if (!auth.requireUserId().equals(userId)) {
            throw ApiExceptions.noPrivilege();
        }
        GameType gameType = gameTypeService.getGameTypeById(gameTypeId).orElseThrow(()-> ApiExceptions.notFound("不存在此游戏类型"));
        UserGameConfigEntity userGameConfigEntity = userGameConfigService.getUserGameConfigByUserAndGameType(userId, gameType.getId()).orElseThrow(() -> ApiExceptions.notFound("暂无相关配置"));
        return new UserGameConfigView(userGameConfigEntity);
    }

}
