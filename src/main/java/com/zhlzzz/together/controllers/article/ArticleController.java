package com.zhlzzz.together.controllers.article;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping(path = "/articles", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(description = "资讯", tags = {"Article"})
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleController {

    @GetMapping
    @ApiOperation(value = "文章列表")
    @ResponseBody
    public Page<ArticleView> getArticleList(@PageableDefault Pageable pageable) {
        return null;
    }

    @GetMapping(path = "/{id:\\d+}")
    @ApiOperation(value = "文章详情")
    @ResponseBody
    public ArticleView getArticle(@PathVariable Long id) {
        return new ArticleView();
    }
}
