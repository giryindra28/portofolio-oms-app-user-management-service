package org.portfolio.giry.oms.service;

import jakarta.ws.rs.core.Response;
import org.portfolio.giry.oms.dto.*;

public interface UserService {
    Response registerUser(RegisterUserReq registerUserReq);
    Response updateUser(UpdateReq updateUserReq);
    Response updatePassword(UpdatePasswordReq updatePasswordReq);
    Response getUserById(Integer id);
    Response deleteUser(DeleteUserReq deleteUserReq);
    Response getAllUsers(GetAllUserReq getAllUserReq);
}
