package com.zhlzzz.together.rank;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RankParam {

    private String nickname;

    private String area;

    private Double rating;

    private Double kd;
}
