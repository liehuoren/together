package com.zhlzzz.together.article.discuss;

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
public class DiscussParam implements Serializable {

    @ApiModelProperty(value = "评论内容", example = "评论")
    protected String content;

    @ApiModelProperty(value = "是否审核", example = "0")
    protected int audit;//0-否 1-是

    @ApiModelProperty(value = "是否置顶", example = "0")
    protected int toTop;//0-否 1-是

    @ApiModelProperty(value = "用户标识", example = "0")
    protected Long userId;
}
