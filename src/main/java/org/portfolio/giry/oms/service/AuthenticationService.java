package org.portfolio.giry.oms.service;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.portfolio.giry.oms.dto.JwtDto;
import org.portfolio.giry.oms.dto.LoginReq;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface AuthenticationService {
    Response login(LoginReq loginReq) throws GeneralSecurityException, IOException;
    JwtDto doConvertJwtResult(JsonWebToken jwt);
}
