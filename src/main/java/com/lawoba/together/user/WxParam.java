package com.lawoba.together.user;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@ApiModel(description = "微信登录参数")
@Data
@Builder
public class WxParam implements Serializable {

    private static final long serialVersionUID = -3029077147248096315L;

    private String code;

    private String rawData;

    private String signature;

    private String encryptedData;

    private String iv;
}
