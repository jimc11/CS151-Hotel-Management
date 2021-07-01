package edu.sjsu.Messages;

import edu.sjsu.Model.User;

/**
 * message for time and type of room being booked
 */
public class TimeAndTypeMessage implements Message{
    String [] mdytt;
    User user;

    /**
     * Constructor to update variables
     * @param month
     * @param day
     * @param year
     * @param time
     * @param type
     * @param user
     */
    public TimeAndTypeMessage(String month, String day, String year, String time, String type, User user) {
        this.mdytt = new String[]{month,day,year,time,type};
        this.user =user;

    }

    /**
     * @return variables printed
     */
    public String[] getMdytt() {
        return mdytt;
    }

    /**
     * @return user
     */
    public User getUser() {
        return user;
    }


}
