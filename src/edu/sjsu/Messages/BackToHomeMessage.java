package edu.sjsu.Messages;

import edu.sjsu.Model.User;

/**
 * Message which retreives the proper user to go to thier portal
 */
public class BackToHomeMessage implements Message {
    private User user;

    /**
     * updates the user variable depending on who's logged in
     * @param user - authenticated user
     */
    public BackToHomeMessage(User user) {
        this.user= user;
    }

    /**
     * getter method to return the user
     * @return user
     */
    public User getUser() {
        return user;
    }
}
