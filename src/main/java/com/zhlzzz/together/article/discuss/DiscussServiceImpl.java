package com.zhlzzz.together.article.discuss;

import com.zhlzzz.together.user.User;
import com.zhlzzz.together.user.UserNotFoundException;
import com.zhlzzz.together.user.UserRepository;
import com.zhlzzz.together.user.user_label.UserLabelUsedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @_({@Autowired}))
public class DiscussServiceImpl implements DiscussService {

    @PersistenceContext
    private EntityManager em;
    private final TransactionTemplate tt;
    private final DiscussRepository discussRepository;
    private final UserRepository userRepository;

    private void setParameter(DiscussEntity discussEntity,DiscussParam discussParam){
        if (StringUtils.isNotBlank(discussParam.getContent()))
            discussEntity.setContent(discussParam.getContent());
            discussEntity.setAudit(discussParam.isAudit());
            discussEntity.setToTop(discussParam.isToTop());
    }

    @Override
    public DiscussEntity addDiscuss(Long articleId,Long userId,DiscussParam discussParam) {
        User user = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException(userId));
        DiscussEntity discussEntity = new DiscussEntity();
        setParameter(discussEntity,discussParam);
        discussEntity.setArticleId(articleId);
        discussEntity.setUserId(user.getId());
        discussEntity.setCreateTime(LocalDateTime.now());
        return discussRepository.save(discussEntity);
    }

    @Override
    public DiscussEntity updateDiscuss(Long id, DiscussParam discussParam) {
        DiscussEntity discussEntity = discussRepository.findById(id).orElseThrow(()->new DiscussNotFoundException(id));
        setParameter(discussEntity,discussParam);
        try {
            return discussRepository.save(discussEntity);
        }catch (DataIntegrityViolationException e){
            throw new UserLabelUsedException(discussEntity.isAudit()+","+discussEntity.isToTop(), e);
        }
    }

    @Override
    public Optional<? extends DiscussEntity> getDiscussById(Long id) {
        return discussRepository.findById(id);
    }

//    @Override
//    public Slice<? extends DiscussEntity, Integer> getDiscusses(SliceIndicator<Integer> indicator) {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<DiscussEntity> q = cb.createQuery(DiscussEntity.class);
//        Root<DiscussEntity> m = q.from(DiscussEntity.class);
//
//        q.select(m).orderBy(cb.desc(m.get("createTime")));
//
//        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
//        Root<DiscussEntity> countM = countQuery.from(DiscussEntity.class);
//
//        List<Predicate> predicates = new ArrayList<>(5);
//        countQuery.select(cb.count(countM)).where(cb.and(predicates.toArray(new Predicate[0])));
//
//        Slice<DiscussEntity, Integer> slice = Slices.of(em, q, indicator, countQuery);
//        return null;
//    }

    @Override
    public void deleteDiscuss(Long id) {
        DiscussEntity discussEntity = discussRepository.findById(id).orElseThrow(() -> new DiscussNotFoundException(id));
        tt.execute((s)-> {
            em.createQuery("DELETE FROM DiscussEntity u WHERE u.id = :id")
                    .setParameter("id", discussEntity.getId())
                    .executeUpdate();
            return true;
        });
    }

    @Override
    public Set<DiscussEntity> findByArticleId(Long articleId){
        return discussRepository.findByArticleId(articleId);
    }

    @Override
    public  Optional<DiscussEntity> findByIdAndArticleIdAndUserId(Long id,Long articleId,Long userId){
        return  discussRepository.findByIdAndArticleIdAndUserId(id,articleId,userId);
    }
}
