package com.zhlzzz.together.system.feedback;

import java.util.Optional;
import java.util.Set;

public interface FeedbackService {

    FeedbackEntity saveFeedback(FeedbackParam aboutParam);
    Optional<? extends FeedbackEntity> findFeedbackParamById(Long id);
    Set<FeedbackEntity> findAll();
}
