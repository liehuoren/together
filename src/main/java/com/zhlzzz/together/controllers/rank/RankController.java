package com.zhlzzz.together.controllers.rank;

import com.zhlzzz.together.controllers.ApiAuthentication;
import com.zhlzzz.together.controllers.ApiExceptions;
import com.zhlzzz.together.rank.RankService;
import com.zhlzzz.together.user.User;
import com.zhlzzz.together.user.UserService;
import com.zhlzzz.together.user.user_relation.UserRelation;
import com.zhlzzz.together.user.user_relation.UserRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        List<? extends UserRelation> userRelations = userRelationService.getUserRelationsByUserIdAndRelation(user.getId(), UserRelation.Relation.friend);

        return null;
    }



}
