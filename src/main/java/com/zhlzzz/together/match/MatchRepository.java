package com.zhlzzz.together.match;

import org.springframework.data.repository.Repository;

public interface MatchRepository extends Repository<MatchEntity, Long> {
    MatchEntity save(MatchEntity matchEntity);

    
}
