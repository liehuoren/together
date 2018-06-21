package com.zhlzzz.together.controllers.game;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.zhlzzz.together.game.GameType;
import com.zhlzzz.together.game.game_config.GameConfig;
import com.zhlzzz.together.user.user_game_config.UserGameConfigEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "游戏类别")
@JsonPropertyOrder({"id","hot","name","imgUrl","deleted","createTime","userGameConfigs","matchConfigs","userMatchConfigs"})
public class GameTypeView extends GameTypeBaseView {

    private final GameConfig gameConfig;
    private final UserGameConfigEntity userGameConfigEntity;
    private GameConfigView gameConfigView;
    private UserGameConfigView userGameConfigView;

    public GameTypeView(GameType gameType, Boolean isShow, GameConfig gameConfig, UserGameConfigEntity userGameConfigEntity) {
        super(gameType, isShow);
        this.gameConfig = gameConfig;
        this.userGameConfigEntity = userGameConfigEntity;

    }

    @ApiModelProperty(value = "用户游戏基本配置列表")
    public UserGameConfigView getUserGameConfig() {
        if (userGameConfigEntity == null) {
            return null;
        }
        if (userGameConfigView == null) {
            userGameConfigView = new UserGameConfigView(userGameConfigEntity);
        }
        return userGameConfigView;
    }

    @ApiModelProperty(value = "游戏基本配置项列表")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public GameConfigView getGameConfig() {
        if (gameConfig == null) {
            return null;
        }
        if (gameConfigView == null) {
            gameConfigView = new GameConfigView(gameConfig);
        }
        return gameConfigView;
    }

}
