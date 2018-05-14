package com.zhlzzz.together.controllers.system;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "用户")
@JsonPropertyOrder({"logo","company","content"})
public class AboutView {

    @ApiModelProperty(value = "logo", example = "http://zhlzzz.com/logo.png")
    public String getLogo() { return "http://zhlzzz.com/logo.png"; }

    @ApiModelProperty(value = "公司名称", example = "组起")
    public String getCompany() { return "组起"; }

    @ApiModelProperty(value = "公司简介", example = "公司")
    public String getContent() { return "公司"; }
}
