package com.lawoba.together.controllers.system;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lawoba.together.system.AboutEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "用户")
@JsonPropertyOrder({"logo","company","introduction"})
public class AboutView {

    private final AboutEntity aboutEntity;

    public AboutView(AboutEntity aboutEntity) {
         this.aboutEntity = aboutEntity;
    }

    @ApiModelProperty(value = "logo", example = "http://p6rwlbhj0.bkt.clouddn.com/image/together/logo.jpg")
    public String getLogo() { return aboutEntity.getLogo(); }

    @ApiModelProperty(value = "公司名称", example = "组起")
    public String getCompany() { return aboutEntity.getCompany(); }

    @ApiModelProperty(value = "公司简介", example = "公司")
    public String getIntroduction() { return aboutEntity.getIntroduction(); }
}
