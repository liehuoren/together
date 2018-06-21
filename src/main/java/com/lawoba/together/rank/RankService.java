package com.zhlzzz.together.rank;

import java.util.List;
import java.util.Optional;

public interface RankService {

    Rank add(Long userId, String nickname, String area);

    Rank updateBasic(Long userId, String nickname, String area);

    Rank updateRank(Long userId, Double rating, Double kd);

    Optional<Rank> findByUserId(Long userId);

    List<Rank> getRanksByUserIds(List<Long> userIds);
}
