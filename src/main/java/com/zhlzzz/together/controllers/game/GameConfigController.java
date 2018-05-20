package com.zhlzzz.together.controllers.game;

import com.zhlzzz.together.controllers.ApiExceptions;
import com.zhlzzz.together.game.GameConfigImpl;
import com.zhlzzz.together.game.GameType;
import com.zhlzzz.together.game.GameTypeService;
import com.zhlzzz.together.game.game_config.GameConfig;
import com.zhlzzz.together.game.game_config.GameConfigEntity;
import com.zhlzzz.together.game.game_config.GameConfigOptionEntity;
import com.zhlzzz.together.game.game_config.GameConfigService;
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

    @PostMapping
    @ApiOperation(value = "新增游戏配置")
    @ResponseBody
    public GameConfigView createGameConfig(@RequestParam Integer gameTypeId, @RequestParam GameConfig.InputType inputType,
                                           @RequestParam String label, @RequestParam(required = false, defaultValue = "true")
                                                       boolean required) {
        GameType gameType = gameTypeService.getGameTypeById(gameTypeId).orElseThrow(()-> ApiExceptions.notFound("不存在此游戏类型"));

        GameConfigEntity gameConfig = gameConfigService.addGameConfig(gameTypeId, inputType, label, required);

        return new GameConfigView(new GameConfigImpl(gameConfig, new ArrayList<>()));
    }

    @PutMapping(path = "/{id:\\d+}")
    @ApiOperation(value = "更新游戏配置")
    @ResponseBody
    public GameConfigView updateGameConfig(@PathVariable Long id, @RequestParam GameConfig.InputType inputType,
                                           @RequestParam String label, @RequestParam(required = false, defaultValue = "true")
                                                       boolean required) {
        GameConfigEntity gameConfig = gameConfigService.updateGameConfig(id,inputType,label,required);

        return new GameConfigView(new GameConfigImpl(gameConfig, new ArrayList<>()));
    }

    @DeleteMapping(path = "/{id:\\d+}")
    @ApiOperation(value = "删除游戏配置")
    @ResponseBody
    public void deleteGameConfig(@PathVariable Long id) {
        gameConfigService.deleteGameConfig(id);
    }

    @PostMapping(path = "/id:\\d+/options")
    @ApiOperation(value = "新增游戏配置选项")
    @ResponseBody
    public GameConfigView.Option createOption(@PathVariable Long id, @RequestParam String value) {
        GameConfigEntity gameConfig= gameConfigService.getGameConfigById(id).orElseThrow(()-> ApiExceptions.notFound("不存在此游戏配置"));

        GameConfig.Option option = gameConfigService.addOption(gameConfig.getId(), value);

        return new GameConfigView.Option(option);
    }

    @PutMapping(path = "/configId:\\d+/options/id:\\d+")
    @ApiOperation(value = "新增游戏配置选项")
    @ResponseBody
    public GameConfigView.Option updateOption(@PathVariable Long id, @RequestParam String value) {
        GameConfigOptionEntity gameConfigOption = gameConfigService.getGameConfigOptionById(id).orElseThrow(()-> ApiExceptions.notFound("不存在此游戏配置选项"));
        GameConfig.Option option = gameConfigService.updateOption(gameConfigOption.getId(), value);

        return new GameConfigView.Option(option);
    }

    @DeleteMapping(path = "/configId:\\d+/options/id:\\d+")
    @ApiOperation(value = "删除游戏配置选项")
    @ResponseBody
    public void deleteGameConfigOption(@PathVariable Long id) {
        gameConfigService.deleteOption(id);
    }

}
