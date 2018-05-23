package com.zhlzzz.together.user.user_label;

import com.zhlzzz.together.user.UserNotFoundException;

import java.util.Optional;
import java.util.Set;

public interface UserLabelService {
    UserLabelEntity addUserLabel(Long userId, String label) throws UserLabelUsedException;
    void showUserLabels(Long userId, Set<Long> ids);
    Set<UserLabelEntity> getUserLabelsByUserId(Long userId, Boolean showed);
    void delete(Set<Long> ids);
}
