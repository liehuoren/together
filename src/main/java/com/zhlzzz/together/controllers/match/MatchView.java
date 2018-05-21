package com.zhlzzz.together.controllers.match;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.zhlzzz.together.match.Match;
import io.swagger.annotations.ApiModel;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@ApiModel(description = "游戏匹配")
@JsonPropertyOrder({"id"})
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class MatchView {

    private final Match match;

    public Long getId() { return match.getId(); }

    public Long getUserId() { return match.getUserId(); }

    public Integer getGameTypeId() { return match.getGameTypeId(); }

    public boolean isFinished() { return match.isFinished(); }

    public boolean isDeleted() { return match.isDeleted(); }

    public LocalDateTime getCreateTime() { return match.getCreateTime(); }

    public LocalDateTime getExpiration() { return match.getExpiration(); }
}
