package com.lawoba.together.match;

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
    private String formId;
    private String name;
    private Integer memberNum;
    private Integer minute;
    private Range matchRange;
    private String otherItem;
    private Long roomId;
    private LocalDateTime createTime;
    private LocalDateTime expiration;
    private boolean finished;
    private boolean deleted;
    private boolean effective;
    private boolean closeDown;
}
