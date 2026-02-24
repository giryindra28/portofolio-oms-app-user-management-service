package org.portfolio.giry.oms.service;

import jakarta.ws.rs.core.Response;
import org.portfolio.giry.oms.dto.RegisterUserReq;
import org.portfolio.giry.oms.dto.UpdatePasswordReq;
import org.portfolio.giry.oms.dto.UpdateReq;

public interface UserService {
    Response registerUser(RegisterUserReq registerUserReq);
    Response updateUser(UpdateReq updateUserReq);
    Response updatePassword(UpdatePasswordReq updatePasswordReq);
}
