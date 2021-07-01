package edu.sjsu.Messages;

public class LoginSubmitMessage implements Message{

    private String username;
    private char [] password;

    public LoginSubmitMessage(String username, char [] password){
        this.username = username;
        this.password = password;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        String s="";
        for (char ch: password)
            s += ch;
        return s;
    }


}
