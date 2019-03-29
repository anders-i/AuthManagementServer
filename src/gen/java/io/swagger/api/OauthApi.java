package io.swagger.api;

import io.swagger.model.*;
import io.swagger.api.OauthApiService;
import io.swagger.api.factories.OauthApiServiceFactory;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;

import io.swagger.model.AccessTokenCheckResponse;
import io.swagger.model.LoginRequest;
import io.swagger.model.Token;

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

@Path("/oauth")


@io.swagger.annotations.Api(description = "the oauth API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2019-03-19T08:38:28.039Z")
public class OauthApi  {
   private final OauthApiService delegate;

   public OauthApi(@Context ServletConfig servletContext) {
      OauthApiService delegate = null;

      if (servletContext != null) {
         String implClass = servletContext.getInitParameter("OauthApi.implementation");
         if (implClass != null && !"".equals(implClass.trim())) {
            try {
               delegate = (OauthApiService) Class.forName(implClass).newInstance();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         } 
      }

      if (delegate == null) {
         delegate = OauthApiServiceFactory.getOauthApi();
      }

      this.delegate = delegate;
   }

    @POST
    @Path("/checkAccessToken")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "To check an access token it must have been recieved from /oauth/requestAccessToken ", response = AccessTokenCheckResponse.class, tags={ "Delegation protocol", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = AccessTokenCheckResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Void.class) })
    public Response checkAccessToken(@ApiParam(value = "check access token" ,required=true) Token body
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.checkAccessToken(body,securityContext);
    }
    @POST
    @Path("/requestAccessToken")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "To request an access token, the client obtains authorization from the resource owner.  The authorization is expressed in the form of an authorization grant, which the client uses to request the access token. See https://tools.ietf.org/html/rfc6749#section-4 for more detail. ", response = Token.class, tags={ "Delegation protocol", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = Token.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Void.class) })
    public Response requestAccessToken(@ApiParam(value = "Request an access token" ,required=true) LoginRequest body
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.requestAccessToken(body,securityContext);
    }
}
