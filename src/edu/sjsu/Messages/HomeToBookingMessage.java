package edu.sjsu.Messages;

import edu.sjsu.Model.User;

/**
 * Message which goes to book room
 */
public class HomeToBookingMessage implements Message {
    User user;

    /**
     * constructor to update user
     * @param user
     */
    public HomeToBookingMessage(User user) {

        this.user= user;
    }

    /**
     * @return user
     */
    public User getUser() {
        return user;
    }
}
