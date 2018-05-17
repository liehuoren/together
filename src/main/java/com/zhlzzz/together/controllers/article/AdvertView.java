package com.zhlzzz.together.controllers.article;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.zhlzzz.together.article.advert.AdvertEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.NonNull;

import java.time.LocalDateTime;

@ApiModel(description = "广告")
@JsonPropertyOrder({"id","advertUrl","createTime","modifyTime","articleId"})
public class AdvertView {

    @NonNull
    private final AdvertEntity advertEntity;

    public AdvertView(AdvertEntity advertEntity){
        this.advertEntity = advertEntity;
    }

    @ApiModelProperty(name = "广告ID",example = "1")
    public  Long getId(){
        return this.advertEntity.getId();
    }

    @ApiModelProperty(name="广告图片",example = "http://www.baidu.com")
    public String getContent(){
        return this.advertEntity.getAdvertUrl();
    }

    @ApiModelProperty(name = "创建时间",example = "2017-12-13T12:03:20+08:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'+08:00'", timezone = "GMT+0800")
    public LocalDateTime getCreateTime(){
        return this.advertEntity.getCreateTime();
    }

    @ApiModelProperty(name = "修改时间",example = "2017-12-13T12:03:20+08:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'+08:00'", timezone = "GMT+0800")
    public LocalDateTime getModifyTime(){
        return this.advertEntity.getModifyTime();
    }

    @ApiModelProperty(name ="文章id",example = "123")
    public Long getArticleId(){
        return this.advertEntity.getArticleId();
    }
}
