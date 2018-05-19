package com.zhlzzz.together.system.feedback;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@ApiModel(description = "反馈意见参数")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackParam implements Serializable {

    @ApiModelProperty(value = "用户id",example = "1")
    protected Long userId;

    @ApiModelProperty(value = "用户邮箱",example = "1234567890@qq.com")
    protected String email;

    @ApiModelProperty(value = "反馈意见", example = "正文正文正文")
    protected String content;
}
