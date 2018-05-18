package com.zhlzzz.together.controllers.game;

import com.zhlzzz.together.controllers.ApiExceptions;
import com.zhlzzz.together.game.GameType;
import com.zhlzzz.together.game.GameTypeService;
import com.zhlzzz.together.utils.CollectionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/games/game-types", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(description = "游戏类别", tags = {"Game"})
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GameTypeController {

    private final GameTypeService gameTypeService;

    @PostMapping
    @ApiOperation(value = "新增游戏类别")
    @ResponseBody
    public GameTypeView create(@RequestParam String name, @RequestParam String imgUrl, @RequestParam boolean hot) {
        requireNonNull(name, "name");
        return new GameTypeView(gameTypeService.addGameType(name,imgUrl,hot));
    }

    @PutMapping(path = "{id:\\d+}")
    @ApiOperation(value = "更新游戏类别")
    @ResponseBody
    public GameTypeView update(@PathVariable Integer id, @RequestParam String name, @RequestParam String imgUrl, @RequestParam boolean hot) {
        GameType gameType = gameTypeService.getGameTypeById(id).orElseThrow(()-> ApiExceptions.notFound("不存在此游戏类型"));
        return new GameTypeView(gameType);
    }

    @GetMapping
    @ApiOperation(value = "获取游戏类别列表")
    @ResponseBody
    public List<GameTypeView> getGameTypes() {
        List<? extends GameType> gameTypes = gameTypeService.getAllGameTypes();

        return CollectionUtils.map(gameTypes, (r) -> new GameTypeView(r));
    }

    @DeleteMapping(path = "{id:\\d+}")
    @ApiOperation(value = "删除游戏类别")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        GameType gameType = gameTypeService.getGameTypeById(id).orElseThrow(()-> ApiExceptions.notFound("不存在此游戏类型"));
        gameTypeService.delete(gameType.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void requireNonNull(Object value, String name) {
        if (value == null) {
            throw ApiExceptions.missingParameter(name);
        }
    }

}
