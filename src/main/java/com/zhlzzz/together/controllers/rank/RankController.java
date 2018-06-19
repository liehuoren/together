package com.zhlzzz.together.controllers.rank;

import com.zhlzzz.together.controllers.ApiAuthentication;
import com.zhlzzz.together.controllers.ApiExceptions;
import com.zhlzzz.together.rank.Rank;
import com.zhlzzz.together.rank.RankService;
import com.zhlzzz.together.user.User;
import com.zhlzzz.together.user.UserService;
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

import java.util.*;

@RestController
@RequestMapping(path = "/ranks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(description = "排行榜", tags = {"Rank"})
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RankController {

    private final RankService rankService;
    private final UserService userService;
    private final UserRelationService userRelationService;

    @GetMapping
    @ApiOperation(value = "获取排行榜列表")
    @ResponseBody
    public List<RankView> getPlayer(ApiAuthentication auth){
        User user = userService.getUserById(auth.requireUserId()).orElseThrow(() -> ApiExceptions.notFound("没有相关用户信息"));

        List<Long> ids = new ArrayList<>();
        List<? extends UserRelation> userRelations = userRelationService.getUserRelationsByUserIdAndRelation(user.getId(), UserRelation.Relation.friend);
        for (UserRelation userRelation : userRelations) {
            ids.add(userRelation.getToUserId());
        }
        ids.add(user.getId());
        List<Rank> rankList = rankService.getRanksByUserIds(ids);

        if (rankList == null || rankList.size() == 0) {
            return null;
        }

        Set<Long> userIds = new HashSet<>();
        for (Rank rank : rankList) {
            userIds.add(rank.getUserId());
        }

        HashMap<Long, User> userMap = new HashMap<>();
        Set<? extends User> users = userService.getUsersByIds(userIds);
        for (User user1 : users) {
            userMap.put(user1.getId(), user1);
        }

        return  CollectionUtils.map(rankList, (r) -> new RankView(r, userMap.get(r.getUserId())));
    }

}
