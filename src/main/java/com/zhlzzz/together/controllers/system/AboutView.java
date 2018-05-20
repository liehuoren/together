package com.zhlzzz.together.controllers.system;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.zhlzzz.together.system.AboutEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.NonNull;

@ApiModel(description = "用户")
@JsonPropertyOrder({"logo","company","introduction"})
public class AboutView {

    @NonNull
    private final AboutEntity aboutEntity;

    public AboutView(AboutEntity aboutEntity) {
         this.aboutEntity = aboutEntity;
    }

    @ApiModelProperty(value = "logo", example = "http://zhlzzz.com/logo.png")
    public String getLogo() { return this.aboutEntity.getLogo(); }

    @ApiModelProperty(value = "公司名称", example = "组起")
    public String getCompany() { return this.aboutEntity.getCompany(); }

    @ApiModelProperty(value = "公司简介", example = "公司")
    public String getIntroduction() { return this.aboutEntity.getIntroduction(); }
}
