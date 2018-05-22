package com.zhlzzz.together.controllers.article;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.zhlzzz.together.article.Article;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@ApiModel(description = "文章列表")
@JsonPropertyOrder({"id","title","img_url","introduction"})
@RequiredArgsConstructor
public class ArticleListView {

    @NonNull
    private final Article article;

    @ApiModelProperty(value = "文章ID", example = "1")
    public Long getId() { return article.getId(); }

    @ApiModelProperty(value = "文章头图", example = "http://www.baidu.com")
    public String getImgUrl() { return article.getImgUrl(); }

    @ApiModelProperty(value = "标题", example = "标题")
    public String getTitle() { return article.getTitle(); }

    @ApiModelProperty(value = "文章简介", example = "简介")
    public String getIntroduction() { return article.getIntroduction(); }

}
