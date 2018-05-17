package com.zhlzzz.together.article.advert;

import java.util.Optional;
import java.util.Set;

public interface AdvertService {

    AdvertEntity addAdvert(AdvertParam advertParam);
    AdvertEntity updateAdvert(Long id, AdvertParam advertParam);
    Optional<? extends AdvertEntity> getAdvertById(Long id);
    void deleteAdvert(Long id);
    Set<AdvertEntity> findAll();
}
