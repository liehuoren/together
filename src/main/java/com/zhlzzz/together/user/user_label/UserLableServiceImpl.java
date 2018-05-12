package com.zhlzzz.together.user.user_label;

import com.zhlzzz.together.user.User;
import com.zhlzzz.together.user.UserNotFoundException;
import com.zhlzzz.together.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class UserLableServiceImpl implements UserLabelService {

    private final UserRepository userRepository;
    private final UserLabelRepository userLabelRepository;

    @Override
    public UserLabelEntity addUserLabel(String label, Long userId) throws UserLabelUsedException, UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        UserLabelEntity userLabel = new UserLabelEntity();
        userLabel.setLabel(label);
        userLabel.setUserId(user.getId());

        try{
            return userLabelRepository.save(userLabel);
        } catch (DataIntegrityViolationException e) {
            throw new UserLabelUsedException(label, e);
        }
    }

    @Override
    public UserLabelEntity updateUserLabel(Long id, String label) throws UserLabelNotFoundException {
        UserLabelEntity userLabel = userLabelRepository.findById(id).orElseThrow(() -> new UserLabelNotFoundException(id));

        userLabel.setLabel(label);

        try {
            return userLabelRepository.save(userLabel);
        } catch (DataIntegrityViolationException e) {
            throw new UserLabelUsedException(label, e);
        }

    }

    @Override
    public Set<UserLabelEntity> getUserLabelsByUserId(Long userId) {
        return null;
    }
}
