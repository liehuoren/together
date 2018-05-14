package com.zhlzzz.together.user.user_label;

import com.zhlzzz.together.user.UserNotFoundException;

import java.util.Optional;
import java.util.Set;

public interface UserLabelService {
    UserLabelEntity addUserLabel(String label, Long userId) throws UserLabelUsedException, UserNotFoundException;
    UserLabelEntity updateUserLabel(Long id, String label) throws UserLabelNotFoundException;
    Optional<UserLabelEntity> getById(Long id);
    Optional<UserLabelEntity> getByIdAndUserId(Long id, Long userId);
    Set<UserLabelEntity> getUserLabelsByUserId(Long userId);
    void delete(Long id);
}
