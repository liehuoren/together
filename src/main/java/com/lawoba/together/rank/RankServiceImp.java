package com.zhlzzz.together.rank;

import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class RankServiceImp implements RankService {

    private final RankRepository rankRepository;

    @Override
    public Rank add(Long userId, String nickname, String area) {
        Rank rank = new Rank();
        if (userId != null) {
            rank.setUserId(userId);
        }
        if (!Strings.isNullOrEmpty(nickname)) {
            rank.setNickname(nickname);
        }
        if (!Strings.isNullOrEmpty(area)) {
            rank.setArea(area);
        }
        rank.setRating(0.00);
        rank.setKd(0.00);
        return rankRepository.save(rank);
    }

    @Override
    public Rank updateBasic(Long userId, String nickname, String area) {
        Rank rank = rankRepository.findByUserId(userId).orElseThrow(() -> new RankNotFoundException());
        rank.setNickname(nickname);
        if (!Strings.isNullOrEmpty(area)) {
            rank.setArea(area);
        }

        return rankRepository.save(rank);
    }

    @Override
    public Rank updateRank(Long userId, Double rating, Double kd) {
        Rank rank = rankRepository.findByUserId(userId).orElseThrow(() -> new RankNotFoundException());
        rank.setRating(rating);
        rank.setKd(kd);
        return rankRepository.save(rank);
    }

    @Override
    public Optional<Rank> findByUserId(Long userId) {
        return rankRepository.findByUserId(userId);
    }

    @Override
    public List<Rank> getRanksByUserIds(List<Long> userIds) {
        return rankRepository.findAllByUserIdInOrderByRatingDesc(userIds);
    }
}
