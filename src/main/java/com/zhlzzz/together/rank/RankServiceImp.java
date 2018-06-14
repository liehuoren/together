package com.zhlzzz.together.rank;

import com.google.common.base.Strings;
import com.zhlzzz.together.rank.player.PlayerEntity;
import com.zhlzzz.together.rank.player.PlayerNotFoundException;
import com.zhlzzz.together.rank.player.PlayerRepository;
import com.zhlzzz.together.rank.player.player_season.PlayerSeasonEntity;
import com.zhlzzz.together.rank.player.player_season.PlayerSeasonNotFoundException;
import com.zhlzzz.together.rank.player.player_season.PlayerSeasonRepository;
import com.zhlzzz.together.user.user_relation.UserRelation;
import com.zhlzzz.together.user.user_relation.UserRelationRepository;
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
        if (Strings.isNullOrEmpty(nickname)) {
            rank.setNickname(nickname);
        }
        if (Strings.isNullOrEmpty(area)) {
            rank.setArea(area);
        }
        return rankRepository.save(rank);
    }

    @Override
    public Rank update(Long userId, RankParam rankParam) {
        Rank rank = rankRepository.findByUserId(userId).orElse(null);
        return null;
    }

    @Override
    public Optional<Rank> findByUserId(Long userId) {
        return rankRepository.findByUserId(userId);
    }
}
