package org.portfolio.giry.oms.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.portfolio.giry.oms.entity.User;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
    public User findByEmail(String email) {
        return find("email",email).firstResult();
    }
    public User findById(int id) {
        return find("id", id).firstResult();
    }
    public User findByPassword(String password) {
        return find("password", password).firstResult();
    }
}
