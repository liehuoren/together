package com.zhlzzz.together.user;

import java.util.Optional;

public interface UserService {

    User addUser(UserParam  parameters) throws OpenIdUsedException;
    Optional<? extends User> getUserById(Long id);
    Optional<? extends User> getUserByOpenId(String openId);
}
