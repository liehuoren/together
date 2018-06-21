package com.lawoba.together.controllers.game;

import com.lawoba.together.controllers.ApiAuthentication;
import com.lawoba.together.controllers.ApiExceptions;
import com.lawoba.together.game.GameType;
import com.lawoba.together.game.GameTypeParam;
import com.lawoba.together.game.GameTypeService;
import com.lawoba.together.game.game_config.GameConfig;
import com.lawoba.together.user.User;
import com.lawoba.together.user.UserService;
import com.lawoba.together.user.user_game_config.UserGameConfigEntity;
import com.lawoba.together.user.user_game_config.UserGameConfigService;
import com.lawoba.together.utils.CollectionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/games/game-types", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(description = "游戏类别", tags = {"Game"})
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GameTypeController {

    private final GameTypeService gameTypeService;
    private final UserService userService;
    private final UserGameConfigService userGameConfigService;

    @PostMapping
    @ApiOperation(value = "新增游戏类别")
    @ResponseBody
    public GameTypeBaseView create(@RequestBody GameTypeParam gameTypeParam, ApiAuthentication auth, BindingResult result) {
        User admin = userService.getUserById(auth.requireUserId()).filter(u -> u.isAdmin()).orElse(null);
        if (admin == null) {
            throw ApiExceptions.noPrivilege();
        }
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream().map((e)->e.toString()).collect(Collectors.joining(";\n"));
            throw ApiExceptions.badRequest(errors);
        }
        requireNonNull(gameTypeParam.getName(), "name");
        requireNonNull(gameTypeParam.getImgUrl(), "imgUrl");
        requireNonNull(gameTypeParam.getLogo(), "logo");
        requireNonNull(gameTypeParam.getMaxMember(), "maxMember");
        return new GameTypeBaseView(gameTypeService.addGameType(gameTypeParam), true);
    }

    @PutMapping(path = "{id:\\d+}")
    @ApiOperation(value = "更新游戏类别")
    @ResponseBody
    public GameTypeBaseView update(@PathVariable Integer id, @RequestBody GameTypeParam gameTypeParam, ApiAuthentication auth, BindingResult result) {
        User admin = userService.getUserById(auth.requireUserId()).filter(u -> u.isAdmin()).orElse(null);
        if (admin == null) {
            throw ApiExceptions.noPrivilege();
        }
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream().map((e)->e.toString()).collect(Collectors.joining(";\n"));
            throw ApiExceptions.badRequest(errors);
        }
        GameType gameType = gameTypeService.getGameTypeById(id).orElseThrow(()-> ApiExceptions.notFound("不存在此游戏类型"));

        return new GameTypeBaseView(gameTypeService.updateGameType(gameType.getId(),gameTypeParam), true);
    }

    @GetMapping
    @ApiOperation(value = "获取游戏类别列表")
    @ResponseBody
    public List<GameTypeBaseView> getGameTypes(ApiAuthentication auth) {
        List<? extends GameType> gameTypes = gameTypeService.getAllGameTypes();
        Boolean isShow = userService.getUserById(auth.requireUserId()).filter(u -> u.isAdmin()).isPresent();

        return CollectionUtils.map(gameTypes, (r) -> new GameTypeBaseView(r, isShow));

    }

    @GetMapping(path = "/{id:\\d+}")
    @ApiOperation(value = "获取游戏分类详细配置信息")
    @ResponseBody
    public GameTypeView getGameType(@PathVariable Integer id, ApiAuthentication auth) {
        User admin = userService.getUserById(auth.requireUserId()).filter(u -> u.isAdmin()).orElse(null);
        if (admin == null) {
            throw ApiExceptions.noPrivilege();
        }
        GameType gameType = gameTypeService.getGameTypeById(id).orElseThrow(()-> ApiExceptions.notFound("不存在此游戏类型"));
        List<? extends GameConfig> gameConfigs = gameTypeService.getGameTypeConfigs(gameType.getId());
        GameConfig gameConfig = null;
        if (gameConfigs != null && gameConfigs.size() != 0) {
            gameConfig = gameConfigs.get(0);
        }
        return new GameTypeView(gameType, true,gameConfig,null);
    }

    @GetMapping(path = "/{id:\\d+}/config")
    @ApiOperation(value = "获取用户对应游戏类别的配置")
    @ResponseBody
    public GameTypeView getGameTypeConfigs(@PathVariable Integer id, ApiAuthentication auth) {
        GameType gameType = gameTypeService.getGameTypeById(id).orElseThrow(()-> ApiExceptions.notFound("不存在此游戏类型"));
        List<? extends GameConfig> gameConfigs = gameTypeService.getGameTypeConfigs(gameType.getId());
        GameConfig gameConfig = null;
        if (gameConfigs != null && gameConfigs.size() != 0) {
            gameConfig = gameConfigs.get(0);
        }
        UserGameConfigEntity userGameConfigEntity = userGameConfigService.getUserGameConfigByUserAndGameType(auth.requireUserId(), gameType.getId()).orElse(null);
        return new GameTypeView(gameType, true,gameConfig,userGameConfigEntity);
    }

    private void requireNonNull(Object value, String name) {
        if (value == null) {
            throw ApiExceptions.missingParameter(name);
        }
    }

    @DeleteMapping(path = "/{id:\\d+}")
    @ApiOperation(value = "删除对应游戏")
    @ResponseBody
    public void delete(@PathVariable Integer id, ApiAuthentication auth) {
        if (auth.requireUserId() != 1) {
            throw ApiExceptions.noPrivilege();
        }
        gameTypeService.delete(id);
    }

}
