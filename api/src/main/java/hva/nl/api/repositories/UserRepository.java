package hva.nl.api.repositories;
import hva.nl.api.controllers.PasswordController;
import hva.nl.api.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserRepository implements IUserRepository {

    @Autowired
    private EntityManager em;

    public List<User> getUsers() {
        return em.createNamedQuery("find_all_users", User.class).getResultList();
    }

    public List<User> findTopFiveUsers() {
        return em.createNamedQuery("top_5_users", User.class).setMaxResults(5).getResultList();
    }

    public User getUserByID(int id) {
        return em.find(User.class, id);
    }

    public User setUser(User user) {
        return em.merge(user);
    }

    public User getUserByLogin(String loginname, String loginpassword) {
        return getUsers()
                .stream()
                .filter(u ->
                        (u.getUsername().equals(loginname)
                                || u.getEmail().equals(loginname))
                                && PasswordController.check(loginpassword,
                                u.getPassword()) && u.getDisabled() == 0)
                .findFirst()
                .orElse(null);
    }

    public long countUsers() {
        return (long) em.createNamedQuery("count_users").getSingleResult();
    }

    public List<User> getData() {
        return em.createNamedQuery("user_data").getResultList();
    }

    public void disable(int id) {
        User user = getUserByID(id);
        if(user.getDisabled() == 0) {
            user.setDisabled(1);
        }
        em.merge(user);
    }

    public void enable(int id) {
        User user = getUserByID(id);
        if(user.getDisabled() == 1) {
            user.setDisabled(0);
        }
        em.merge(user);
    }


}
