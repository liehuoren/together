package com.zhlzzz.together.user.user_label;

import com.zhlzzz.together.user.User;
import com.zhlzzz.together.user.UserNotFoundException;
import com.zhlzzz.together.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class UserLableServiceImpl implements UserLabelService {

    @PersistenceContext
    private EntityManager em;
    private final TransactionTemplate tt;
    private final UserRepository userRepository;
    private final UserLabelRepository userLabelRepository;

    @Override
    public UserLabelEntity addUserLabel(String label, Long userId) throws UserLabelUsedException,
            UserNotFoundException {
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
        UserLabelEntity userLabel = userLabelRepository.findById(id).orElseThrow(() ->
                new UserLabelNotFoundException(id));

        userLabel.setLabel(label);

        try {
            return userLabelRepository.save(userLabel);
        } catch (DataIntegrityViolationException e) {
            throw new UserLabelUsedException(label, e);
        }

    }

    @Override
    public Optional<UserLabelEntity> getById(Long id) {
        return userLabelRepository.findById(id);
    }

    @Override
    public Optional<UserLabelEntity> getByIdAndUserId(Long id, Long userId) {
        return userLabelRepository.findByIdAndUserId(id, userId);
    }

    @Override
    public Set<UserLabelEntity> getUserLabelsByUserId(Long userId) {
        return userLabelRepository.findByUserId(userId);
    }

    @Override
    public void delete(Long id) {
        UserLabelEntity userLabel = userLabelRepository.findById(id).orElseThrow(() ->
                new UserLabelNotFoundException(id));
        tt.execute((s)-> {
            em.createQuery("DELETE FROM UserLabelEntity u WHERE u.id = :id")
                    .setParameter("id", userLabel.getId())
                    .executeUpdate();
            return true;
        });
    }
}
