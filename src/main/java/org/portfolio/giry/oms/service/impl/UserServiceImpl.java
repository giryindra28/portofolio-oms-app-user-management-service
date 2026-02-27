package org.portfolio.giry.oms.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;

import org.portfolio.giry.oms.Exceptions.ProcessExceptions;
import org.portfolio.giry.oms.constants.Code;
import org.portfolio.giry.oms.constants.Messages;
import org.portfolio.giry.oms.dto.*;
import org.portfolio.giry.oms.entity.User;
import org.portfolio.giry.oms.repository.UserRepository;
import org.portfolio.giry.oms.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    @Inject
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Response registerUser(RegisterUserReq registerUserReq) {
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
    public Response updateUser(UpdateReq updateUserReq) {
        User user = userRepository.findById(updateUserReq.id());
        if(user == null){
            throw new ProcessExceptions("User Not Found");
        }
        updateUserReq.username().ifPresent(username -> {
            if(StringUtils.isNotBlank(username)){
                user.setUsername(username);
            }
        });
        updateUserReq.email().ifPresent(email -> {
            if(StringUtils.isNotBlank(email)){
                user.setEmail(email);
            }
        });

        user.setModifiedAt(LocalDateTime.now());
        user.setModifiedBy("test");

        userRepository.persist(user);
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
    public Response getUserById(Integer id) {
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
    public Response deleteUser(DeleteUserReq deleteUserReq) {

        for(Integer value : deleteUserReq.ids()){
            userRepository.deleteById(Long.valueOf(value));
        }
        ObjectResponse<?> response = new ObjectResponse<>(new ResponseObject(Code.SUCCESS_CODE,"Success delete user"),null);
        return Response.status(Response.Status.OK).entity(response).build();
    }

    @Override
    public Response getAllUsers(GetAllUserReq getAllUserReq) {
        List<User> userList = userRepository.getAllUsersPagination(getAllUserReq).stream().toList();
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
