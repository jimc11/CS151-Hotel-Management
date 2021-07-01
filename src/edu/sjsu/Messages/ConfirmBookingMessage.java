package edu.sjsu.Messages;

import edu.sjsu.Messages.Message;
import edu.sjsu.Model.Room;
import edu.sjsu.Model.User;

/**
 * Message to confirm the booking
 */
public class ConfirmBookingMessage implements Message {

    private int [] start;
    private int duration;
    private Room room;
    private User user;

    /**
     * constructor to initialize the variables
     * @param start - start date
     * @param duration - length of stay
     * @param room - room selected
     * @param user - user logged in
     */
    public ConfirmBookingMessage(int[] start, int duration, Room room, User user) {
        this.start = start;
        this.duration = duration;
        this.room = room;
        this.user = user;
    }

    /**
     * Getter method to return the user
     * @return user
     */
    public User getUser() {
        return user;
    }

    /**
     * Getter method to return the duration
     * @return duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * getter method to get start date
     * @return start
     */
    public int[] getStart() {
        return start;
    }

    /**
     * getter method to return room
     * @return room
     */
    public Room getRoom() {
        return room;
    }

}
