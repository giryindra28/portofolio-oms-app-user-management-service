package org.portfolio.giry.oms.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.portfolio.giry.oms.dto.*;
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

    @GET
    @Path("/get/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") Integer id){
        return userService.getUserById(id);
    }

    @DELETE
    @Path("/delete/user")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteUser(DeleteUserReq deleteUserReq){
        return userService.deleteUser(deleteUserReq);
    }

    @POST
    @Path("/user/list")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllUsers(GetAllUserReq req){
        return userService.getAllUsers(req);
    }
}
