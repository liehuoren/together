package com.zhlzzz.together.controllers.article;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

@ApiModel(description = "用户")
@JsonPropertyOrder({"id","title","img_url","author","create_time","content"})
public class ArticleView {

    @ApiModelProperty(value = "文章ID", example = "1")
    public Long getId() { return 1L; }

    @ApiModelProperty(value = "标题", example = "标题")
    public String getTitle() { return "高开低走，谈谈想吃鸡的十大窍门，保你成大神。"; }

    @ApiModelProperty(value = "作者", example = "Mr.zhang")
    public String getAuthor() { return "Mr.zhang"; }

    @ApiModelProperty(value = "文章头图", example = "http://www.baidu.com")
    public String getImgUrl() { return "http://zhlzzz.com/article.png"; }

    @ApiModelProperty(value = "创建时间", example = "2017-12-13T12:03:20+08:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'+08:00'", timezone = "GMT+0800")
    public LocalDateTime getCreateTime() {
        return LocalDateTime.now();
    }

    @ApiModelProperty(value = "文章正文", example = "正文正文")
    public String getContent() { return "正文正文"; }


}
