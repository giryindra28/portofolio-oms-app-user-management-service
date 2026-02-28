package org.portfolio.giry.oms.service.impl;

import io.quarkus.cache.CacheInvalidate;
import io.quarkus.cache.CacheInvalidateAll;
import io.quarkus.cache.CacheKey;
import io.quarkus.cache.CacheResult;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import org.portfolio.giry.oms.dto.GetAllUserReq;
import org.portfolio.giry.oms.dto.LoginReq;
import org.portfolio.giry.oms.entity.User;
import org.portfolio.giry.oms.repository.UserRepository;
import org.portfolio.giry.oms.service.CacheService;

import java.util.List;

@ApplicationScoped
public class CacheServiceImpl implements CacheService {
    UserRepository userRepository;
    public CacheServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    @CacheResult(cacheName = "user-cache")
    public User loginUser(@CacheKey String username, String password) {
        Log.info("check db first");
        return userRepository.finByUsernameAndPassword(username, password);
    }

    @Override
    @CacheInvalidate(cacheName = "user-cache")
    public void invalidateCacheUser(@CacheKey String username) {
        Log.info("invalidate user: " + username);
    }

    @Override
    @CacheInvalidateAll(cacheName = "user-cache")
    public void invalidateAllCacheUsers() {
        Log.infof("invalidateAllCacheUsers");
    }

    @Override
    @CacheResult(cacheName = "user-cache")
    public List<User> getAllCacheUsers(GetAllUserReq getAllUserReq) {
        Log.info("check db first get all users");
        return userRepository.getAllUsersPagination(getAllUserReq).stream().toList();
    }
}
