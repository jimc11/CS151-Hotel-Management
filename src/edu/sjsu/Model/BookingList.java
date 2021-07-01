package edu.sjsu.Model;

import java.util.*;

//List used to add and remove bookings

public class BookingList extends ArrayList<Booking>
{
    /**
     *
     * @param b - booking to add to list
     */
    public void addBooking(Booking b)
    {
        this.add(b);
    }

    /**
     * removing a booking
     * @param b - booking
     */
    public void removeBooking(Booking b)
    {
        this.remove(b);
    }


}
