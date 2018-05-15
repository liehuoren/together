package com.zhlzzz.together.article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;

@ApiModel(description = "文章参数")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleParam implements Serializable {

    @ApiModelProperty(value = "文章标题", example = "标题")
    @Length(max = 50)
    protected String title;

    @ApiModelProperty(value = "文章作者", example = "zzz")
    @Length(max = 30)
    protected String author;

    @ApiModelProperty(value = "文章头图", example = "http://www.baidu.com")
    @URL
    protected String imgUrl;

    @ApiModelProperty(value = "文章正文", example = "正文正文正文")
    protected String content;
}
