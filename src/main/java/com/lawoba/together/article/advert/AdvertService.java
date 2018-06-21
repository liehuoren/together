package com.lawoba.together.article.advert;

import java.util.List;
import java.util.Optional;

public interface AdvertService {

    Advert addAdvert(AdvertParam advertParam);
    Advert updateAdvert(Long id, AdvertParam advertParam);
    Optional<? extends Advert> getAdvertById(Long id);
    void deleteAdvert(Long id);
    List<AdvertEntity> findAll();
}
