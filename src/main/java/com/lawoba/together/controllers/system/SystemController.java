package com.lawoba.together.controllers.system;

import com.lawoba.together.system.AboutParam;
import com.lawoba.together.controllers.ApiAuthentication;
import com.lawoba.together.controllers.ApiExceptions;
import com.lawoba.together.game.GameType;
import com.lawoba.together.game.GameTypeService;
import com.lawoba.together.match.Match;
import com.lawoba.together.match.MatchService;
import com.lawoba.together.system.AboutEntity;
import com.lawoba.together.system.AboutService;
import com.lawoba.together.user.User;
import com.lawoba.together.user.UserService;
import com.lawoba.together.user.user_relation.UserRelation;
import com.lawoba.together.user.user_relation.UserRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/system", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(description = "系统", tags = {"System"})
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SystemController {

    private final AboutService aboutService;
    private final UserService userService;
    private final MatchService matchService;
    private final GameTypeService gameTypeService;
    private final UserRelationService userRelationService;

    @PutMapping(path = "/about")
    @ApiOperation(value = "更新小程序介绍")
    @ResponseBody
    public AboutView updateAdvert(@RequestBody AboutParam aboutParam, ApiAuthentication auth) {
        User admin = userService.getUserById(auth.requireUserId()).filter(u -> u.isAdmin()).orElse(null);
        if (admin == null) {
            throw ApiExceptions.noPrivilege();
        }
        return new AboutView(aboutService.updateAbout(aboutParam.getCompany(), aboutParam.getLogo(), aboutParam.getIntroduction()));
    }

    @GetMapping(path = "/about")
    @ApiOperation(value = "获取小程序简介")
    @ResponseBody
    public AboutView getAboutCompany() {
        AboutEntity aboutEntity = aboutService.getAbout();
        return new AboutView(aboutEntity);
    }

    @GetMapping(path = "/config")
    @ApiOperation(value = "获取小程序首页信息")
    @ResponseBody
    public SystemView getSystem(ApiAuthentication auth) {
        Match match = matchService.getCurrentMatchByUser(auth.requireUserId()).orElse(null);
        List<? extends UserRelation> userRelations = userRelationService.getUserRelationsByUserIdAndRelation(auth.requireUserId(), UserRelation.Relation.friend);
        Integer friendsNum = userRelations.size();
        Set<Long> userIds = new HashSet<>();
        for (UserRelation userRelation : userRelations) {
            userIds.add(userRelation.getToUserId());
        }
        Integer online = 0;
        if (userIds.size() > 0) {
            List<? extends Match> matches = matchService.getMatchsInUserIds(userIds);

            for (Match match1 : matches) {
                if (match1.isEffective()) {
                    online++;
                }
            }
        }
        List<? extends GameType> gameTypes = gameTypeService.getAllGameTypes();
        return new SystemView(friendsNum,online,match,gameTypes);
    }
}
