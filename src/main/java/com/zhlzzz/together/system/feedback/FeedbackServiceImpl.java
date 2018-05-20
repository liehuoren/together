package com.zhlzzz.together.system.feedback;

import com.zhlzzz.together.user.User;
import com.zhlzzz.together.user.UserNotFoundException;
import com.zhlzzz.together.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;

    @Override
    public Optional<? extends FeedbackEntity> findFeedbackParamById(Long id) {
        return feedbackRepository.findById(id);
    }

    @Override
    public FeedbackEntity saveFeedback(FeedbackParam feedbackParam) {
        User user = userRepository.findById(feedbackParam.getUserId()).orElseThrow(()->new UserNotFoundException(feedbackParam.getUserId()));
        FeedbackEntity feedbackEntity = new FeedbackEntity();
        if (StringUtils.isNotEmpty(feedbackParam.getContent())) {
            feedbackEntity.setContent(feedbackParam.getContent());
        }
        if (StringUtils.isNotEmpty(feedbackParam.getEmail())) {
            feedbackEntity.setEmail(feedbackParam.getEmail());
        }
        feedbackEntity.setUserId(user.getId());
        feedbackEntity.setCreateTime(LocalDateTime.now());
        return feedbackRepository.save(feedbackEntity);
    }

    @Override
    public Set<FeedbackEntity> findAll() {
        return feedbackRepository.findAll();
    }
}
