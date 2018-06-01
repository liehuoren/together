package com.zhlzzz.together.match;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

@Value
@Builder
@ToString
public class MatchDto implements Match, Serializable {

    @NonNull
    private Long id;
    private Long userId;
    private Integer gameTypeId;
    private LocalDateTime createTime;
    private LocalDateTime expiration;
    private Boolean onlyFriend;
    private Boolean finished;
    private Boolean deleted;
}
