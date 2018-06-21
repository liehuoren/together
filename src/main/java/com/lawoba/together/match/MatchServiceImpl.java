package com.lawoba.together.match;

import com.google.common.base.Strings;
import com.lawoba.together.data.Slice;
import com.lawoba.together.data.SliceIndicator;
import com.lawoba.together.data.Slices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class MatchServiceImpl implements MatchService {

    @PersistenceContext
    private EntityManager em;
    private final MatchRepository matchRepository;

    @Override
    public Match addMatch(MatchParam matchParam) {
        MatchEntity matchEntity = new MatchEntity();
        if (!Strings.isNullOrEmpty(matchParam.getName())) {
            matchEntity.setName(matchParam.getName());
        }
        if (matchParam.getUserId() != null) {
            matchEntity.setUserId(matchParam.getUserId());
        }
        if (matchParam.getGameTypeId() != null) {
            matchEntity.setGameTypeId(matchParam.getGameTypeId());
        }
        if (!Strings.isNullOrEmpty(matchParam.getFormId())) {
            matchEntity.setFormId(matchParam.getFormId());
        }
        if (matchParam.getMatchRange() != null) {
            matchEntity.setMatchRange(matchParam.getMatchRange());
        }
        if (matchParam.getMinute() != null) {
            matchEntity.setMinute(matchParam.getMinute());
            matchEntity.setExpiration(LocalDateTime.now().plusMinutes(matchParam.getMinute()));
        }
        if (matchParam.getMemberNum() != null) {
            matchEntity.setMemberNum(matchParam.getMemberNum());
        }
        if (!Strings.isNullOrEmpty(matchParam.getOtherItem())) {
            matchEntity.setOtherItem(matchParam.getOtherItem());
        }
        matchEntity.setCreateTime(LocalDateTime.now());

        return matchRepository.save(matchEntity);
    }

    @Override
    public Optional<? extends Match> getCurrentMatchByUser(Long userId) {

        return matchRepository.getFirstByUserIdOrderByCreateTimeDesc(userId);
    }

    @Override
    public Slice<? extends Match, Integer> getMatchs(SliceIndicator<Integer> indicator, Long userId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<MatchEntity> q = cb.createQuery(MatchEntity.class);
        Root<MatchEntity> m = q.from(MatchEntity.class);

        Predicate where = buildPredicate(cb, m, userId);
        q.select(m).where(where).orderBy(cb.desc(m.get("createTime")), cb.desc(m.get("id")));

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<MatchEntity> countM = countQuery.from(MatchEntity.class);

        countQuery.select(cb.count(countM)).where(buildPredicate(cb, countM, userId));

        Slice<MatchEntity, Integer> slice = Slices.of(em, q, indicator, countQuery);
        return slice.map(MatchEntity::toDto);
    }

    private Predicate buildPredicate(CriteriaBuilder cb, Root<MatchEntity> m, Long userId) {
        List<Predicate> predicates = new ArrayList<>(1);

        predicates.add(cb.equal(m.get("userId"), userId));
        predicates.add(cb.equal(m.get("finished"), true));

        return cb.and(predicates.toArray(new Predicate[0]));
    }

    @Override
    public List<? extends Match> getCurrentMatchListCondition(Integer gameTypeId, Integer memberNum, String otherItem) {
        List<MatchEntity> matchEntitys = em.createQuery("SELECT m FROM MatchEntity m WHERE m.memberNum = :memberNum AND " +
                "m.finished = false AND m.gameTypeId = :gameTypeId AND m.deleted = false AND m.expiration > :currentTime AND m.otherItem like :otherItem ORDER BY m.createTime desc", MatchEntity.class)
                .setParameter("memberNum", memberNum)
                .setParameter("gameTypeId", gameTypeId)
                .setParameter("currentTime", LocalDateTime.now())
                .setParameter("otherItem",  otherItem)
                .getResultList();
        return matchEntitys;
    }

    @Override
    public Boolean finish(Long id, Long roomId) {
        MatchEntity matchEntity = matchRepository.getById(id).orElseThrow(() -> new MatchNotFoundException(id));
        matchEntity.setFinished(true);
        matchEntity.setRoomId(roomId);
        matchRepository.save(matchEntity);
        return true;
    }

    @Override
    public Boolean closeMatch(Long id) {
        MatchEntity matchEntity = matchRepository.getById(id).orElseThrow(() -> new MatchNotFoundException(id));
        matchEntity.setCloseDown(true);
        matchRepository.save(matchEntity);
        return true;
    }

    @Override
    public Boolean delete(Long id) {
        MatchEntity matchEntity = matchRepository.getById(id).orElseThrow(() -> new MatchNotFoundException(id));
        matchEntity.setDeleted(true);
        matchRepository.save(matchEntity);
        return true;
    }

    @Override
    public List<? extends Match> getMatchsInUserIds(Set<Long> userIds) {
        List<MatchEntity> matchEntitys = em.createQuery("SELECT m FROM MatchEntity m WHERE m.userId in :userIds AND " +
                "m.finished = false AND m.deleted = false AND m.expiration > :currentTime ORDER BY m.createTime desc", MatchEntity.class)
                .setParameter("userIds", userIds)
                .setParameter("currentTime", LocalDateTime.now())
                .getResultList();
        return matchEntitys;
    }

    @Override
    public List<? extends Match> getMatchsByUserIdsInAndEffective(Set<Long> userIds, Integer gameTypeId) {
        List<MatchEntity> matchEntitys = em.createQuery("SELECT m FROM MatchEntity m WHERE m.userId in :userIds AND " +
                "m.finished = false AND m.gameTypeId = :gameTypeId AND m.deleted = false AND m.expiration > :currentTime ORDER BY m.createTime desc", MatchEntity.class)
                .setParameter("userIds", userIds)
                .setParameter("gameTypeId", gameTypeId)
                .setParameter("currentTime", LocalDateTime.now())
                .getResultList();
        return matchEntitys;
    }
}
