package com.lawoba.together.controllers.article;

import com.lawoba.together.article.discuss.*;
import com.lawoba.together.controllers.ApiAuthentication;
import com.lawoba.together.controllers.ApiExceptions;
import com.lawoba.together.data.Slice;
import com.lawoba.together.data.SliceIndicator;
import com.lawoba.together.user.User;
import com.lawoba.together.user.UserService;
import com.lawoba.together.utils.CollectionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/discusses", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(description = "评论", tags = {"Discuss"})
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DiscussController {

    private final DiscussService discussService;
    private final UserService userService;

    @GetMapping(path = "/{id:\\d+}")
    @ApiOperation(value = "根据ID获取评论")
    @ResponseBody
    public DiscussView getDiscuss(@PathVariable Long id) {
        Discuss discuss = discussService.getDiscussById(id).orElseThrow(() -> ApiExceptions.notFound("没有相关评论"));
        User user = userService.getUserById(discuss.getUserId()).orElse(null);
        return new DiscussView(discuss, user);
    }

    @GetMapping
    @ApiOperation(value = "获取评论列表")
    @ResponseBody
    public Slice<? extends DiscussView, Integer> getDiscussList(DiscussCriteria criteria, SliceIndicator<Integer> indicator, ApiAuthentication auth) {
        User admin = userService.getUserById(auth.requireUserId()).filter(u -> u.isAdmin()).orElse(null);
        if (admin == null) {
            throw ApiExceptions.noPrivilege();
        }
        val discusses = discussService.getDiscussesByCriteria(criteria, indicator);
        return discusses.mapAll(items -> buildDiscussViews(items));
    }

    @PutMapping(path = "/{id:\\d+}")
    @ApiOperation(value = "回复评论")
    @ResponseBody
    public DiscussView addDiscussReply(@PathVariable Long id, @Valid @RequestBody DiscussParam param,
                                       ApiAuthentication auth, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream().map((e)->e.toString()).collect(Collectors.joining(";\n"));
            throw ApiExceptions.badRequest(errors);
        }
        User admin = userService.getUserById(auth.requireUserId()).filter(u -> u.isAdmin()).orElse(null);
        if (admin == null) {
            throw ApiExceptions.noPrivilege();
        }

        Discuss discuss = discussService.updateDiscuss(id, param);
        User user = userService.getUserById(discuss.getUserId()).orElse(null);
        return new DiscussView(discuss, user);
    }


    @DeleteMapping(path = "/{id:\\d+}")
    @ApiOperation(value = "删除评论")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Long id, ApiAuthentication auth) {
        User admin = userService.getUserById(auth.requireUserId()).filter(u -> u.isAdmin()).orElse(null);
        if (admin == null) {
            throw ApiExceptions.noPrivilege();
        }
        Discuss discuss = discussService.getDiscussById(id).orElseThrow(() -> ApiExceptions.notFound("不存在此评论"));
        discussService.deleteDiscuss(discuss.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private List<DiscussView> buildDiscussViews(List<? extends Discuss> discusses) {
        Set<Long> userIds = new HashSet<>();
        for (Discuss discuss : discusses) {
            userIds.add(discuss.getUserId());
        }
        Set<? extends User> users = userService.getUsersByIds(userIds);
        Map<Long, ? extends User> userMap = CollectionUtils.toMap(users, User::getId);

        return CollectionUtils.map(discusses, (d) -> {
            User user = userMap.get(d.getUserId());
            return new DiscussView(d, user);});
    }


}
