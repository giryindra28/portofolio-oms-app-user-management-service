package org.portfolio.giry.oms.service;

import org.portfolio.giry.oms.dto.GetAllUserReq;
import org.portfolio.giry.oms.dto.LoginReq;
import org.portfolio.giry.oms.entity.User;

import java.util.List;

public interface CacheService {
    User loginUser(String username, String password);
    void invalidateCacheUser(String username);
    void invalidateAllCacheUsers();
    List<User> getAllCacheUsers(GetAllUserReq getAllUserReq);
}
