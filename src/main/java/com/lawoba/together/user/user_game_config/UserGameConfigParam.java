package com.zhlzzz.together.user.user_game_config;

import com.zhlzzz.together.match.Match;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UserGameConfigParam implements Serializable {

    private static final long serialVersionUID = -7125399262820530354L;

    private String nickname;

    private String area;

    private String contact;

    private Integer memberNum;

    private Integer minute;

    private Match.Range matchRange;

    private String otherItem;
}
