package org.portfolio.giry.oms.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.lang3.StringUtils;
import org.portfolio.giry.oms.dto.GetAllUserReq;
import org.portfolio.giry.oms.dto.LoginReq;
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
    public User finByUsernameAndPassword(String username, String password) {
        return find("username=?1 and password=?2",username,password).firstResult();
    }
    public PanacheQuery<User> getAllUsersPagination(GetAllUserReq req){
        Sort sort = "Ascending".equalsIgnoreCase(req.sortDirection())
                ? Sort.by(req.sortField().toLowerCase()).ascending()
                : Sort.by(req.sortField().toLowerCase()).descending();

        PanacheQuery<User> query;
        if(StringUtils.isNotBlank(req.keyword())){
            query = find("username like ?1", sort,"%" + req.keyword() + "%");
        }else{
            query = findAll(sort);
        }
        return query.page(Page.of(req.page()-1, req.size()));
    }

}
