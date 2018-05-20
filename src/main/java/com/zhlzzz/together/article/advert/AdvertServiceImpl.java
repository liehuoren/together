package com.zhlzzz.together.article.advert;

import com.zhlzzz.together.article.ArticleNotFoundException;
import com.zhlzzz.together.user.user_label.UserLabelNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class AdvertServiceImpl implements AdvertService {

    @PersistenceContext
    private EntityManager em;
    private final TransactionTemplate tt;
    private final AdvertRepository advertRepository;

    private void setParamter(AdvertEntity advertEntity,AdvertParam advertParam){
        if (StringUtils.isNotEmpty(advertParam.getAdvertUrl()))
            advertEntity.setAdvertUrl(advertParam.getAdvertUrl());
        advertEntity.setAvailable(advertParam.isAvailable());
    }

    @Override
    public AdvertEntity addAdvert(AdvertParam advertParam) {
        AdvertEntity advertEntity = new AdvertEntity();
        setParamter(advertEntity,advertParam);
        advertEntity.setCreateTime(LocalDateTime.now());
        return advertRepository.save(advertEntity);
    }

    @Override
    public AdvertEntity updateAdvert(Long id, AdvertParam advertParam) {
        AdvertEntity advert = advertRepository.findById(id).orElseThrow(()->new UserLabelNotFoundException(id));
        setParamter(advert,advertParam);
        return advertRepository.save(advert);
    }

    @Override
    public Optional<? extends AdvertEntity> getAdvertById(Long id) {
        return advertRepository.findById(id);
    }

    @Override
    public void deleteAdvert(Long id) {
        AdvertEntity replyEntity = advertRepository.findById(id).orElseThrow(() -> new ArticleNotFoundException(id));
        tt.execute((s)-> {
            em.createQuery("DELETE FROM AdvertEntity u WHERE u.id = :id")
                    .setParameter("id", replyEntity.getId())
                    .executeUpdate();
            return true;
        });
    }

    @Override
    public Set<AdvertEntity> findAll(){
        return advertRepository.findAll();
    }
}
