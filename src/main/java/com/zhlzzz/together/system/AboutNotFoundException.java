package com.zhlzzz.together.system;

public class AboutNotFoundException extends RuntimeException {

    public AboutNotFoundException() {
        super(String.format("找不到小程序介绍（id: %d）"));
    }

}
