package com.zhlzzz.together.article.reply;

import lombok.Getter;

import javax.annotation.Nullable;

public class ReplyNotFoundException extends RuntimeException {

    @Getter
    private Long id;

    public ReplyNotFoundException(@Nullable Long id) {
        super(String.format("找不到指定的回复（id: %d）", id));
        this.id = id;
    }

}
