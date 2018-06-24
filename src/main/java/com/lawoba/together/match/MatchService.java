package com.lawoba.together.match;

import com.lawoba.together.data.Slice;
import com.lawoba.together.data.SliceIndicator;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MatchService {

    Match addMatch(MatchParam matchParam);

    Boolean finish(Long id, Long roomId);

    Boolean closeMatch(Long id);

    Boolean delete(Long id);

    Optional<? extends Match> getCurrentMatchByUser(Long userId);

    Optional<? extends Match> getMatchByUserAndRoom(Long userId, Long roomId);

    Slice<? extends Match, Integer> getMatchs(SliceIndicator<Integer> indicator, Long userId);

    List<? extends Match> getMatchsInUserIds(Set<Long> userIds);

    List<? extends Match> getMatchsByUserIdsInAndEffective(Set<Long> userIds, Integer gameTypeId);

    List<? extends Match> getCurrentMatchListCondition(Integer gameTypeId, Integer memberNum, String otherItem);
}
