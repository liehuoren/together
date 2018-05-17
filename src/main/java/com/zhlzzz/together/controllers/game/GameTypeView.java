package com.zhlzzz.together.controllers.game;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.zhlzzz.together.game.GameType;
import io.swagger.annotations.ApiModel;
import lombok.NonNull;

import java.time.LocalDateTime;

@ApiModel(description = "游戏类别")
@JsonPropertyOrder({"id"})
public class GameTypeView {

    @NonNull
    private final GameType gameType;

    public GameTypeView(GameType gameType) {
        this.gameType = gameType;
    }

    public Integer getId() { return gameType.getId(); }

    public String getName() { return gameType.getName(); }

    public String getImgUrl() { return gameType.getImgUrl(); }

    public boolean isHot() { return gameType.isHot(); }

    public LocalDateTime getCreateTime() { return gameType.getCreateTime(); }
}
