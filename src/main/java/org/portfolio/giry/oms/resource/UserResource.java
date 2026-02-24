package org.portfolio.giry.oms.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.portfolio.giry.oms.dto.RegisterUserReq;
import org.portfolio.giry.oms.dto.UpdatePasswordReq;
import org.portfolio.giry.oms.dto.UpdateReq;
import org.portfolio.giry.oms.service.UserService;

@Path("api/v1/user")
public class UserResource {
    @Inject
    UserService userService;

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerUser(RegisterUserReq registerUserReq) {
        return userService.registerUser(registerUserReq);
    }
    @POST
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(UpdateReq request){
        return userService.updateUser(request);
    }
    @POST
    @Path("/update/password")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePassword(UpdatePasswordReq updatePasswordReq){
        return userService.updatePassword(updatePasswordReq);
    }
}
