package com.zhlzzz.together.rank;

import java.util.Optional;

public interface RankService {

    Rank add(Long userId, String nickname, String area);

    Rank update(Long userId, RankParam rankParam);

    Optional<Rank> findByUserId(Long userId);
}
