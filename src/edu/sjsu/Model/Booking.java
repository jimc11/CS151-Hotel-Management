package edu.sjsu.Model;

import java.time.LocalDate;

/**
 * Class used to help with booking rooms
 */
public class Booking
{
    Room room;
    User user;
    BookingDate bookingDates;

    /**
     * Constructor to update variables
     * @param user
     * @param room
     * @param startDate
     * @param endDate
     */
    public Booking(User user, Room room, LocalDate startDate, LocalDate endDate)
    {
        this.user = user;
        this.room = room;
        this.bookingDates = new BookingDate(startDate, endDate);
    }

    /**
     * @return room
     */
    public Room getRoom()
    {
        return this.room;
    }

    /**
     * @return user
     */
    public User getUser()
    {
        return this.user;
    }

    /**
     * @return booking dates
     */
    public BookingDate getBookingDates()
    {
        return this.bookingDates;
    }

    @Override
    public String toString()
    {
        return "User: " + this.getUser().toString() + "Room: " + this.getRoom().toString() + "Booked Dates: " + this.getBookingDates().toString();
    }
}
