package io.swagger.api.impl;

import ch.qos.logback.core.rolling.helper.TokenConverter;
import implementation.DataSource;
import implementation.DatabaseController;
import implementation.UsersServerImplementation;
import io.swagger.api.*;
import io.swagger.model.*;

import io.swagger.model.Token;
import io.swagger.model.UserArray;
import io.swagger.model.UserRequest;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2019-05-10T17:25:08.962Z")
public class UsersApiServiceImpl extends UsersApiService {

    @Override
    public Response createUser(UserRequest body, SecurityContext securityContext) throws NotFoundException {
        try (Connection con = DataSource.getInstance().getConnection()) {
            new DatabaseController().checkAccessToken(body.getToken(), con);
            new UsersServerImplementation().createUser(body.getUser() ,con);
            return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
        } catch (SQLException ex) {
            return Response.status(400).entity(ex.toString()).build();
        }
    }

    @Override
    public Response deleteUser(UserRequest body, SecurityContext securityContext) throws NotFoundException {
        try (Connection con = DataSource.getInstance().getConnection()) {
            new DatabaseController().checkAccessToken(body.getToken(), con);
            new UsersServerImplementation().deleteUser(body.getUser(), con);
            return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
        } catch (SQLException ex) {
            return Response.status(400).entity(ex.toString()).build();
        }
    }

    @Override
    public Response editUser(UserRequest body, SecurityContext securityContext) throws NotFoundException {
        try (Connection con = DataSource.getInstance().getConnection()) {
            new DatabaseController().checkAccessToken(body.getToken(), con);
            new UsersServerImplementation().editUser(body.getUser() ,con);
            System.out.println(body.getUser().getId() + " : ID HEEEEER");
            System.out.println(body.getUser().getPassword() + " : PASSWORD HEEEEER");
            System.out.println(body.getUser().getUsername() + " : USERNAME HEEEEER");
            System.out.println(body.getUser().getRights() + " : RIGHTS HEEEEER");
            return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
        } catch (SQLException ex) {
            return Response.status(400).entity(ex.toString()).build();
        }
    }

    @Override
    public Response getAllUsers(Token body, SecurityContext securityContext) throws NotFoundException {
        try (Connection con = DataSource.getInstance().getConnection()) {
            new DatabaseController().checkAccessToken(body, con);
            UserArray response = new UsersServerImplementation().getAllUsers(con);
            return Response.ok().entity(response).build();
        } catch (SQLException ex) {
            return Response.status(400).entity(ex.toString()).build();
        }
    }
}
