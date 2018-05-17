package com.zhlzzz.together.controllers.article;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.zhlzzz.together.article.discuss.DiscussEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.NonNull;

import java.time.LocalDateTime;

@ApiModel(description = "评论")
@JsonPropertyOrder({"id","content","createTime","audit","toTop","articleId","userId"})
public class DiscussView {

    @NonNull
    private final DiscussEntity discussEntity;

    public  DiscussView(DiscussEntity discussEntity){
        this.discussEntity = discussEntity;
    }

    @ApiModelProperty(name = "评论ID",example = "1")
    public  Long getId(){
        return this.discussEntity.getId();
    }

    @ApiModelProperty(name="评论内容",example = "评论")
    public String getContent(){
        return this.discussEntity.getContent();
    }

    @ApiModelProperty(name="回复内容",example = "回复")
    public String getReplyContent(){
        return this.discussEntity.getReplyContent();
    }

    @ApiModelProperty(name = "评论时间",example = "2017-12-13T12:03:20+08:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'+08:00'", timezone = "GMT+0800")
    public LocalDateTime getCreateTime(){
        return this.discussEntity.getCreateTime();
    }

    @ApiModelProperty(name ="是否审核",example = "true")
    public Boolean getAudit(){
        return this.discussEntity.isAudit();
    }

    @ApiModelProperty(name ="是否置顶",example = "true")
    public Boolean getToTop(){
        return this.discussEntity.isToTop();
    }

    @ApiModelProperty(name ="文章id",example = "123")
    public Long getArticleId(){
        return this.discussEntity.getArticleId();
    }
    @ApiModelProperty(name ="评论用户id",example = "123")
    public Long getUserId(){
        return this.discussEntity.getUserId();
    }
}
