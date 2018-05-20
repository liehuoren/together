package com.zhlzzz.together.user.user_relation;

import com.google.common.base.Strings;
import com.zhlzzz.together.user.User;
import com.zhlzzz.together.user.UserNotFoundException;
import com.zhlzzz.together.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserRelationServiceImpl implements UserRelationService {

    @PersistenceContext
    private EntityManager em;
    private final TransactionTemplate tt;
    private final UserRelationRepository userRelationRepository;
    private final UserRepository userRepository;

    @Override
    public void updateUserRelation(Long userId, Long toUserId, String remark, UserRelation.Relation relation) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        User toUser = userRepository.findById(toUserId).orElseThrow(() -> new UserNotFoundException(toUserId));
        tt.execute((s)-> {
            em.createQuery("DELETE FROM UserRelationEntity v WHERE v.userId = :userId AND v.toUserId = :toUserId")
                    .setParameter("userId", user.getId())
                    .setParameter("toUserId", toUser.getId())
                    .executeUpdate();
            UserRelationEntity userRelationEntity = new UserRelationEntity();
            if (!Strings.isNullOrEmpty(remark)) {
                userRelationEntity.setRemark(remark);
            }
            if (relation != null) {
                userRelationEntity.setRelation(relation);
            } else {
                userRelationEntity.setRelation(UserRelation.Relation.friend);
            }
            userRelationEntity.setUpdateTime(LocalDateTime.now());
            return userRelationRepository.save(userRelationEntity);
        });

    }

    @Override
    public Optional<? extends UserRelation> getUserRelationByUserIdAndToUserId(Long userId, Long toUserId) {
        return userRelationRepository.findByUserIdAndToUserId(userId, toUserId);
    }

    @Override
    public List<? extends UserRelation> getUserRelationsByUserIdAndRelation(Long userId, UserRelation.Relation relation) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return userRelationRepository.findByUserIdAndRelation(userId, relation);
    }
}
