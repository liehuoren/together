package com.zhlzzz.together.system.feedback;

import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.Set;

public interface FeedbackRepository extends Repository<FeedbackEntity, Long> {

    FeedbackEntity save(FeedbackEntity systemEntity);
    Optional<FeedbackEntity> findById(Long id);
    Set<FeedbackEntity> findAll();
}
