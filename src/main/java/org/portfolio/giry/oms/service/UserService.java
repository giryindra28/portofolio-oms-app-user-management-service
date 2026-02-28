package org.portfolio.giry.oms.service;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.portfolio.giry.oms.dto.*;

public interface UserService {
    Response registerUser(RegisterUserReq registerUserReq, JsonWebToken jwt);
    Response updateUser(UpdateReq updateUserReq, JsonWebToken jsonWebToken);
    Response updatePassword(UpdatePasswordReq updatePasswordReq);
    Response getUserById(Integer id, JsonWebToken jwt);
    Response deleteUser(DeleteUserReq deleteUserReq, JsonWebToken jsonWebToken);
    Response getAllUsers(GetAllUserReq getAllUserReq, JsonWebToken jsonWebToken);
}
