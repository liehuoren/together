package com.zhlzzz.together.match;

import com.zhlzzz.together.data.Slice;
import com.zhlzzz.together.data.SliceIndicator;

import java.util.List;
import java.util.Optional;

public interface MatchService {

    Match addMatch(Long userId, Integer gameTypeId, Long minute, String formId, Boolean onlyFriend);

    Boolean finish(Long id);

    Boolean delete(Long id);

    Optional<? extends Match> getCurrentMatchByUser(Long userId);

    Slice<? extends Match, Integer> getMatchs(SliceIndicator<Integer> indicator);
}
