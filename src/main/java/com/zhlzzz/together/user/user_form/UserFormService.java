package com.zhlzzz.together.user.user_form;

import java.util.Optional;

public interface UserFormService {
    Boolean add(Long userId, String formId);
    UserFormEntity getUserForm(Long userId);
    void delete(Long id);
}
