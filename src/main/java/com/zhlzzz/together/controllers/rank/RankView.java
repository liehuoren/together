package com.zhlzzz.together.controllers.rank;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.zhlzzz.together.rank.Rank;
import com.zhlzzz.together.user.User;
import com.zhlzzz.together.user.user_game_config.UserGameConfigEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@ApiModel(description = "排行榜")
@JsonPropertyOrder({"palyerId","shardId","palyerName","userId"})
@RequiredArgsConstructor
public class RankView {

    @NonNull
    private final Rank rankEntity;
    private final User user;
    private final UserGameConfigEntity userGameConfigEntity;

    @ApiModelProperty(name="用户ID",example = "123")
    public Long getUserId(){
        return this.rankEntity.getUserId();
    }

    public String getNickName() { return user.getNickName(); }

    public String getAvatarUrl() { return user.getAvatarUrl(); }

    public String getGameNickName() { return userGameConfigEntity.getNickname(); }

    @ApiModelProperty(name="Rating",example = "12.00")
    public Double getRating(){
        return this.rankEntity.getRating();
    }

    @ApiModelProperty(name="KD",example = "13.00")
    public Double getKd(){
        return this.rankEntity.getKd();
    }

}
