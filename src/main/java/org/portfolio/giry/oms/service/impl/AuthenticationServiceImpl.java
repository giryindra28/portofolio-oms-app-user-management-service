package org.portfolio.giry.oms.service.impl;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.util.KeyUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.portfolio.giry.oms.Exceptions.ProcessExceptions;
import org.portfolio.giry.oms.constants.Code;
import org.portfolio.giry.oms.dto.*;
import org.portfolio.giry.oms.entity.User;
import org.portfolio.giry.oms.repository.UserRepository;
import org.portfolio.giry.oms.service.AuthenticationService;
import org.portfolio.giry.oms.service.CacheService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.time.Instant;

@ApplicationScoped
public class AuthenticationServiceImpl implements AuthenticationService {
    UserRepository userRepository;
    CacheService cacheService;

    @Inject
    public AuthenticationServiceImpl(UserRepository userRepository,CacheService cacheService) {
        this.userRepository = userRepository;
        this.cacheService = cacheService;
    }

    @Override
    public Response login(LoginReq loginReq) throws GeneralSecurityException, IOException {
        User user = cacheService.loginUser(loginReq.username(), loginReq.password());
        if(user == null){
            throw new ProcessExceptions(loginReq.username() + " is not registered");
        }
        UserLoginResp userLoginResp = new UserLoginResp(user.getId(), user.getUsername(),user.getEmail(),user.getRoleName());
        AuthenticationResp authenticationResp = new AuthenticationResp(userLoginResp,this.doGenerateToken(user));
        ObjectResponse<AuthenticationResp> response = new ObjectResponse<>(new ResponseObject(Code.SUCCESS_CODE,"Success Login"),authenticationResp);
        return Response.status(Response.Status.OK).entity(response).build();
    }

    public String doGenerateToken(User user) throws GeneralSecurityException, IOException {
        PrivateKey privateKey = KeyUtils.readPrivateKey("privateKey.pem");
        return String.valueOf(Jwt.issuer("oms-quarkus")
                .upn(user.getUsername())
                .claim("username",user.getUsername())
                .claim("email",user.getEmail())
                .claim("role",user.getRoleName())
                .expiresAt(Instant.now().plusSeconds(3600))
                .sign());
    }

    @Override
    public JwtDto doConvertJwtResult(JsonWebToken jwt) {
        JwtDto jwtDto = new JwtDto();
        jwtDto.setUsername(jwt.getClaim("username"));
        jwtDto.setEmail(jwt.getClaim("email"));
        jwtDto.setRoleName(jwt.getClaim("role"));
        return jwtDto;
    }
}
