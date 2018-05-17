package com.zhlzzz.together.article.reply;

import java.util.Optional;
import java.util.Set;

public interface ReplyService {

    ReplyEntity addReply(Long articleId,Long discussId,ReplyParam replyParam);
    ReplyEntity updateReply(Long articleId,Long discussId,Long id, ReplyParam replyParam);
    Optional<? extends ReplyEntity> getReplyById(Long articleId,Long discussId,Long id);
    void deleteReply(Long articleId,Long discussId,Long id);
    Set<ReplyEntity> findByArticleIdAndDiscussId(Long articleId,Long discussId);
}
