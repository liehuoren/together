package com.zhlzzz.together.article.advert;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@ApiModel(description = "文章参数")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvertParam implements Serializable {

    @ApiModelProperty(value = "广告图片", example = "http://www.baidu.com")
    protected String advertUrl;

    @ApiModelProperty(value = "是否展示", example = "0")
    protected int isAvailable;//0-否 1-是
}
