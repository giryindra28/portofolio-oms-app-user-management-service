package org.portfolio.giry.oms.resource;

import io.quarkus.security.Authenticated;
import io.smallrye.jwt.build.Jwt;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.portfolio.giry.oms.dto.*;
import org.portfolio.giry.oms.service.UserService;

@Path("api/v1/user")
@Authenticated
public class UserResource {
    @Inject
    UserService userService;

    @Inject
    JsonWebToken jsonWebToken;

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerUser(RegisterUserReq registerUserReq) {
        return userService.registerUser(registerUserReq, jsonWebToken);
    }

    @POST
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(UpdateReq request){
        return userService.updateUser(request, jsonWebToken);
    }

    @POST
    @Path("/update/password")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePassword(UpdatePasswordReq updatePasswordReq){
        return userService.updatePassword(updatePasswordReq);
    }

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") Integer id){
        return userService.getUserById(id,jsonWebToken);
    }

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteUser(DeleteUserReq deleteUserReq){
        return userService.deleteUser(deleteUserReq, jsonWebToken);
    }

    @POST
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllUsers(GetAllUserReq req){
        return userService.getAllUsers(req, jsonWebToken);
    }

}
