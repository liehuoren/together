package com.zhlzzz.together.rank;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface RankRepository extends Repository<Rank, Long> {
    Rank save(Rank rank);

    Optional<Rank> findByUserId(Long userId);
}
