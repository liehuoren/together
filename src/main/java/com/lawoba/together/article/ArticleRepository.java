package com.lawoba.together.article;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface ArticleRepository extends Repository<ArticleEntity, Long> {

    ArticleEntity save(ArticleEntity articleEntity);
    Optional<ArticleEntity> findById(Long id);
}
