/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementation;

import io.swagger.model.AccessTokenCheckResponse;
import io.swagger.model.LoginRequest;
import io.swagger.model.Token;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author herin
 */
public class OAuthServerImplementation {
    
    public AccessTokenCheckResponse checkAccessToken(Token token, Connection con) throws SQLException{
        return DatabaseController.getInstance().checkAccessToken(token, con);
    }
    
    public Token requestAccessToken(LoginRequest request, Connection con) throws SQLException, LoginException{
        return DatabaseController.getInstance().getAccessToken(request, con);
    }
    
}
