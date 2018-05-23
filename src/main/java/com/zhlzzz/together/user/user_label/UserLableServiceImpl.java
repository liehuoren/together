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
import java.util.List;
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
    public UserLabelEntity addUserLabel(Long userId, String label) throws UserLabelUsedException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        UserLabelEntity userLabel= userLabelRepository.findByUserIdAndLabel(user.getId(), label).orElse(null);
        if (userLabel == null) {
            userLabel = new UserLabelEntity();
            userLabel.setLabel(label);
            userLabel.setUserId(user.getId());
            return userLabelRepository.save(userLabel);
        } else {
            throw new UserLabelUsedException(label);
        }

    }

    @Override
    public void showUserLabels(Long userId, Set<Long> ids) {
        tt.execute((s)-> {
            List<UserLabelEntity> userLabelEntities = em.createQuery("SELECT u FROM UserLabelEntity u WHERE u.id = :id", UserLabelEntity.class)
                    .setParameter("id", userId)
                    .getResultList();
            for (UserLabelEntity userLabelEntity : userLabelEntities) {
                if (ids.contains(userLabelEntity.getId())) {
                    userLabelEntity.setShowed(true);
                } else {
                    userLabelEntity.setShowed(false);
                }
                em.merge(userLabelEntity);
            }
            em.flush();
            return true;
        });
    }

    @Override
    public Set<UserLabelEntity> getUserLabelsByUserId(Long userId , Boolean show) {
        if (show == null) {
            return userLabelRepository.findByUserId(userId);
        } else {
            return userLabelRepository.findByUserIdAndShowed(userId, true);
        }

    }

    @Override
    public void delete(Set<Long> ids) {
        tt.execute((s)-> {
            em.createQuery("DELETE FROM UserLabelEntity u WHERE u.id in :ids")
                    .setParameter("ids", ids)
                    .executeUpdate();
            return true;
        });
    }
}
