package org.portfolio.giry.oms.Exceptions;

import io.quarkus.hibernate.validator.runtime.jaxrs.ResteasyReactiveViolationException;
import io.quarkus.logging.Log;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.portfolio.giry.oms.dto.ObjectResponse;
import org.portfolio.giry.oms.dto.ResponseObject;

@Provider
public class GlobalExceptions implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception e) {
        if(e instanceof ProcessExceptions){
            ObjectResponse<?> response = new ObjectResponse<>(new ResponseObject("4000",e.getMessage()),null);
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }  else{
            Log.error(e.getMessage());
            ObjectResponse<?> response = new ObjectResponse<>(new ResponseObject("5000",e.getMessage() ),null);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
        }
    }
}
