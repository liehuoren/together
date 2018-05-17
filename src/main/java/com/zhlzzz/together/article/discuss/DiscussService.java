package com.zhlzzz.together.article.discuss;

import java.util.Optional;
import java.util.Set;

public interface DiscussService {

    DiscussEntity addDiscuss(Long articleId,DiscussParam discussParam);
    DiscussEntity updateDiscuss(Long id,DiscussParam discussParam);
    Optional<? extends  DiscussEntity > getDiscussById(Long id);
//    Slice<? extends DiscussEntity,Integer> getDiscusses(SliceIndicator<Integer> indicator);
    void deleteDiscuss(Long id);
    Set<DiscussEntity> findByArticleId(Long articleId);
    Optional<DiscussEntity> findByIdAndArticleIdAndUserId(Long id,Long articleId,Long userId);
}
