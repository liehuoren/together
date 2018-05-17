package com.zhlzzz.together.controllers.article;

import com.zhlzzz.together.article.reply.ReplyEntity;
import com.zhlzzz.together.article.reply.ReplyParam;
import com.zhlzzz.together.article.reply.ReplyService;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping(path = "/articles/{articleId:\\d+}/{discussId:\\d+}/reply", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(description = "评论回复", tags = {"Discuss"})
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping
    @ApiOperation(value = "获取评论回复列表")
    @ResponseBody
    public Set<ReplyView> getDiscussList(@PathVariable Long articleId,@PathVariable Long discussId) {
        Set<ReplyEntity> replyEntities = replyService.findByArticleIdAndDiscussId(articleId,discussId);
        return CollectionUtils.map(replyEntities,(r) ->{ return new ReplyView(r);});
    }

    @PostMapping
    @ApiOperation(value = "新建回复")
    @ResponseBody
    public ReplyView addDiscuss(@PathVariable Long articleId,@PathVariable Long discussId,@RequestBody ReplyParam replyParam) {
        ReplyEntity replyEntity = replyService.addReply(articleId,discussId,replyParam);
        return new ReplyView(replyEntity);
    }

    @PutMapping(path = "/{id:\\d+}")
    @ApiOperation(value = "更新回复")
    @ResponseBody
    public ReplyView updateDiscuss(@PathVariable Long articleId,@PathVariable Long discussId,@PathVariable Long id, @Valid @RequestBody ReplyParam replyParam) {
        ReplyEntity reply = replyService.getReplyById(articleId,discussId,id).orElseThrow(() -> ApiExceptions.notFound("不存在此回复"));
        return new ReplyView(replyService.updateReply(articleId,discussId,reply.getId(), replyParam));
    }

    @DeleteMapping(path = "/{id:\\d+}")
    @ApiOperation(value = "删除回复")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Long articleId,@PathVariable Long discussId,@PathVariable Long id) {
        ReplyEntity replyEntity = replyService.getReplyById(articleId,discussId,id).orElseThrow(() -> ApiExceptions.notFound("不存在此回复"));
        replyService.deleteReply(articleId,discussId,replyEntity.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
