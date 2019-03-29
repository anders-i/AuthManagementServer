/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementation;

/**
 *
 * @author Anders
 */
public class LoginException extends Exception{
    /**
     * Exception used when login was unsuccessfull
     * @param message 
     */
    public LoginException(String message){
        super(message);
    }
}
