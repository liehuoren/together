package com.zhlzzz.together.article.discuss;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface Discuss extends Serializable {

    Long getId();
    String getContent();
    String getReplyContent();
    Boolean isAudit();
    Boolean isToTop();
    Long getArticleId();
    Long getUserId();
    LocalDateTime getCreateTime();
}
