package org.wj.prajumsook.eshop.service;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Provider
public class ApplicationErrorMapper implements ExceptionMapper<Exception> {

  @Override
  public Response toResponse(Exception exception) {
    int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
    if (exception instanceof WebApplicationException) {
      statusCode = ((WebApplicationException) exception).getResponse().getStatus();
    }

    ObjectMapper mapper = new ObjectMapper();
    ObjectNode error = mapper.createObjectNode();
    error.put("exceptionType", exception.getClass().getName());
    error.put("statusCode", statusCode);
    error.put("error", (exception.getMessage() != null) ? exception.getMessage() : "undefined");

    return Response.status(statusCode).entity(error).build();
  }

}
