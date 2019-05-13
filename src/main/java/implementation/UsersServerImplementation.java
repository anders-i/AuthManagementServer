/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementation;

import io.swagger.model.User;
import io.swagger.model.UserArray;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Anders
 */
public class UsersServerImplementation {

    public UserArray getAllUsers(Connection con) throws SQLException {
        return DatabaseController.getInstance().getAllUsers(con);
    }

    public void createUser(User user, Connection con) throws SQLException {
        DatabaseController.getInstance().createUser(user, con);
    }

    public void deleteUser(User user, Connection con) throws SQLException {
        DatabaseController.getInstance().deleteUser(user, con);
    }

    public void editUser(User user, Connection con) throws SQLException {
        DatabaseController.getInstance().editUser(user, con);
    }
    
}
