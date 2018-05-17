package com.zhlzzz.together.controllers.article;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.zhlzzz.together.article.reply.ReplyEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.NonNull;

import java.time.LocalDateTime;

@ApiModel(description = "评论回复")
@JsonPropertyOrder({"id","content","createTime","modifyTime","articleId","discussId"})
public class ReplyView {

    @NonNull
    private final ReplyEntity replyEntity;

    public ReplyView(ReplyEntity replyEntity){
        this.replyEntity = replyEntity;
    }

    @ApiModelProperty(name = "回复ID",example = "1")
    public  Long getId(){
        return this.replyEntity.getId();
    }

    @ApiModelProperty(name="回复内容",example = "回复回复")
    public String getContent(){
        return this.replyEntity.getContent();
    }

    @ApiModelProperty(name = "回复时间",example = "2017-12-13T12:03:20+08:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'+08:00'", timezone = "GMT+0800")
    public LocalDateTime getCreateTime(){
        return this.replyEntity.getCreateTime();
    }

    @ApiModelProperty(name ="文章id",example = "123")
    public Long getArticleId(){
        return this.replyEntity.getArticleId();
    }
    @ApiModelProperty(name ="用户id",example = "123")
    public Long getDiscussId(){
        return this.replyEntity.getDiscussId();
    }
}
