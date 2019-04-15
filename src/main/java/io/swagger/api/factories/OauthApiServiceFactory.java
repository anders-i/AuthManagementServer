package io.swagger.api.factories;

import io.swagger.api.OauthApiService;
import io.swagger.api.impl.OauthApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2019-04-15T09:56:43.257Z")
public class OauthApiServiceFactory {
    private final static OauthApiService service = new OauthApiServiceImpl();

    public static OauthApiService getOauthApi() {
        return service;
    }
}
