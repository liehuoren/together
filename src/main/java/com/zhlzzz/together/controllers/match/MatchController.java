package com.zhlzzz.together.controllers.match;

import com.zhlzzz.together.match.Match;
import com.zhlzzz.together.match.MatchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/match", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(description = "匹配", tags = {"Match"})
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MatchController {

    private final MatchService matchService;

    @PostMapping
    @ApiOperation(value = "新建匹配")
    @ResponseBody
    public MatchView addMatch(@RequestParam Long userId, @RequestParam Integer gameTypeId, @RequestParam Long minute) {
        Match match = matchService.addMatch(userId, gameTypeId,minute);
        return new MatchView(match);
    }


    @DeleteMapping(path = "/{id:\\d+}")
    @ApiOperation(value = "删除匹配")
    public void deleteMatch(@PathVariable Long id) {
        matchService.delete(id);
    }
}
