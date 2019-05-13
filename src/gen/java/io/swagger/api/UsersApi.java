package io.swagger.api;

import io.swagger.model.*;
import io.swagger.api.UsersApiService;
import io.swagger.api.factories.UsersApiServiceFactory;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;

import io.swagger.model.Token;
import io.swagger.model.UserArray;
import io.swagger.model.UserRequest;

import java.util.Map;
import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.servlet.ServletConfig;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;
import javax.validation.constraints.*;

@Path("/users")


@io.swagger.annotations.Api(description = "the users API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2019-05-10T17:25:08.962Z")
public class UsersApi  {
   private final UsersApiService delegate;

   public UsersApi(@Context ServletConfig servletContext) {
      UsersApiService delegate = null;

      if (servletContext != null) {
         String implClass = servletContext.getInitParameter("UsersApi.implementation");
         if (implClass != null && !"".equals(implClass.trim())) {
            try {
               delegate = (UsersApiService) Class.forName(implClass).newInstance();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         } 
      }

      if (delegate == null) {
         delegate = UsersApiServiceFactory.getUsersApi();
      }

      this.delegate = delegate;
   }

    @POST
    @Path("/createUser")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "Create a desired user in the system ", response = Void.class, tags={ "User management", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Void.class) })
    public Response createUser(@ApiParam(value = "Create user" ,required=true) UserRequest body
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.createUser(body,securityContext);
    }
    @DELETE
    @Path("/deleteUser")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "Delete a desired user in the system ", response = Void.class, tags={ "User management", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Void.class) })
    public Response deleteUser(@ApiParam(value = "Delete user" ,required=true) UserRequest body
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.deleteUser(body,securityContext);
    }
    @POST
    @Path("/editUser")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "Edit a desired user in the system ", response = Void.class, tags={ "User management", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Void.class) })
    public Response editUser(@ApiParam(value = "Edit user" ,required=true) UserRequest body
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.editUser(body,securityContext);
    }
    @POST
    @Path("/GetAllUsers")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "Get all users in the system ", response = UserArray.class, tags={ "User management", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = UserArray.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Void.class) })
    public Response getAllUsers(@ApiParam(value = "Get all users" ,required=true) Token body
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.getAllUsers(body,securityContext);
    }
}
