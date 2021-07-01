package edu.sjsu.Model;

import java.time.LocalDate;

// TODO should we merge users[] and available[] into one object called "Booking" or something?  May be that would be unnecessary

/**
 * This class represents a room in a hotel
 */
public class Room {

    int number;
    String type;
    double pricePerNight;
    int beds;
    boolean [] available; // availability for the next 365 days
    User[] users; // users that occupy the rooms for the next 365 days

    public Object[] toArray(){
        Object[] objs = new Object[]{(Integer)beds,(Double)pricePerNight,type,(Integer)number};
        return  objs;
    }

    /**
     * Constructor for a Room given a room num, type, price and num of beds
     * @param number
     * @param type
     * @param pricePerNight
     * @param beds
     */
    public Room(int number, String type, double pricePerNight, int beds) {
        this.number = number;
        this.type = type;
        this.pricePerNight = Math.round(pricePerNight*100.0)/100.0;
        this.beds = beds;
        this.available = new boolean [365];
        for (int i = 0; i < 365; i++)
            this.available[i] = true;
        this.users = new User[365];
    }

    /**
     * Constructor for a room given room num, type, price, num of beds, and availability
     * @param number
     * @param type
     * @param pricePerNight
     * @param beds
     * @param available
     */
    public Room(int number, String type, double pricePerNight, int beds, boolean [] available) {
        this.number = number;
        this.type = type;
        this.pricePerNight = Math.round(pricePerNight*100.0)/100.0;
        this.beds = beds;
        this.available = available;
        this.users = new User[365];
    }

    /**
     * Get a room number
     * @return room number
     */
    public int getNumber(){
        return this.number;
    }

    /**
     * This returns a room's availability
     * @return available
     */
    public boolean [] getAvailable(){
        return this.available;
    }

    @Override
    public String toString() {
        LocalDate now = LocalDate.now();
        String s = "Room Number: " + this.number + "\n" + "Type: " + this.type + "\n" + "Price: $" + this.pricePerNight + "\n" + "Beds: " + this.beds + "\n" + "Availability: ";
        for (int i = 0; i < 365; i++){
            LocalDate date = now.plusDays(i);
            s+= date.getMonth() + " " + date.getDayOfMonth() +", "+ date.getYear() + ": ";
            if (this.available[i])
                s+= "available, ";
            else s+= "booked, ";
        }
        return s;
    }

    /**
     * Set a room to be available
     * @param b boolean of availability to change
     */
    public void setAvailable(boolean [] b) {
        this.available = b;
    }

    /**
     * Update a room to be available given a specific day
     * @param b boolean of availability to change
     * @param day of day to change
     */
    public void setAvailable(boolean b, int day ){
        this.available[day] = b;
    }

    /**
     * update availability of room given a start and end day and user
     * @param b
     * @param start
     * @param end
     * @param user
     */
    public void setAvailable(boolean b, int start, int end, User user ){
        for (int i = start; i < end; i++) {
            this.available[i] = b;
            this.users[i] = user;
        }
    }

    /**
     * Get the type of a Room
     * @return type of the Room
     */
    public String getType() {
        return this.type;
    }
} // end class
