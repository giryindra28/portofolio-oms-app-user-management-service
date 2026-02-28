package org.portfolio.giry.oms.service.impl;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.apache.commons.lang3.StringUtils;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.portfolio.giry.oms.Exceptions.ProcessExceptions;
import org.portfolio.giry.oms.constants.Code;
import org.portfolio.giry.oms.constants.Messages;
import org.portfolio.giry.oms.dto.*;
import org.portfolio.giry.oms.entity.User;
import org.portfolio.giry.oms.helper.HelperService;
import org.portfolio.giry.oms.repository.UserRepository;
import org.portfolio.giry.oms.service.AuthenticationService;
import org.portfolio.giry.oms.service.CacheService;
import org.portfolio.giry.oms.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    CacheService cacheService;
    AuthenticationService authenticationService;
    @Inject
    public UserServiceImpl(UserRepository userRepository, AuthenticationService authenticationService, CacheService cacheService) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
        this.cacheService = cacheService;
    }

    @Override
    @Transactional
    public Response registerUser(RegisterUserReq registerUserReq, JsonWebToken jsonWebToken) {
        JwtDto jwtDto = authenticationService.doConvertJwtResult(jsonWebToken);
        HelperService.doCheckAdmin(jwtDto.getRoleName());
        User userAlready = userRepository.findByEmail(registerUserReq.email());
        if(userAlready != null){
            throw new ProcessExceptions("User Already Registered");
        }

        User user = new User();
        user.setUsername(registerUserReq.username());
        user.setPassword(registerUserReq.password());
        user.setEmail(registerUserReq.email());
        user.setCreatedAt(LocalDateTime.now());
        userRepository.persist(user);

        ObjectResponse<?> objectResponse = new ObjectResponse<>(new ResponseObject(Code.SUCCESS_CODE, Messages.SUCCESS_MESSAGE),null);
        return Response.status(Response.Status.OK).entity(objectResponse).build();
    }

    @Override
    @Transactional
    public Response updateUser(UpdateReq updateUserReq, JsonWebToken jsonWebToken) {
        User user = userRepository.findById(updateUserReq.id());
        if(user == null){
            throw new ProcessExceptions("User Not Found");
        }
        updateUserReq.username().ifPresent(username -> {
            if(StringUtils.isNotBlank(username)){
                cacheService.invalidateCacheUser(user.getUsername());
                user.setUsername(username);
            }
        });
        updateUserReq.email().ifPresent(email -> {
            if(StringUtils.isNotBlank(email)){
                user.setEmail(email);
            }
        });
        updateUserReq.roleName().ifPresent(roleName -> {
            if(StringUtils.isNotBlank(roleName)){
                user.setRoleName(roleName);
            }
        });

        user.setModifiedAt(LocalDateTime.now());
        user.setModifiedBy("test");

        userRepository.persist(user);
        cacheService.invalidateAllCacheUsers();
        ObjectResponse<?> response = new ObjectResponse<>(new ResponseObject(Code.SUCCESS_CODE, "Success Update User"),null);
        return Response.status(Response.Status.OK).entity(response).build();
    }

    @Override
    @Transactional
    public Response updatePassword(UpdatePasswordReq updatePasswordReq) {
        User user = userRepository.findByPassword(updatePasswordReq.oldPassword());

        if(user == null){
            throw new ProcessExceptions("old password is not matchh");
        }
        this.doCheckValidationPassword(updatePasswordReq.oldPassword(), updatePasswordReq.newPassword());
        user.setPassword(updatePasswordReq.newPassword());
        user.setModifiedAt(LocalDateTime.now());
        user.setModifiedBy("test");
        userRepository.persist(user);

        ObjectResponse<?> response = new ObjectResponse<>(new ResponseObject(Code.SUCCESS_CODE,"Success Update Password"),null);
        return Response.status(Response.Status.OK).entity(response).build();
    }
    public void doCheckValidationPassword(String oldPassword,String newPassword){
        if(oldPassword.equals(newPassword)){
            throw new ProcessExceptions("Passwords do not matcfh");
        }

        boolean valid = newPassword.length() >= 8
                && newPassword.matches(".*[A-Z].*")
                && newPassword.matches(".*[a-z].*")
                && newPassword.matches(".*\\d.*")
                && newPassword.matches(".*[^a-zA-Z0-9].*");
        if(!valid){
            throw new ProcessExceptions("Password harus minimal 8 karakter, mengandung huruf besar, huruf kecil, angka, dan simbol khusus.");
        }

    }

    @Override
    public Response getUserById(Integer id, JsonWebToken jsonWebToken) {
        User user = userRepository.findById(id);
        if(user == null){
            throw new ProcessExceptions("User Not Found");
        }

        GetUserResp getUserResp = new GetUserResp(user.getId(),user.getUsername(),user.getEmail());
        ObjectResponse<GetUserResp> response = new ObjectResponse<>(new ResponseObject(Code.SUCCESS_CODE,Messages.SUCCESS_MESSAGE),getUserResp);
        return Response.status(Response.Status.OK).entity(response).build() ;
    }

    @Override
    @Transactional
    public Response deleteUser(DeleteUserReq deleteUserReq, JsonWebToken jsonWebToken) {
        JwtDto jwtDto = authenticationService.doConvertJwtResult(jsonWebToken);
        HelperService.doCheckAdmin(jwtDto.getRoleName());
        for(Integer value : deleteUserReq.ids()){
            userRepository.deleteById(Long.valueOf(value));

        }
        cacheService.invalidateAllCacheUsers();
        ObjectResponse<?> response = new ObjectResponse<>(new ResponseObject(Code.SUCCESS_CODE,"Success delete user"),null);
        return Response.status(Response.Status.OK).entity(response).build();
    }

    @Override
    public Response getAllUsers(GetAllUserReq getAllUserReq, JsonWebToken jsonWebToken) {
        List<User> userList = cacheService.getAllCacheUsers(getAllUserReq);
        List<GetAllUserResp> resultList = new ArrayList<>();
        for(User valueUser : userList){
            GetAllUserResp getAllUserResp = new GetAllUserResp(valueUser.getId(), valueUser.getUsername(),
                    valueUser.getEmail(), valueUser.getCreatedBy());
            resultList.add(getAllUserResp);
        }
        UsersAllResp usersAllResp = new UsersAllResp(resultList);
        ObjectResponse<UsersAllResp> response = new ObjectResponse<>(new ResponseObject(Code.SUCCESS_CODE,Messages.SUCCESS_MESSAGE),usersAllResp);
        return  Response.status(Response.Status.OK).entity(response).build();
    }
}
