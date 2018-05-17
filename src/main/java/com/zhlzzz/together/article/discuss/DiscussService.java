package com.zhlzzz.together.article.discuss;

import java.util.Optional;
import java.util.Set;

public interface DiscussService {

    DiscussEntity addDiscuss(Long articleId, Long userId, DiscussParam discussParam);
    DiscussEntity addDiscuss(Long articleId, Long userId,Long discussId ,ReplyParam replyParam);
    DiscussEntity updateDiscuss(Long id,DiscussParam discussParam);
    DiscussEntity updateDiscuss(Long discussId,ReplyParam replyParam);
    Optional<? extends  DiscussEntity > getDiscussById(Long id);
//    Slice<? extends DiscussEntity,Integer> getDiscusses(SliceIndicator<Integer> indicator);
    void deleteDiscuss(Long id);
    Set<DiscussEntity> findByArticleId(Long articleId);
    Optional<DiscussEntity> findByIdAndArticleIdAndUserId(Long id,Long articleId,Long userId);
}
