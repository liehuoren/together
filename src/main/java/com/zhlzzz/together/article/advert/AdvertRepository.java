package com.zhlzzz.together.article.advert;

import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.Set;

public interface AdvertRepository extends Repository<AdvertEntity, Long> {

    AdvertEntity save(AdvertEntity advertEntity);
    Optional<AdvertEntity> findById(Long id);
    Set<AdvertEntity> findAll();
}
