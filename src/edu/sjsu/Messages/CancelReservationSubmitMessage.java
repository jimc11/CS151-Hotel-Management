package edu.sjsu.Messages;

import edu.sjsu.Messages.Message;
import edu.sjsu.Model.User;

/**
 * Message for cancelling when submit is selected
 */
public class CancelReservationSubmitMessage implements Message {
    int[] start;
    int[] end;
    int roomNum ;
    User user;

    /**
     * Constructor to update the variables
     * @param start - start date
     * @param end - end date
     * @param roomNum - room number
     * @param user - user
     */
    public CancelReservationSubmitMessage(int[] start, int[] end, int roomNum, User user)
    {
        this.start = start;
        this.end =end;
        this.roomNum = roomNum;
        this.user = user;
    }

    /**
     * @return user
     */
    public User getUser() {
        return user;
    }

    /**
     * @return start date
     */
    public int[] getStart() {
        return start;
    }

    /**
     * @return end date
     */
    public int[] getEnd() {
        return end;
    }

    /**
     * @return room number
     */
    public int getRoomNum() {
        return this.roomNum;
    }
}
