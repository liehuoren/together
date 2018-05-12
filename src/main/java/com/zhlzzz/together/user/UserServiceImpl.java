package com.zhlzzz.together.user;

import com.zhlzzz.together.utils.EntityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.nio.charset.Charset;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class UserServiceImpl implements UserService {

    @PersistenceContext
    private EntityManager em;
    private final JdbcTemplate jdbc;
    private final UserRepository userRepository;

    @Override
    public User addUser(UserParam parameters) throws OpenIdUsedException {
        return null;
    }

    @Override
    public Optional<? extends User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<? extends User> getUserByOpenId(String openId) {
        return userRepository.findByOpenId(openId);
    }

    @PostConstruct
    public void onStartUp() {
        if (!EntityUtils.isEntitiesEmpty(em, UserEntity.class)) {
            return;
        }

        try {
            Resource resource = new ClassPathResource("users.sql");
            byte[] bytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
            String sql = new String(bytes, Charset.forName("UTF-8"));

            jdbc.execute(sql);
        } catch (Throwable e) {
            log.error("can not load user sql.", e);
        }

    }
}
