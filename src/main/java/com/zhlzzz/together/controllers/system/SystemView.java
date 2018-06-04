package com.zhlzzz.together.controllers.system;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.zhlzzz.together.controllers.game.GameTypeBaseView;
import com.zhlzzz.together.controllers.match.MatchView;
import com.zhlzzz.together.game.GameType;
import com.zhlzzz.together.match.Match;
import com.zhlzzz.together.utils.CollectionUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ApiModel(description = "系统初始值")
@JsonPropertyOrder({})
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class SystemView {

    private Integer friendsNum;
    private Integer onlineFriendsNum;
    private Long roomId;
    private final Match match;
    private final List<? extends GameType> gameTypes;
    private MatchView matchView;
    private List<GameTypeBaseView> gameTypeViews;

    @ApiModelProperty(name = "用户好友数量",example = "1")
    public Integer getFriendsNum() { return  20; }

    @ApiModelProperty(name = "用户在线数量",example = "1")
    public Integer getOnlineFriendsNum() { return 10; }

    @ApiModelProperty(name = "房间号Id",example = "1")
    public Long getRoomId() { return null; }

    public MatchView getMatch() {

        if (match == null) {
            return null;
        }
        if (matchView == null) {
            matchView = new MatchView(match);
        }
        return matchView;
    }

    public List<GameTypeBaseView> getGameTypes() {
        if (gameTypes == null) {
            return null;
        }
        if (gameTypeViews == null) {
            gameTypeViews = CollectionUtils.map(gameTypes, (r) -> new GameTypeBaseView(r, true));
        }
        return gameTypeViews;
    }
}
