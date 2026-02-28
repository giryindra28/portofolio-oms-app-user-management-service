package org.portfolio.giry.oms.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.portfolio.giry.oms.dto.LoginReq;
import org.portfolio.giry.oms.service.AuthenticationService;
import org.portfolio.giry.oms.service.UserService;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Path("/api/v1/auth")
public class AuthenticationResource {
    @Inject
    AuthenticationService authenticationService;

    @Path("/login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(@Valid LoginReq loginReq) throws GeneralSecurityException, IOException {
        return authenticationService.login(loginReq);
    }
}
