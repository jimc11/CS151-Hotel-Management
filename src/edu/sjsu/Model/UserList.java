package edu.sjsu.Model;

import java.util.*;

/**
 * This class stores a list of all users, is an extension of ArrayList
 */
public class UserList extends ArrayList<User>
{

    /**
     * Adds a user to the UserList
     * @param name of User
     * @param password of User
     * @param password2 which checks if a user's password is correct
     */
    public boolean addUser(String name, String password, String password2){

        if (!password.equals(password2)){
            //System.out.println("password mismatch");
            return false;
        }
        if (this.nameExists(name)) {
            //System.out.println("name exists");
            return false;
        }
        this.add(new User(name,password));
        add(new User(name,password));
        return true;
    }

    /**
     * This checks to see if a User exists in the list
     * @param name to check
     * @return true if user is in list, false if not
     */
    public boolean nameExists(String name){
        for (User u: this)
            if (u.getName().equals(name))
                return true;

        return false;
    }

    /**
     * This checks to see if a login is valid
     * @param name of User to check
     * @param password of User to check
     * @return true if it is a valid login, false otherwise
     */
    public boolean validLogin(String name, String password){
        for (User u: this)
            if (u.getName().equals( name) && u.getPassword().equals( password)){
                return true;
            }
        return false;
    }

    /**
     * This checks to see if a login is valid
     * @param name of User to check
     * @param password of User to check
     * @return true if it is a valid login, false otherwise
     */
    public User login(String name, String password){
        for (User u: this)
            if (u.getName().equals( name) && u.getPassword().equals( password)){
                return u;
            }
        return null;
    }

    /**
     * Show all users in the User List
     */
    public void showUsers()
    {
        for(int i = 0; i < this.size(); i++)
        {
            this.get(i).toString();
        }
    }
}
