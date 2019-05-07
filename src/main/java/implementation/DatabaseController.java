/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementation;

import io.swagger.model.AccessTokenCheckResponse;
import io.swagger.model.LoginRequest;
import io.swagger.model.Token;
import io.swagger.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Anders
 */
public class DatabaseController {
    
    // Map used to sync with object if same username is being requested access on.
    // Using Collections.synchronizedMap to ensure each thread have an up to date view on the map.
    private final Map<String, Object> objectLock = Collections.synchronizedMap(new HashMap());
    private static DatabaseController instance;
    private static final Object threadLock = new Object();
    
    public static DatabaseController getInstance() throws SQLException {
        if (instance == null) {
            synchronized(threadLock){
                if(instance == null){
                    instance = new DatabaseController();
                }
            }
        }
        return instance;
    }
    
    private Token getAccessTokenSync(Connection con, int userid) throws SQLException, LoginException {
        //Check if we already have an access token
        String checkAlreadyExistingToken = "SELECT * FROM public.tokens WHERE user_id = ?;";
        Token token;
        try (PreparedStatement existingToken = con.prepareStatement(checkAlreadyExistingToken)) {
            existingToken.setInt(1, userid);
            ResultSet existingRS = existingToken.executeQuery();
            token = new Token();
            if (existingRS.next()) {
                ZonedDateTime times = ZonedDateTime.parse(existingRS.getString("expires"));
                if (times.isBefore(ZonedDateTime.now())) {
                    String testInsert = "DELETE FROM public.tokens WHERE token = ?;";
                    try (PreparedStatement deleteStatement = con.prepareStatement(testInsert)) {
                        deleteStatement.setString(1, existingRS.getString("token"));
                        deleteStatement.executeUpdate();
                    }
                } else {
                    token.setAccessToken(existingRS.getString("token"));
                    token.setExpires(existingRS.getString("expires"));
                    existingToken.close();
                    return token;
                }
            }
            RandomString session = new RandomString(200);
            token.setAccessToken(session.nextString());
            LocalDate localDate = LocalDate.now().plusDays(1);
            LocalTime localTime = LocalTime.now();
            OffsetDateTime datetime = OffsetDateTime.of(LocalDateTime.of(localDate, localTime), ZoneOffset.UTC);
            token.setExpires(datetime.toString());
            String insertTokenSQL = "INSERT INTO public.tokens (token, user_id, expires) VALUES (?, ?, ?);";
            try (PreparedStatement tokenStatement = con.prepareStatement(insertTokenSQL)) {
                tokenStatement.setString(1, token.getAccessToken());
                tokenStatement.setInt(2, userid);
                tokenStatement.setString(3, datetime.toString());
                tokenStatement.executeUpdate();
            }
        }
        return token;
    }

    /**
     *
     * @param request LoginRequest
     * @param con Connection
     * @return Token
     * @throws SQLException
     * @throws LoginException
     */
    public Token getAccessToken(LoginRequest request, Connection con) throws SQLException, LoginException {
        String sql = "SELECT * FROM public.users WHERE username = ?;";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, request.getUsername());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if (checkPassword(request.getPassword(), rs.getString("password"))) {
                    Token token;
                    int userid = rs.getInt("id");
                    String username = request.getUsername();
                    Object lock = objectLock.get(username);
                    //no need to sync objectlock if object already exists. But double check needed
                    if(lock == null){
                        synchronized(objectLock){
                            //check again if it exists now that we are in sync
                            Object checkLock = objectLock.get(username);
                            if (checkLock == null) {
                                Object newLock = new Object();
                                objectLock.put(username, newLock);
                                lock = newLock;
                            }else{
                                lock = checkLock;
                            }
                        }
                    }
                    synchronized (lock) {
                        token = getAccessTokenSync(con, userid);
                    }
                    return token;
                }
            }
        }
        throw new LoginException("Wrong username or password provided");

    }

    /**
     * Method checks if token is correct and is still valid, if not, 
     * delete the token and return token was not valid.
     * @param token to check
     * @param con connection to use
     * @return AccessTokenCheckResponse 
     * @throws SQLException 
     */
    public AccessTokenCheckResponse checkAccessToken(Token token, Connection con) throws SQLException {
        AccessTokenCheckResponse response = new AccessTokenCheckResponse();
        response.setCanAccess(false);
        String sql = "SELECT * FROM tokens WHERE token = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, token.getAccessToken());
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            ZonedDateTime times = ZonedDateTime.parse(rs.getString("expires"));
            if (times.isBefore(ZonedDateTime.now())) {
                String testInsert = "DELETE FROM tokens WHERE token = ?;";
                PreparedStatement deleteStatement = con.prepareStatement(testInsert);
                deleteStatement.setString(1, rs.getString("token"));
                deleteStatement.executeUpdate();
            } else {
                response.setCanAccess(true);
            }
        }
        rs.close();
        statement.close();
        return response;
    }

    /**
     * Creates a user 
     * @param user to create
     * @param con connection to use
     * @throws SQLException 
     */
    public void createUser(User user, Connection con) throws SQLException {
        String testInsert = "INSERT INTO users (username, password) VALUES (?, ?);";
        PreparedStatement statement = con.prepareStatement(testInsert);
        statement.setString(1, user.getUsername());
        statement.setString(2, hashPassword(user.getPassword()));
        statement.executeUpdate();
        statement.close();
    }

    /**
     * Hash a given plaintext password
     * @param password_plaintext to hash
     * @return hashed password
     */
    private String hashPassword(String password_plaintext) {
        String salt = BCrypt.gensalt(12);
        String hashed_password = BCrypt.hashpw(password_plaintext, salt);

        return (hashed_password);
    }

    /**
     * This method can be used to verify a computed hash from a plaintext (e.g.
     * during a login request) with that of a stored hash from a database. The
     * password hash from the database must be passed as the second variable.
     *
     * @param password_plaintext The account's plaintext password, as provided
     * during a login request
     * @param stored_hash The account's stored password hash, retrieved from the
     * authorization database
     * @return boolean - true if the password matches the password of the stored
     * hash, false otherwise
     */
    private boolean checkPassword(String password_plaintext, String stored_hash) {
        boolean password_verified = false;

        if (null == stored_hash || !stored_hash.startsWith("$2a$")) {
            throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");
        }

        password_verified = BCrypt.checkpw(password_plaintext, stored_hash);

        return (password_verified);
    }
    
}
