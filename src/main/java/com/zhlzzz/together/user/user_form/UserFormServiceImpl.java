package com.zhlzzz.together.user.user_form;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserFormServiceImpl implements UserFormService {

    @PersistenceContext
    private EntityManager em;
    private final TransactionTemplate tt;

    @Override
    public Boolean add(Long userId, String formId) {
        UserFormEntity userForm = new UserFormEntity();
        userForm.setUserId(userId);
        userForm.setFormId(formId);
        userForm.setCreateTime(LocalDateTime.now());
        em.persist(userForm);
        em.flush();
        return true;
    }

    @Override
    public UserFormEntity getUserForm(Long userId) {
        UserFormEntity userFormEntity = em.createQuery("SELECT FROM UserFormEntity u WHERE u.userId = :userId AND u.createTime > :time", UserFormEntity.class)
                .setParameter("userId", userId)
                .setParameter("time", LocalDateTime.now().plusDays(7L))
                .getSingleResult();
        return userFormEntity;
    }

    @Override
    public void delete(Long id) {
        tt.execute((s)-> {
            em.createQuery("DELETE FROM UserFormEntity u WHERE u.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            return true;
        });
    }
}
