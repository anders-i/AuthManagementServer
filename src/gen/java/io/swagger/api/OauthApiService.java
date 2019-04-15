package io.swagger.api;

import io.swagger.api.*;
import io.swagger.model.*;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import io.swagger.model.AccessTokenCheckResponse;
import io.swagger.model.LoginRequest;
import io.swagger.model.Token;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2019-04-15T09:56:43.257Z")
public abstract class OauthApiService {
    public abstract Response checkAccessToken(Token body,SecurityContext securityContext) throws NotFoundException;
    public abstract Response requestAccessToken(LoginRequest body,SecurityContext securityContext) throws NotFoundException;
}
