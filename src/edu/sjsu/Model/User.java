package edu.sjsu.Model;

import edu.sjsu.Model.Booking;

import java.util.*;

/**
 * User class represents a User in the Hotel
 */
public class User
{
    private String name;
    private String password;
    ArrayList<Booking> bookedRooms = new ArrayList<>();

    /**
     * Constructor for a User with just a name
     * @param name
     */
    public User(String name){
        this.name = name;
    }

    /**
     * Constructor for a User with a name and associated password
     * @param name
     * @param password
     */
    public User(String name,String password)
    {
        this.name = name;
        this.password = password;
        this.bookedRooms = new ArrayList<Booking>();
    }

    /**
     * Constructor for a User with name, associated password, and a list of their booked Rooms
     * @param name
     * @param password
     * @param bookedRooms
     */
    public User(String name,String password,ArrayList<Booking> bookedRooms)
    {
        this.name = name;
        this.password = password;
        this.bookedRooms = bookedRooms;
    }

    /**
     * This method adds a room to the user's booked rooms list
     *
     * @param b is a booking that is passed to the method
     */
    public void addRoom(Booking b){
        bookedRooms.add(b);
    }


    @Override
    public String toString()
    {
        return name;
    }

    /**
     * This method prints out a user's bookings neatly
     */
    public void showUserBookings()
    {
        System.out.println("Name: " + this.toString());
        for(int i = 0; i < this.bookedRooms.size(); i++)
        {
            System.out.println("Booking " + (i + 1) + ":" + this.bookedRooms.get(i).toString());
        }
        System.out.println();
    }

    /**
     * Gets name of the user
     * @return name of the user
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets a user's password
     * @return password of a user
     */
    public String getPassword() {
        return this.password;
    }

    public ArrayList<Booking> getRooms() { return this.bookedRooms; }
}
