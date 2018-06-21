package com.lawoba.together.system.feedback;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Override
    public Optional<FeedbackEntity> findFeedbackParamById(Long id) {
        return feedbackRepository.findById(id);
    }

    @Override
    public FeedbackEntity addFeedback(Long userId, FeedbackParam feedbackParam) {

        FeedbackEntity feedbackEntity = new FeedbackEntity();
        if (StringUtils.isNotEmpty(feedbackParam.getContent())) {
            feedbackEntity.setContent(feedbackParam.getContent());
        }
        if (StringUtils.isNotEmpty(feedbackParam.getEmail())) {
            feedbackEntity.setEmail(feedbackParam.getEmail());
        }
        feedbackEntity.setUserId(userId);
        feedbackEntity.setCreateTime(LocalDateTime.now());
        return feedbackRepository.save(feedbackEntity);
    }

    @Override
    public List<FeedbackEntity> findAll() {
        return feedbackRepository.findAll();
    }

    @Override
    public void finishedFeedback(Long id) {
        FeedbackEntity feedbackEntity = feedbackRepository.findById(id).orElseThrow(() -> new FeedbackNotFoundException(id));

        feedbackEntity.setFinished(true);
        feedbackRepository.save(feedbackEntity);
    }
}
