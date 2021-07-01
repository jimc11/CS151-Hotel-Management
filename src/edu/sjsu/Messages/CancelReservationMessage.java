package edu.sjsu.Messages;

import edu.sjsu.Model.User;

/**
 * Message used when reservation is cancelled
 */
public class CancelReservationMessage implements Message
{
    User user;
    public CancelReservationMessage(User user)
    {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
