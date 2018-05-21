package com.zhlzzz.together.match;

import com.zhlzzz.together.data.Slice;
import com.zhlzzz.together.data.SliceIndicator;
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

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class MatchServiceImpl implements MatchService {

    @PersistenceContext
    private EntityManager em;
    private final TransactionTemplate tt;
    private final MatchRepository matchRepository;

    @Override
    public Match addMatch(Long userId, Integer gameTypeId, Long minute) {
        MatchEntity matchEntity = new MatchEntity();
        matchEntity.setUserId(userId);
        matchEntity.setGameTypeId(gameTypeId);
        matchEntity.setCreateTime(LocalDateTime.now());
        matchEntity.setExpiration(LocalDateTime.now().plusMinutes(minute));
        return matchRepository.save(matchEntity);
    }

    @Override
    public Match getCurrentMatchByUser(Long userId) {
        MatchEntity matchEntity = em.createQuery("SELECT m FROM MatchEntity m WHERE m.userId = :userId AND " +
                "m.finished = false AND m.deleted = false AND m.expiration > :currentTime ORDER BY m.createTime desc", MatchEntity.class)
                .setParameter("userId", userId)
                .setParameter("currentTime", LocalDateTime.now())
                .getSingleResult();
        return matchEntity;
    }

    @Override
    public Slice<? extends Match, Integer> getMatchs(SliceIndicator<Integer> indicator) {
        return null;
    }

    @Override
    public Boolean finish(Long id) {
        MatchEntity matchEntity = matchRepository.getById(id).orElseThrow(() -> new MatchNotFoundException(id));
        matchEntity.setFinished(true);
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
}
