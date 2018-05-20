package com.zhlzzz.together.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;

@ApiModel(description = "小程序结束参数")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AboutParam implements Serializable {

    @ApiModelProperty(value = "公司名称", example = "组起")
    @Length(max = 50)
    protected String company;

    @ApiModelProperty(value = "logo图", example = "http://www.baidu.com")
    @URL
    protected String logo;

    @ApiModelProperty(value = "小程序结束", example = "正文正文正文")
    protected String introduction;
}
