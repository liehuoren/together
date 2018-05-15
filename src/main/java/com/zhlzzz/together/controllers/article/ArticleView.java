package com.zhlzzz.together.controllers.article;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.zhlzzz.together.article.Article;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.NonNull;

import java.time.LocalDateTime;

@ApiModel(description = "文章")
@JsonPropertyOrder({"id","title","img_url","author","create_time","content"})
public class ArticleView {

    @NonNull
    private final Article article;

    public ArticleView(Article article) {
        this.article = article;
    }

    @ApiModelProperty(value = "文章ID", example = "1")
    public Long getId() { return article.getId(); }

    @ApiModelProperty(value = "标题", example = "标题")
    public String getTitle() { return article.getTitle(); }

    @ApiModelProperty(value = "作者", example = "Mr.zhang")
    public String getAuthor() { return article.getAuthor(); }

    @ApiModelProperty(value = "文章头图", example = "http://www.baidu.com")
    public String getImgUrl() { return article.getImgUrl(); }

    @ApiModelProperty(value = "创建时间", example = "2017-12-13T12:03:20+08:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'+08:00'", timezone = "GMT+0800")
    public LocalDateTime getCreateTime() {
        return article.getCreateTime();
    }

    @ApiModelProperty(value = "文章正文", example = "正文正文")
    public String getContent() { return article.getContent(); }


}
