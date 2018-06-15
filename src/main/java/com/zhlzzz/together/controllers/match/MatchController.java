package com.zhlzzz.together.controllers.match;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.zhlzzz.together.chat_room.ChatRoom;
import com.zhlzzz.together.chat_room.ChatRoomService;
import com.zhlzzz.together.controllers.ApiAuthentication;
import com.zhlzzz.together.controllers.ApiExceptions;
import com.zhlzzz.together.data.Slice;
import com.zhlzzz.together.data.SliceIndicator;
import com.zhlzzz.together.game.GameType;
import com.zhlzzz.together.game.GameTypeService;
import com.zhlzzz.together.game.game_config.GameConfig;
import com.zhlzzz.together.game.game_config.GameConfigOptionEntity;
import com.zhlzzz.together.game.game_config.GameConfigService;
import com.zhlzzz.together.match.Match;
import com.zhlzzz.together.match.MatchParam;
import com.zhlzzz.together.match.MatchService;
import com.zhlzzz.together.user.User;
import com.zhlzzz.together.user.UserService;
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
import me.chanjar.weixin.common.exception.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import lombok.val;

import java.util.*;
import java.util.stream.Collectors;

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
    private final GameTypeService gameTypeService;
    private final WxMaService wxMaService;
    private final UserService userService;

    @PostMapping
    @ApiOperation(value = "新建匹配")
    @ResponseBody
    public MatchView addMatch(@RequestParam Integer gameTypeId, @RequestParam(required = false) String formId, ApiAuthentication auth) {

        GameType gameType = gameTypeService.getGameTypeById(gameTypeId).orElseThrow(() -> ApiExceptions.notFound("没有相关游戏"));

        UserGameConfigEntity userGameConfig = userGameConfigService.getUserGameConfigByUserAndGameType(auth.requireUserId(), gameType.getId()).orElseThrow(() -> ApiExceptions.notFound("没有用户相关游戏配置"));
        requireNonNull(userGameConfig.getGameTypeId(),"gameTypeId");
        requireNonNull(userGameConfig.getMemberNum(),"memberNum");
        requireNonNull(userGameConfig.getMinute(),"minute");
        requireNonNull(userGameConfig.getMatchRange(),"matchRange");

        MatchParam matchParam = new MatchParam();
        matchParam.setName(gameType.getName() + "—1缺" + userGameConfig.getMemberNum().toString());
        matchParam.setGameTypeId(gameType.getId());
        matchParam.setMemberNum(userGameConfig.getMemberNum());
        matchParam.setMinute(userGameConfig.getMinute());
        matchParam.setMatchRange(userGameConfig.getMatchRange());
        if (!Strings.isNullOrEmpty(formId)) {
            matchParam.setFormId(formId);
        }
        if (!Strings.isNullOrEmpty(userGameConfig.getOtherItem())) {
            matchParam.setOtherItem(userGameConfig.getOtherItem());
        }

        Match match = matchService.getCurrentMatchByUser(auth.requireUserId()).orElse(null);
        if (match == null || match.isEffective() == false) {
            matchParam.setUserId(auth.requireUserId());
            match = matchService.addMatch(matchParam);
            matchPeople(match);
        } else {
            throw ApiExceptions.badRequest("当前正在匹配中，无法新增匹配");
        }
        return new MatchView(match);
    }

    private void requireNonNull(Object value, String name) {
        if (value == null) {
            throw ApiExceptions.missingParameter(name);
        }
    }

    @DeleteMapping(path = "/chat-room")
    @ApiOperation(value = "关闭聊天室")
    public void deleteCharRoom(ApiAuthentication auth) {
        Match match = matchService.getCurrentMatchByUser(auth.requireUserId()).orElseThrow(() -> ApiExceptions.notFound("没有相关匹配"));
        if (match.isFinished() && match.isCloseDown() == false) {
            matchService.closeMatch(match.getId());
        }
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
    public Slice<? extends MatchListView, Integer> getMatchList(SliceIndicator<Integer> indicator, ApiAuthentication auth) {
        val matchs = matchService.getMatchs(indicator, auth.requireUserId());

        return matchs.mapAll(items -> buildMatchListViews(items, auth.requireUserId()));
    }

    private List<MatchListView> buildMatchListViews(List<? extends Match> matches, Long userId) {
        Set<Integer> gameTypeIds = new HashSet<>();
        List<? extends GameType> gameTypes = gameTypeService.getAllGameTypes();
        Map<Integer, GameType> gameTypeMap = new HashMap<>();
        Map<Integer, UserGameConfigEntity> userGameConfigEntityMap = new HashMap<>();
        for (GameType gameType : gameTypes) {
            gameTypeIds.add(gameType.getId());
            gameTypeMap.put(gameType.getId(), gameType);
        }
        List<UserGameConfigEntity> userGameConfigEntities = userGameConfigService.getUserGameConfigs(userId, gameTypeIds);
        for (UserGameConfigEntity userGameConfigEntity : userGameConfigEntities) {
            userGameConfigEntityMap.put(userGameConfigEntity.getGameTypeId(), userGameConfigEntity);
        }
        return CollectionUtils.map(matches, (r) -> new MatchListView(r,gameTypeMap.get(r.getGameTypeId()), userGameConfigEntityMap.get(r.getGameTypeId())));
    }

    private void matchPeople(Match matchParam) {
        List<? extends Match> matches = matchService.getCurrentMatchListCondition(matchParam.getGameTypeId(), matchParam.getMemberNum(), matchParam.getOtherItem());
        if (matches == null || matches.size() < matchParam.getMemberNum()) {
            return;
        }

        Set<Long> result = new HashSet<>();
        Set<Long> userIds = new HashSet<>(); //相同匹配条件下的人员ID
        Map<Long, Match> matchMap = new HashMap<>();
        for (Match match : matches) {
            userIds.add(match.getUserId());
            matchMap.put(match.getUserId(), match);
        }
        if (userIds.size() < matchParam.getMemberNum()) {
            return;
        }

        Set<Long> userIds1 = new HashSet<>(); // 好友IDs
        Set<Long> userIds2 = new HashSet<>(); // 黑名单IDs
        List<? extends UserRelation> userOnlyFriends = userRelationService.getUserRelationsByUserIdAndRelation(matchParam.getUserId(), UserRelation.Relation.friend);
        if (userOnlyFriends == null || userOnlyFriends.size() == 0) {
            return;
        }

        for (UserRelation userRelation : userOnlyFriends) {
            userIds1.add(userRelation.getToUserId());
        }


        result.clear();
        result.addAll(userIds);
        if (matchParam.getMatchRange().equals(Match.Range.onlyFriend)) {
            result.retainAll(userIds1);
            if (result.size() < matchParam.getMemberNum()) {
                return;
            }
            Set<? extends UserRelation> userNoFriends = userRelationService.getUserRelationsByUserIdsInAndRelation(result, UserRelation.Relation.nofriend);
            for (UserRelation userRelation : userNoFriends) {
                if (userRelation.getToUserId().equals(matchParam.getUserId())) {
                    userIds2.add(userRelation.getUserId());
                } else {
                    userIds2.add(userRelation.getToUserId());
                }
            }
            result.removeAll(userIds2);

            if (result.size() < matchParam.getMemberNum()) {
                return;
            }
        } else {
            Set<? extends UserRelation> userAllFriends = userRelationService.getUserRelationsByUserIdsInAndRelation(userIds1, UserRelation.Relation.friend);
            for (UserRelation userRelation : userAllFriends) {
                userIds1.add(userRelation.getToUserId());
            }
            result.retainAll(userIds1);
            result.add(matchParam.getUserId());
            if (result.size() < matchParam.getMemberNum() + 1) {
                return;
            }
            Set<? extends UserRelation> userNoFriends = userRelationService.getUserRelationsByUserIdsInAndRelation(result, UserRelation.Relation.nofriend);
            for (UserRelation userRelation : userNoFriends) {
                if (userRelation.getToUserId().equals(matchParam.getUserId())) {
                    userIds2.add(userRelation.getUserId());
                } else {
                    userIds2.add(userRelation.getToUserId());
                }
            }
            result.removeAll(userIds2);
            if (result.size() < matchParam.getMemberNum() + 1) {
                return;
            }
        }

        result.add(matchParam.getUserId());

        List<? extends User> users = userService.getUserByIdsOrderByCreditScore(result);
        List<? extends User> matchUsers = users.subList(0, matchParam.getMemberNum()+1);

        result.clear();
        for (User user : matchUsers) {
            result.add(user.getId());
        }
        ChatRoom chatRoom = chatRoomService.addChatRoom(matchParam.getName(), matchParam.getGameTypeId(), result);

        for (User user : matchUsers) {
             matchService.finish(matchMap.get(user.getId()).getId(), chatRoom.getId());
             userService.increaseScore(user.getId(), 2);
             try {
                 wxMaService.getMsgService().sendTemplateMsg(WxMaTemplateMessage.builder()
                         .toUser(user.getOpenId())
                         .templateId("Qc09iPq7R50tI_ac4KrTaO8WBROefpHsveYyiy2cf64")
                         .formId(matchMap.get(user.getId()).getFormId())
                         .page("/pages/match-success/match-success?id=" + chatRoom.getId())
                         .data(Lists.newArrayList(
                                 new WxMaTemplateMessage.Data("keyword1", chatRoom.getName()),
                                 new WxMaTemplateMessage.Data("keyword2","战友已经召集完毕，战斗即将打响，您还在等什么？")
                         ))
                         .build());
             } catch (WxErrorException e) {

             }
             for (User user1 : matchUsers) {
                 if (!user.getId().equals(user1.getId())) {
                     boolean hasRelation = userRelationService.getUserRelationByUserIdAndToUserId(user.getId(), user1.getId()).isPresent();
                     if (!hasRelation) {
                         userRelationService.addUserRelation(user.getId(), user1.getId(), UserRelation.Relation.stranger);
                     }
                 }
             }
        }
    }

}
