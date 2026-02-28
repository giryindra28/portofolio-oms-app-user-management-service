package org.portfolio.giry.oms.Exceptions;

import jakarta.validation.ValidationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.portfolio.giry.oms.dto.ObjectResponse;
import org.portfolio.giry.oms.dto.ResponseObject;

@Provider
public class ValidationExceptions implements ExceptionMapper<ValidationException> {

    @Override
    public Response toResponse(ValidationException e) {
        ObjectResponse<?> response = new ObjectResponse<>(new ResponseObject("4000",e.getMessage()),null);
        return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
    }
}
