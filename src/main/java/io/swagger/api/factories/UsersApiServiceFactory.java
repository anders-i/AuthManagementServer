package io.swagger.api.factories;

import io.swagger.api.UsersApiService;
import io.swagger.api.impl.UsersApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2019-04-15T09:56:43.257Z")
public class UsersApiServiceFactory {
    private final static UsersApiService service = new UsersApiServiceImpl();

    public static UsersApiService getUsersApi() {
        return service;
    }
}
