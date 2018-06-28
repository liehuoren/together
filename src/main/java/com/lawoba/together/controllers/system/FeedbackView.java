package com.lawoba.together.controllers.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lawoba.together.system.feedback.FeedbackEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.NonNull;

import java.time.LocalDateTime;

@ApiModel(description = "反馈意见")
@JsonPropertyOrder({"id","userId","email","content"})
public class FeedbackView {

    @NonNull
    private final FeedbackEntity feedbackEntity;

    public FeedbackView(FeedbackEntity feedbackEntity) {
         this.feedbackEntity = feedbackEntity;
    }

    @ApiModelProperty(name = "反馈ID",example = "1")
    public  Long getId(){
        return feedbackEntity.getId();
    }

    @ApiModelProperty(value = "反馈用户id", example = "123")
    public Long getUserId() { return feedbackEntity.getUserId(); }

    @ApiModelProperty(value = "用户邮箱", example = "1234567890@qq.com")
    public String getEmail() { return feedbackEntity.getEmail(); }

    @ApiModelProperty(value = "反馈内容", example = "反馈反馈反馈")
    public String getContent() { return feedbackEntity.getContent(); }

    @ApiModelProperty(value = "创建时间", example = "2017-12-13 12:03:20")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+0800")
    public LocalDateTime getCreateTime() { return feedbackEntity.getCreateTime(); }

    @ApiModelProperty(value = "是否处理", example = "true")
    public Boolean isFinished() { return feedbackEntity.isFinished(); }
}
