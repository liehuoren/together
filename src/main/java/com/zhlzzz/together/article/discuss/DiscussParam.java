package com.zhlzzz.together.article.discuss;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@ApiModel(description = "评论参数")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscussParam implements Serializable {

    @ApiModelProperty(value = "评论内容", example = "评论")
    protected String content;

    @ApiModelProperty(value = "是否审核", example = "false")
    protected boolean audit;

    @ApiModelProperty(value = "是否置顶", example = "false")
    protected boolean toTop;

//    @ApiModelProperty(value = "回复内容", example = "回复")
//    protected String replyContent;

}
