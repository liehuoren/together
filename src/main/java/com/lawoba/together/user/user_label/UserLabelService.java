package com.lawoba.together.user.user_label;

import java.util.List;
import java.util.Set;

public interface UserLabelService {
    UserLabelEntity addUserLabel(Long userId, String label) throws UserLabelUsedException;
    void showUserLabels(Long userId, Set<Long> ids);
    List<UserLabelEntity> getUserLabelsByUserId(Long userId);
    List<UserLabelEntity> getAllByUserId(Long userId);
    void delete(Set<Long> ids);
}
