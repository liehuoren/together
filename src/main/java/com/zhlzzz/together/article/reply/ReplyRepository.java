package com.zhlzzz.together.article.reply;

import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.Set;

public interface ReplyRepository extends Repository<ReplyEntity, Long> {

    ReplyEntity save(ReplyEntity replyEntity);
    Optional<ReplyEntity> findById(Long id);
    Set<ReplyEntity> findByArticleIdAndDiscussId(Long articleId,Long discussId);
}
