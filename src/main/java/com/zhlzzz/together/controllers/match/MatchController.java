package com.zhlzzz.together.controllers.match;

import com.zhlzzz.together.chat_room.ChatRoomService;
import com.zhlzzz.together.controllers.ApiAuthentication;
import com.zhlzzz.together.controllers.ApiExceptions;
import com.zhlzzz.together.data.Slice;
import com.zhlzzz.together.data.SliceIndicator;
import com.zhlzzz.together.match.Match;
import com.zhlzzz.together.match.MatchService;
import com.zhlzzz.together.user.user_game_config.UserGameConfigEntity;
import com.zhlzzz.together.user.user_game_config.UserGameConfigService;
import com.zhlzzz.together.user.user_match_config.UserMatchConfig;
import com.zhlzzz.together.user.user_relation.UserRelation;
import com.zhlzzz.together.user.user_relation.UserRelationService;
import com.zhlzzz.together.utils.CollectionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import lombok.val;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/match", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(description = "匹配", tags = {"Match"})
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MatchController {

    private final MatchService matchService;
    private final UserGameConfigService userGameConfigService;
    private final ChatRoomService chatRoomService;
    private final UserRelationService userRelationService;

    @PostMapping
    @ApiOperation(value = "新建匹配")
    @ResponseBody
    public MatchView addMatch(@RequestParam Integer gameTypeId, @RequestParam Long minute, @RequestParam String formId, @RequestParam Boolean onlyFriend, ApiAuthentication auth) {
        UserGameConfigEntity userGameConfigEntity = userGameConfigService.getUserGameConfigByUserAndGameType(auth.requireUserId(), gameTypeId).orElseThrow(() -> ApiExceptions.missingParameter("config"));
        List<? extends UserMatchConfig> userMatchConfigs = userGameConfigService.getUserMatchConfigByUserGameConfigId(userGameConfigEntity.getId());
        if (userMatchConfigs.size() < 3) {
            throw ApiExceptions.missingParameter("config");
        }
        Match match = matchService.getCurrentMatchByUser(auth.requireUserId()).orElse(null);
        if (match == null) {
            match = matchService.addMatch(auth.requireUserId(), gameTypeId,minute,formId,onlyFriend);
        } else {
            throw ApiExceptions.badRequest("当前正在匹配中，无法新增匹配");
        }

        return new MatchView(match);
    }

    @DeleteMapping
    @ApiOperation(value = "取消匹配")
    public void deleteMatch(ApiAuthentication auth) {
        Match match = matchService.getCurrentMatchByUser(auth.requireUserId()).orElseThrow(() -> ApiExceptions.notFound("没有相关匹配"));
        if (match.isFinished() == false && match.isDeleted() == false) {
            matchService.delete(match.getId());
        }
    }

    @GetMapping
    @ApiOperation(value = "获取匹配记录")
    public Slice<? extends MatchView, Integer> getMatchList(SliceIndicator<Integer> indicator) {
        val matchs = matchService.getMatchs(indicator);
        return matchs.mapAll(items -> buildMatchViews(items));
    }

    private List<MatchView> buildMatchViews(List<? extends Match> matches) {
        return CollectionUtils.map(matches, (r) -> new MatchView(r));
    }

    private boolean matchFriends(Long uid) {
        List<? extends UserRelation> userRelations = userRelationService.getUserRelationsByUserIdAndRelation(uid, UserRelation.Relation.friend);
        if (userRelations.size() < 5) {

        }
        return true;
    }

    private boolean matchFriendsAndOther(Long uid, List<? extends UserRelation> userRelations) {
        Set<Long> ids = new HashSet<>();
        for (UserRelation userRelation : userRelations) {
            ids.add(userRelation.getToUserId());
        }
        Set<? extends UserRelation> userRelations1 = userRelationService.getUserRelationsByUserIdsInAndRelation(ids, UserRelation.Relation.friend);
        return false;
    }

}
