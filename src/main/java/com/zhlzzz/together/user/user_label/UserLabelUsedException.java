package com.zhlzzz.together.user.user_label;

import lombok.Getter;

public class UserLabelUsedException extends RuntimeException {

    @Getter
    private String label;

    public UserLabelUsedException(String label, Throwable cause) {
        super(label + "已经存在", cause);
        this.label = label;
    }
}
