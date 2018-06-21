package com.lawoba.together.controllers.game;

import com.lawoba.together.controllers.ApiAuthentication;
import com.lawoba.together.controllers.ApiExceptions;
import com.lawoba.together.game.GameConfigImpl;
import com.lawoba.together.game.GameType;
import com.lawoba.together.game.GameTypeService;
import com.lawoba.together.game.game_config.GameConfig;
import com.lawoba.together.game.game_config.GameConfigEntity;
import com.lawoba.together.game.game_config.GameConfigOptionEntity;
import com.lawoba.together.game.game_config.GameConfigService;
import com.lawoba.together.user.User;
import com.lawoba.together.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "/games/game-configs", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(description = "游戏配置", tags = {"Game"})
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GameConfigController {

    private final GameConfigService gameConfigService;
    private final GameTypeService gameTypeService;
    private final UserService userService;

    @PostMapping
    @ApiOperation(value = "新增游戏配置")
    @ResponseBody
    public GameConfigView createGameConfig(@RequestParam Integer gameTypeId, @RequestParam GameConfig.InputType inputType,
                                           @RequestParam String label, @RequestParam(required = false, defaultValue = "true")
                                                       boolean required, ApiAuthentication auth) {

        User admin = userService.getUserById(auth.requireUserId()).filter(u -> u.isAdmin()).orElse(null);
        if (admin == null) {
            throw ApiExceptions.noPrivilege();
        }

        GameType gameType = gameTypeService.getGameTypeById(gameTypeId).orElseThrow(()-> ApiExceptions.notFound("不存在此游戏类型"));
        GameConfigEntity gameConfig = gameConfigService.addGameConfig(gameType.getId(), inputType, label, required);

        return new GameConfigView(new GameConfigImpl(gameConfig, new ArrayList<>()));
    }

    @PutMapping(path = "/{id:\\d+}")
    @ApiOperation(value = "更新游戏配置")
    @ResponseBody
    public GameConfigView updateGameConfig(@PathVariable Long id, @RequestParam GameConfig.InputType inputType,
                                           @RequestParam String label, @RequestParam(required = false, defaultValue = "true")
                                                       boolean required, ApiAuthentication auth) {
        User admin = userService.getUserById(auth.requireUserId()).filter(u -> u.isAdmin()).orElse(null);
        if (admin == null) {
            throw ApiExceptions.noPrivilege();
        }
        GameConfigEntity gameConfig = gameConfigService.updateGameConfig(id,inputType,label,required);

        return new GameConfigView(new GameConfigImpl(gameConfig, new ArrayList<>()));
    }

    @DeleteMapping(path = "/{id:\\d+}")
    @ApiOperation(value = "删除游戏配置")
    @ResponseBody
    public void deleteGameConfig(@PathVariable Long id, ApiAuthentication auth) {
        User admin = userService.getUserById(auth.requireUserId()).filter(u -> u.isAdmin()).orElse(null);
        if (admin == null) {
            throw ApiExceptions.noPrivilege();
        }
        gameConfigService.deleteGameConfig(id);
    }

    @PostMapping(path = "/{id:\\d+}/options")
    @ApiOperation(value = "新增游戏配置选项")
    @ResponseBody
    public GameConfigView.Option createOption(@PathVariable Long id, @RequestParam String value, ApiAuthentication auth) {
        User admin = userService.getUserById(auth.requireUserId()).filter(u -> u.isAdmin()).orElse(null);
        if (admin == null) {
            throw ApiExceptions.noPrivilege();
        }
        GameConfigEntity gameConfig= gameConfigService.getGameConfigById(id).orElseThrow(()-> ApiExceptions.notFound("不存在此游戏配置"));
        GameConfig.Option option = gameConfigService.addOption(gameConfig.getId(), value);

        return new GameConfigView.Option(option);
    }

    @PutMapping(path = "/{configId:\\d+}/options/{id:\\d+}")
    @ApiOperation(value = "更新游戏配置选项")
    @ResponseBody
    public GameConfigView.Option updateOption(@PathVariable Long id, @RequestParam String value, ApiAuthentication auth) {
        User admin = userService.getUserById(auth.requireUserId()).filter(u -> u.isAdmin()).orElse(null);
        if (admin == null) {
            throw ApiExceptions.noPrivilege();
        }
        GameConfigOptionEntity gameConfigOption = gameConfigService.getGameConfigOptionById(id).orElseThrow(()-> ApiExceptions.notFound("不存在此游戏配置选项"));
        GameConfig.Option option = gameConfigService.updateOption(gameConfigOption.getId(), value);

        return new GameConfigView.Option(option);
    }

    @DeleteMapping(path = "/{configId:\\d+}/options/{id:\\d+}")
    @ApiOperation(value = "删除游戏配置选项")
    @ResponseBody
    public void deleteGameConfigOption(@PathVariable Long id, ApiAuthentication auth) {
        User admin = userService.getUserById(auth.requireUserId()).filter(u -> u.isAdmin()).orElse(null);
        if (admin == null) {
            throw ApiExceptions.noPrivilege();
        }
        gameConfigService.deleteOption(id);
    }



}
