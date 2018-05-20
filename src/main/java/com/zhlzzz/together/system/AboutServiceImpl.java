package com.zhlzzz.together.system;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class AboutServiceImpl implements AboutService {

    @PersistenceContext
    private EntityManager em;
    private final TransactionTemplate tt;
    private final AboutRepository systemRepository;

    @Override
    public AboutEntity updateAbout(AboutParam aboutParam) {
        AboutEntity about = systemRepository.findById(1l).orElseThrow(() -> new AboutNotFoundException());
        if (StringUtils.isNotEmpty(aboutParam.getIntroduction()))
            about.setIntroduction(aboutParam.getIntroduction());
        if (StringUtils.isNotEmpty(aboutParam.getCompany()))
            about.setCompany(aboutParam.getCompany());
        if (StringUtils.isNotEmpty(aboutParam.getLogo()))
            about.setLogo(aboutParam.getLogo());
        return systemRepository.save(about);
    }

    @Override
    public Optional<? extends AboutEntity> findAbout() {
        return systemRepository.findById(1l);
    }
}
