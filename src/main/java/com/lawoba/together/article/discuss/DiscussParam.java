package com.lawoba.together.article.discuss;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@ApiModel(description = "官方回复参数")
@Data
@Builder
public class DiscussParam implements Serializable {

    @ApiModelProperty(value = "是否审核", example = "false")
    protected Boolean audit;

    @ApiModelProperty(value = "是否置顶", example = "false")
    protected Boolean toTop;

    @ApiModelProperty(value = "官方回复内容", example = "评论")
    protected String replyContent;

}
