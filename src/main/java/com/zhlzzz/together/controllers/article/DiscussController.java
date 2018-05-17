package com.zhlzzz.together.controllers.article;

import com.zhlzzz.together.article.discuss.DiscussEntity;
import com.zhlzzz.together.article.discuss.DiscussParam;
import com.zhlzzz.together.article.discuss.DiscussService;
import com.zhlzzz.together.controllers.ApiExceptions;
import com.zhlzzz.together.utils.CollectionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/articles/{articleId:\\d+}/discuss", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(description = "评论", tags = {"Discuss"})
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DiscussController {

    private final DiscussService discussService;

    @GetMapping
    @ApiOperation(value = "获取文章评论列表")
    @ResponseBody
    public Set<DiscussView> getDiscussList(@PathVariable Long articleId) {
        Set<DiscussEntity> discussEntities = discussService.findByArticleId(articleId);
        return CollectionUtils.map(discussEntities,(r) ->{ return new DiscussView(r);});
    }

    @PostMapping
    @ApiOperation(value = "新建评论")
    @ResponseBody
    public DiscussView addDiscuss(@PathVariable Long articleId, @Valid @RequestBody DiscussParam discussParam, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream().map((e)->e.toString()).collect(Collectors.joining(";\n"));
            throw ApiExceptions.badRequest(errors);
        }
        DiscussEntity discussEntity = discussService.addDiscuss(articleId,discussParam);
        return new DiscussView(discussEntity);
    }

    @PutMapping(path = "/{id:\\d+}")
    @ApiOperation(value = "更新评论（审核或置顶）")
    @ResponseBody
    public DiscussView updateDiscuss(@PathVariable Long articleId,@PathVariable Long id, @Valid @RequestBody DiscussParam discussParam,BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream().map((e)->e.toString()).collect(Collectors.joining(";\n"));
            throw ApiExceptions.badRequest(errors);
        }
        DiscussEntity discussEntity = discussService.findByIdAndArticleIdAndUserId(id,articleId,discussParam.getUserId()).orElseThrow(()->ApiExceptions.notFound("没有文章相关评论"));
        return new DiscussView(discussService.updateDiscuss(discussEntity.getId(), discussParam));
    }

    @DeleteMapping(path = "/{id:\\d+}")
    @ApiOperation(value = "删除评论")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Long articleId,@PathVariable Long id) {
        DiscussEntity discuss = discussService.getDiscussById(id).orElseThrow(() -> ApiExceptions.notFound("不存在此评论"));
        discussService.deleteDiscuss(discuss.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
