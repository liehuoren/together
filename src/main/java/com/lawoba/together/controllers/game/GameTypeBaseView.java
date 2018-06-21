package com.lawoba.together.controllers.game;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lawoba.together.game.GameType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@ApiModel(description = "游戏类别")
@JsonPropertyOrder({"id"})
@RequiredArgsConstructor
public class GameTypeBaseView {

    @NonNull
    private final GameType gameType;
    private final Boolean isShow;

    @ApiModelProperty(value = "游戏ID", example = "1")
    public Integer getId() { return gameType.getId(); }

    @ApiModelProperty(value = "游戏名称", example = "绝地求生")
    public String getName() { return gameType.getName(); }

    @ApiModelProperty(value = "背景图", example = "http://www.baidu.com")
    public String getImgUrl() { return gameType.getImgUrl(); }

    @ApiModelProperty(value = "游戏logo", example = "http://www.baidu.com")
    public String getLogo() { return gameType.getLogo(); }

    @ApiModelProperty(value = "游戏最大人数", example = "5")
    public Integer getMaxMember() { return gameType.getMaxMember(); }

    @ApiModelProperty(value = "是否标注", example = "true")
    public Boolean isHot() { return gameType.isHot(); }

    @ApiModelProperty(value = "是否删除", example = "true")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Boolean isDeleted() {
        if (isShow) {
            return gameType.isDeleted();
        } else {
            return null;
        }
    }

    @ApiModelProperty(value = "创建时间", example = "2017-12-13 12:03:20")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+0800")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public LocalDateTime getCreateTime() {
        if (isShow) {
            return gameType.getCreateTime();
        } else {
            return null;
        }
    }

}
