package com.zhlzzz.together.user.user_relation;

import com.google.common.base.Strings;
import com.zhlzzz.together.data.Slice;
import com.zhlzzz.together.data.SliceIndicator;
import com.zhlzzz.together.data.Slices;
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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public Boolean addUserRelation(Long userId, Long toUserId) {
        Boolean isHas = userRelationRepository.findByUserIdAndToUserId(userId, toUserId).isPresent();
        if (isHas) {
            return true;
        } else {
            UserRelationEntity relationEntity = new UserRelationEntity();
            relationEntity.setUserId(userId);
            relationEntity.setToUserId(toUserId);
            relationEntity.setRelation(UserRelation.Relation.friend);
            relationEntity.setUpdateTime(LocalDateTime.now());
            UserRelationEntity relationEntity1 = new UserRelationEntity();
            relationEntity1.setUserId(toUserId);
            relationEntity1.setToUserId(userId);
            relationEntity1.setRelation(UserRelation.Relation.friend);
            relationEntity1.setUpdateTime(LocalDateTime.now());

            userRelationRepository.save(relationEntity);
            userRelationRepository.save(relationEntity1);
            return true;
        }
    }

    @Override
    public Boolean updateUserRelation(Long userId, Long toUserId, String remark, UserRelation.Relation relation) {
        UserRelationEntity userRelation = userRelationRepository.findByUserIdAndToUserId(userId, toUserId).orElseThrow(() -> new UserRelationNotFoundException());
        if (!Strings.isNullOrEmpty(remark)) {
            userRelation.setRemark(remark);
        }
        if (relation != null) {
            userRelation.setRelation(relation);
        }
        userRelation.setUpdateTime(LocalDateTime.now());

        userRelationRepository.save(userRelation);
        return true;
    }

    @Override
    public Optional<? extends UserRelation> getUserRelationByUserIdAndToUserId(Long userId, Long toUserId) {
        return userRelationRepository.findByUserIdAndToUserId(userId, toUserId);
    }

    @Override
    public List<? extends UserRelation> getUserRelationsByUserIdAndRelation(Long userId, UserRelation.Relation relation) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return userRelationRepository.findByUserIdAndRelation(user.getId(), relation);
    }

    @Override
    public Slice<? extends UserRelation, Integer> getUserRelationsByRelation(SliceIndicator<Integer> indicator, Long userId, UserRelation.Relation relation) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserRelationEntity> q = cb.createQuery(UserRelationEntity.class);
        Root<UserRelationEntity> m = q.from(UserRelationEntity.class);

        q.select(m).orderBy(cb.desc(m.get("updateTime")), cb.desc(m.get("id")));

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<UserRelationEntity> countM = countQuery.from(UserRelationEntity.class);

        List<Predicate> predicates = new ArrayList<>(5);
        countQuery.select(cb.count(countM)).where(cb.and(predicates.toArray(new Predicate[0])));

        Slice<UserRelationEntity, Integer> slice = Slices.of(em, q, indicator, countQuery);
        return slice.map(UserRelationEntity::toDto);
    }
}
