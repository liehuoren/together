package com.zhlzzz.together.article;

import com.google.common.base.Strings;
import com.zhlzzz.together.data.Slice;
import com.zhlzzz.together.data.SliceIndicator;
import com.zhlzzz.together.data.Slices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class ArticleServiceImpl implements ArticleService {

    @PersistenceContext
    private EntityManager em;
    private final TransactionTemplate tt;
    private final ArticleRepository articleRepository;

    private void setParameter(ArticleEntity article, ArticleParam parameters) {
        if (!Strings.isNullOrEmpty(parameters.getAuthor())) {
            article.setAuthor(parameters.getAuthor());
        }
        if (!Strings.isNullOrEmpty(parameters.getTitle())) {
            article.setTitle(parameters.getTitle());
        }
        if (!Strings.isNullOrEmpty(parameters.getImgUrl())) {
            article.setImgUrl(parameters.getImgUrl());
        }
        if (!Strings.isNullOrEmpty(parameters.getContent())) {
            article.setContent(parameters.getContent());
        }
    }

    @Override
    public Article addArticle(ArticleParam parameters) {
        ArticleEntity article = new ArticleEntity();
        setParameter(article, parameters);
        article.setCreateTime(LocalDateTime.now());
        return articleRepository.save(article);
    }

    @Override
    public Article updateArticle(Long id, ArticleParam parameters) {
        ArticleEntity article = articleRepository.findById(id).orElseThrow(() -> new ArticleNotFoundException(id));
        setParameter(article, parameters);
        return articleRepository.save(article);
    }

    @Override
    public Optional<? extends Article> getArticleById(Long id) {
        return articleRepository.findById(id);
    }

    @Override
    public Slice<? extends Article, Integer> getArticles(SliceIndicator<Integer> indicator) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ArticleEntity> q = cb.createQuery(ArticleEntity.class);
        Root<ArticleEntity> m = q.from(ArticleEntity.class);

        q.select(m).orderBy(cb.desc(m.get("createTime")));

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<ArticleEntity> countM = countQuery.from(ArticleEntity.class);

        List<Predicate> predicates = new ArrayList<>(5);
        countQuery.select(cb.count(countM)).where(cb.and(predicates.toArray(new Predicate[0])));

        Slice<ArticleEntity, Integer> slice = Slices.of(em, q, indicator, countQuery);
        return slice.map(ArticleEntity::toDto);
    }

    @Override
    public void deleteArticle(Long id) {
        ArticleEntity articleEntity = articleRepository.findById(id).orElseThrow(() -> new ArticleNotFoundException(id));
        tt.execute((s)-> {
            em.createQuery("DELETE FROM ARTICLE u WHERE u.id = :id")
                    .setParameter("id", articleEntity.getId())
                    .executeUpdate();
            return true;
        });
    }
}
