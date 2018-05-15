package com.zhlzzz.together.controllers.article;

import com.zhlzzz.together.article.Article;
import com.zhlzzz.together.article.ArticleParam;
import com.zhlzzz.together.article.ArticleService;
import com.zhlzzz.together.controllers.ApiExceptions;
import com.zhlzzz.together.data.Slice;
import com.zhlzzz.together.data.SliceIndicator;
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
import lombok.val;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/articles", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(description = "资讯", tags = {"Article"})
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    @ApiOperation(value = "文章列表")
    @ResponseBody
    public Slice<? extends ArticleView, Integer> getArticleList(SliceIndicator<Integer> indicator) {
        val articles = articleService.getArticles(indicator);
        return articles.mapAll(items -> buildArticlesView(items));
    }

    @GetMapping(path = "/{id:\\d+}")
    @ApiOperation(value = "文章详情")
    @ResponseBody
    public ArticleView getArticle(@PathVariable Long id) {
        Article article = articleService.getArticleById(id).orElseThrow(() -> ApiExceptions.notFound("不存在此文章。"));
        return new ArticleView(article);
    }

    @PostMapping
    @ApiOperation(value = "新建文章")
    @ResponseBody
    public ArticleView addArticle( @Valid @RequestBody ArticleParam parameters, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream().map((e)->e.toString()).collect(Collectors.joining(";\n"));
            throw ApiExceptions.badRequest(errors);
        }
        requireNonNull(parameters.getTitle(),"title");
        Article article = articleService.addArticle(parameters);
        return new ArticleView(article);
    }

    @PutMapping(path = "/{id:\\d+}")
    @ApiOperation(value = "更新文章")
    @ResponseBody
    public ArticleView updateArticle(@PathVariable Long id, @Valid @RequestBody ArticleParam parameters, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream().map((e)->e.toString()).collect(Collectors.joining(";\n"));
            throw ApiExceptions.badRequest(errors);
        }
        Article article = articleService.getArticleById(id).orElseThrow(() -> ApiExceptions.notFound("不存在此文章"));

        return new ArticleView(articleService.updateArticle(article.getId(), parameters));
    }

    @DeleteMapping(path = "/{id:\\d+}")
    @ApiOperation(value = "删除文章")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Long id) {
        Article article = articleService.getArticleById(id).orElseThrow(() -> ApiExceptions.notFound("不存在此文章"));

        articleService.deleteArticle(article.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void requireNonNull(Object value, String name) {
        if (value == null) {
            throw ApiExceptions.missingParameter(name);
        }
    }

    private List<ArticleView> buildArticlesView(List<? extends Article> articles) {
        return CollectionUtils.map(articles, (r) -> { return new ArticleView(r); });
    }
}
