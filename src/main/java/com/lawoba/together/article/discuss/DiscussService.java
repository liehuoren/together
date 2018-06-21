package com.lawoba.together.article.discuss;

import com.lawoba.together.data.Slice;
import com.lawoba.together.data.SliceIndicator;

import java.util.Optional;

public interface DiscussService {

    Discuss addDiscuss(Long articleId, Long userId, String content);
    Discuss updateDiscuss(Long id, DiscussParam param);

    Optional<? extends  Discuss> getDiscussById(Long id);

    void deleteDiscuss(Long id);

    Slice<? extends Discuss, Integer> getDiscussesByCriteria(DiscussCriteria criteria, SliceIndicator<Integer> indicator);

}
