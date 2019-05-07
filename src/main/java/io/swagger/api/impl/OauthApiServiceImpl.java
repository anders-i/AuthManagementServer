package io.swagger.api.impl;

import implementation.DataSource;
import implementation.LoginException;
import implementation.OAuthServerImplementation;
import io.swagger.api.*;
import io.swagger.model.*;

import io.swagger.model.AccessTokenCheckResponse;
import io.swagger.model.LoginRequest;
import io.swagger.model.Token;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
import org.joda.time.DateTime;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2019-04-15T09:56:43.257Z")
public class OauthApiServiceImpl extends OauthApiService {
    @Override
    public Response checkAccessToken(Token body, SecurityContext securityContext) throws NotFoundException {
        try(Connection con = DataSource.getInstance().getConnection()){
            AccessTokenCheckResponse atcr = new OAuthServerImplementation().checkAccessToken(body, con);
            if(atcr.isCanAccess()){
                return Response.ok().entity(atcr).build();
            }else{
                return Response.status(401).build();
            }
        } catch (SQLException ex) {
            Logger.getLogger(OauthApiServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.serverError().build();
    }
    @Override
    public Response requestAccessToken(LoginRequest body, SecurityContext securityContext) throws NotFoundException {
        try (Connection con = DataSource.getInstance().getConnection()) {
            return Response.ok().entity(new OAuthServerImplementation().requestAccessToken(body, con)).build();
        } catch (SQLException ex) {
            Logger.getLogger(OauthApiServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LoginException ex) {
            Logger.getLogger(OauthApiServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.serverError().build();
    }
}
