package edu.sjsu.Messages;

import edu.sjsu.Model.User;

/**
 * Message for submitting a booking
 */
public class SubmitBookingMessage implements Message {
    int[] dateStart;
    int endDate;
    int roomNum;
    User user;
    String type;

    /**
     * Updates variables
     * @param dateStart - start date selected
     * @param endDate - end date selected
     * @param roomNum - room number of room selected
     * @param user - user
     * @param type - room type
     */
    public SubmitBookingMessage(int[] dateStart, int endDate, int roomNum, User user, String type) {
        this.dateStart = dateStart;
        this.endDate = endDate;
        this.roomNum = roomNum;
        this.user = user;
        this.type = type;
    }

    /**
     * returns the start date
     * @return - start date
     */
    public int[] getDateStart() {
        return dateStart;
    }

    /**
     * returns end date
     * @return end date
     */
    public int getEndDate() {
        return endDate;
    }

    /**
     * Returns type of room
     * @return type
     */
    public String getType(){
        return type;
    }

    /**
     * returns room number
     * @return roomNum
     */
    public int getRoomNum() {
        return roomNum;
    }

    /**
     * returns logged in user
     * @return user
     */
    public User getUser() {
        return user;
    }
}
