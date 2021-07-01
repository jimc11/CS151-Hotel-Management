package edu.sjsu.Messages;

import edu.sjsu.Messages.Message;

/**
 * Message for creating an account
 */
public class CreateAccountSubmitMessage implements Message {
    private String username;
    private char [] password;
    private char [] password2;

    /**
     * constructor to update values
     * @param username - username entered
     * @param password - password
     * @param password2 - confirm password
     */
    public CreateAccountSubmitMessage(String username, char [] password, char [] password2){
        this.username = username;
        this.password = password;
        this.password2 = password2;
    }

    public String getUsername(){
        return username;
    }

    /**
     * Getter method which adds original password to a string
     * @return password string
     */
    public String getPassword(){
        String s="";
        for (char ch: password)
            s += ch;
        return s;
    }

    /**
     * @return confirm password string
     */
    public String getPassword2(){
        String s="";
        for (char ch: password2)
            s += ch;
        return s;
    }
}
