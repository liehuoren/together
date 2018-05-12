package com.zhlzzz.together.user;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserRepository extends Repository<UserEntity, Long> {

    UserEntity save(UserEntity userEntity);
    Optional<UserEntity> findById(Long id);
    Optional<UserEntity> findByOpenId(String openId);
}
