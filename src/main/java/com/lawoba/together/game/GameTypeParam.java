package com.lawoba.together.game;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;

@ApiModel(description = "文章参数")
@Data
@Builder
public class GameTypeParam implements Serializable {


    @NonNull
    private String name;

    @URL
    private String imgUrl;

    @URL
    private String logo;

    private Integer maxMember;

    private Boolean hot;

    private Boolean deleted;
}
