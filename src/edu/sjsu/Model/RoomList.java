package edu.sjsu.Model;

import edu.sjsu.DateException;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.time.*;


// TODO Check edge cases on all methods
// TODO Create error handling class for dates out of bound and other mistakes...?

// TODO how can we make the list update every night at midnight?  Eventlistener & LocalDate?
// TODO after 12AM update each day, store bookings in a database for records

// TODO add a User to each room listing!!!  Then, add a room to each user's list of booked rooms

// TODO add methods so that admins can manually remove/make/modify bookings...?

/* This is an arraylist of all of the rooms in the hotel.  it contains methods to
 *  check the availability of each room, and it can book any available room.
 */

/**
 * This is an arraylist of all of the rooms in the hotel.  it contains methods to
 * check the availability of each room, and it can book any available room.
 */
public class RoomList extends ArrayList<Room> {

    public static Map<String, Integer> monthToNum;
    static {
        monthToNum = new HashMap<>();
        monthToNum.put("January", 1);
        monthToNum.put("February", 2);
        monthToNum.put("March", 3);
        monthToNum.put("April", 4);
        monthToNum.put("May", 5);
        monthToNum.put("June", 6);
        monthToNum.put("July", 7);
        monthToNum.put("August", 8);
        monthToNum.put("September", 9);
        monthToNum.put("October", 10);
        monthToNum.put("November", 11);
        monthToNum.put("December", 12);
    }
    public BookingList bookingList;



    /**
     * updates each room's availability every night.
     */
    public void update(){
        for (int i = this.size()-1 ; i > 0; i--){
            if (i == 0){
                // TODO store the first column of this.availability and this.users in database of records
                // Record record = new Record(Room.get(i), date, user, etcc... );
            } else this.set(i,this.get(i-1)); // this swaps the indexes
        }
    }
    /**
     * determines whether the specified room is available within the specified dates
     * @param start
     * @param end
     * @param room
     * @return true if available, false otherwise
     * @throws DateException
     */
    public boolean checkAvailability(int[] start, int[] end, Room room) throws DateException{
        int [] dates = giveDates(start, end);
        if (dates == null)
            return false;
        int numDaysUntilStart = dates[0];
        int numDaysUntilEnd = dates[1];

        for (int i = numDaysUntilStart; i < numDaysUntilEnd; i++){
            if (!room.getAvailable()[i]){
                return false;
            }
        }
        return true;
    }

    /**
     * returns all rooms that are open within specified dates
     * @param start
     * @param days
     * @param roomType
     * @return ArrayList of rooms that are open within those dates
     * @throws DateException
     */
    public ArrayList<Room> checkAvailability(int[] start, int days, String roomType) throws DateException{

        LocalDate now = LocalDate.now();
        LocalDate dateBefore = LocalDate.of(start[2], start[0], start[1]); // year month day
        int numDaysUntilStart = (int)ChronoUnit.DAYS.between(now, dateBefore);
        int numDaysUntilEnd = days;
        LocalDate dateAfter= LocalDate.of(start[2], start[0], start[1]); // year month day
        dateAfter = dateAfter.plusDays(days);
        int [] end = new int[]{dateAfter.getMonthValue(),dateAfter.getDayOfMonth(),dateAfter.getYear()};
        ArrayList<Room> matchingType = new ArrayList<Room>();
        for (int i = 0; i < 100; i++)
        {

            if (this.get(i).getType().equals(roomType)){
                matchingType.add(this.get(i));
            }
        }
        ArrayList<Room> available = new ArrayList<Room>();
        for(Room r: matchingType) {
            if (!this.checkAvailability(start,end,r)) {
                continue;
            }
            available.add(r);
        }
        return available;
    }

    /**
     * returns all rooms that are open within specified dates
     * @param start
     * @param end
     * @return rooms available within specific dates
     * @throws DateException
     */
    public Room []  checkAvailability(int [] start, int [] end) throws DateException{ // month day year

        int count = 0;
        ArrayList<Room> matches = new ArrayList<Room>();

        for (int i = 0; i < 100; i++){
            if (checkAvailability(start, end, this.get(i))){ // is the room available between these days?
                matches.add(this.get(i));
            }
        }
        int i = 0;
        Room [] rm = new Room[matches.size()];
        for (Room r : matches) {
            rm[i++] = r;
        }
        return rm;
    }

    /**
     * returns the days in which the specified room is available
     * @param room
     * @return days of room availability
     */
    public LocalDate [] checkAvailability(Room room){
        int roomNumber = room.getNumber();
        boolean [] upcomingAvailability = this.get(roomNumber).getAvailable();
        ArrayList<LocalDate> availableDays = new ArrayList<LocalDate>();

        for (int i = 0; i < 365 ; i++){
            if (upcomingAvailability[i]){
                availableDays.add(LocalDate.now().plusDays(i));
            }
        }

        int i = 0;
        LocalDate [] ld = new LocalDate[availableDays.size()];
        for (LocalDate l : availableDays) {
            ld[i++] = l;
        }
        return ld;
    }

    /**
     * books a room given a start date, end date, room number, and specific user
     * @param start
     * @param end
     * @param roomNum
     * @param user
     * @return true if booked, false otherwise
     * @throws DateException
     */
    public boolean bookRoom(int[] start, int[] end, int roomNum, User user) throws DateException {
        int [] dates = giveDates(start, end);
        if (dates == null)
            return false;
        int numDaysUntilStart = dates[0];
        int numOfDaysUntilEnd = dates[1];

        Room room = this.get(roomNum);

        LocalDate startBooking = LocalDate.of(start[2], start[0], start[1]); // year month day
        LocalDate endBooking = LocalDate.of(end[2], end[0], end[1]);


        Booking b = new Booking(user, room, startBooking, endBooking);
        user.addRoom(b);
        bookingList.addBooking(b);


        if (checkAvailability(start,end,room)){
            this.get(room.getNumber()).setAvailable(false, numDaysUntilStart, numOfDaysUntilEnd, user );
            return true;
        } else {
            System.out.println("Unable to book room");
            return false;
        }
    }

    public void cancelRoom(int[] start, int[] end, int roomNum, User user) {

        LocalDate ld = LocalDate.now();
        LocalDate startDate = LocalDate.of(start[2], start[0], start[1]);
        int daysBetween = (int)ChronoUnit.DAYS.between(ld, startDate);
        this.get(roomNum).setAvailable(true, daysBetween);

        for (int i = 0 ; i < user.getRooms().size(); i++){

            if (start[0] == user.getRooms().get(i).getBookingDates().startDate.getMonthValue() &&
                    start[1]==  user.getRooms().get(i).getBookingDates().startDate.getDayOfMonth() &&
                    start[2] == user.getRooms().get(i).getBookingDates().startDate.getYear())
                user.getRooms().remove(i);
        }


        for(int i = 0; i < bookingList.size(); i++)
        {
            int roomNum2 = bookingList.get(i).getRoom().getNumber();
            if(roomNum == roomNum2)
            {
                if (bookingList.get(i).bookingDates.startDate == startDate)
                    bookingList.remove(bookingList.get(i));
            }
        }
    }

    /**
     * searches if a room is available given the month,day,year,number of days to book and type of room. If available returns the specific room #, otherwise -1.
     * @param month
     * @param day
     * @param year
     * @param numDays
     * @param type
     * @return room number if availble, -1 otherwise
     * @throws DateException
     */
    public int search(String month, String day, String year, String numDays, String type) throws DateException
    {
        int m = monthToNum.get(month);
        int d = Integer.parseInt(day);
        int y = Integer.parseInt(year);
        int nd = Integer.parseInt(numDays);
        boolean check;

        for(int i = 0; i < this.size(); i++)
        {
            if(this.get(i).getType().equals(type))
            {
                check = checkAvailability(new int[]{m, d, y}, new int[]{m, d + nd, y}, this.get(i));
                if(check)
                {
                    return i;
                }
            }
        }
        return -1;
    }



    // ----- support functions -----

    /**
     * support function for checking availability of rooms
     * @param start
     * @param end
     * @return
     * @throws DateException
     */
    private static int[] giveDates(int [] start, int [] end) throws DateException{
        LocalDate now = LocalDate.now();
        LocalDate dateBefore = LocalDate.of(start[2], start[0], start[1]); // year month day
        LocalDate dateAfter = LocalDate.of(end[2], end[0], end[1]);

        if (dateBefore.isBefore(now) || dateAfter.isBefore(now)) {
            throw new DateException(true);
        }
        if (dateBefore.isAfter(now.plusDays(366)) || dateAfter.isAfter(now.plusDays(366))) {
            throw new DateException(false);
        }

        int numDaysUntilStart = (int)ChronoUnit.DAYS.between(now, dateBefore);
        int noOfDaysUntilEnd = (int)ChronoUnit.DAYS.between(now, dateAfter);
        return new int[]{numDaysUntilStart, noOfDaysUntilEnd};
    }


    // shows all rooms

    /**
     * support function to show all rooms in the Hotel
     */
    public void showRooms() {
        for (int j = 0; j < this.size(); j++) {
            System.out.println("-------------------------------");
            System.out.println(this.get(j));
        }
    }
    // helps with making test cases

    /**
     * Generate the availability of rooms in the Hotel
     * @return
     */
    private boolean [] generateAvailability(){
        boolean [] avail = new boolean[365];
        for (int i = 0; i < 90; i++){
            avail[i] = Math.floor(Math.random() * 8) == 0;
        }
        for (int i = 0; i < 180; i++){
            avail[i] = Math.floor(Math.random() * 3) == 0;
        }
        for (int i = 180; i < 365; i++){
            avail[i] = true;
        }
        return avail.clone();
    }

    // generates test cases

    /**
     * Constructor for a a list of Rooms in a Hotel, do not use to construct an instance,
     * use the public method getInstance() instead.
     */

    private RoomList(BookingList bookingList){
        this.bookingList = bookingList;

        Room room;
        for (int i = 0; i < 100; i++){
            if (i < 25){
                room = new Room(i,"Single Room",Math.random()*120 + 80, 1, generateAvailability());
            }
            else if (i >=25 && i < 50){
                room = new Room(i,"Family",Math.random()*150 + 200, (int) (1 + Math.floor(Math.random() * 4)), generateAvailability());
            }
            else if (i >=50 && i < 75){
                room = new Room(i,"Suite",Math.random()*500 + 350, 2, generateAvailability());
            }
            else {
                room = new Room(i,"Executive Suite",Math.random()*1000 + 1000, 1, generateAvailability());
            }
            this.add(room);
        }
    }

    /**
     * returns a single unique instance of Room List since we want to make sure there
     * is only one instance at any time
     * @return instance of RoomList
     */
    private static RoomList instance = new RoomList(new BookingList()) ;
    public static RoomList getInstance(){
        return instance;
    }
} // end class

    // ----- test code driver -----
//    public static void main(String[] args) {
//
//        LocalDate now = LocalDate.now();
//        int [] today = new int[]{now.getMonthValue(),now.getDayOfMonth(),now.getYear()};
//        int [] fewDaysFromToday = new int[]{now.getMonthValue(),now.getDayOfMonth()+2,now.getYear()};
//
//        User testUser = new User("James Crowley");
//
//        System.out.println("----- All rooms -----");
//        RoomList rl = new RoomList();
//        rl.showRooms();
//
//        System.out.println("\n----- checkAvailability(Room) -----");
//        System.out.println("Test room 99: ");
//        LocalDate [] datesFor99 = rl.checkAvailability(rl.get(99)); // seems to be working as intended
//        for(LocalDate ld : datesFor99)
//            System.out.print(ld.toString() + ", ");
//        System.out.println();
//
//        System.out.println("\n----- checkAvailability(Date, Date) -----");
////        System.out.println("Available rooms for the dates 11/24/2020 - 11/28/2020: ");
////        Room [] rooms1 = rl.checkAvailability(today,fewDaysFromToday);
////        for (Room r: rooms1)
////            System.out.println(r.toString());
////
////        System.out.println("\nAvailable rooms for the dates 5/10/2021 - 5/13/2021: ");
////        Room [] rooms2 = rl.checkAvailability(new int[]{5,10,2021}, new int[]{5,13,2021});
////        for (Room r: rooms2)
////            System.out.println(r.toString());
//
////        System.out.println("\nAvailable rooms for the dates 11/19/2021 - 11/21/2021: ");
////        Room [] rooms3 = rl.checkAvailability(new int[]{11,19,2021},new int[]{11,21,2021});
////        for (Room r: rooms3)
////            System.out.println(r.toString());
//
//        // ----- checkAvailability(Date, Date, Room) -----
//        System.out.println("\nIs room 99 available for the dates 11/25/2020 - 11/26/2020? ");
//      //  boolean avail2 = rl.checkAvailability(today,fewDaysFromToday, rl.get(99) );
//       // if (avail2) System.out.println("Yes.");
//       // else System.out.println("No.");
//
//        System.out.println("\nIs room 99 available for the dates 11/19/2021 - 11/21/2021? ");
//       // boolean avail = rl.checkAvailability(new int[]{11,19,2021},new int[]{11,21,2021}, rl.get(99) );
//       // if (avail) System.out.println("Yes.");
//       // else System.out.println("No.");
//
//        // ----- bookRoom -----
//        System.out.println("\n----- bookRoom(Date, Date, Room) -----");
//        System.out.println("Attempting to book room 99...");
//       // rl.bookRoom(new int[]{11,19,2021}, new int[]{11,21,2021}, 99, testUser);
//        System.out.println("\nWas the booking successful? ");
//        //boolean avail3 = rl.checkAvailability(new int[]{11,19,2021},new int[]{11,21,2021}, rl.get(99) );
//       // if (!avail3) System.out.println("Yes.");
//       // else System.out.println("No.");
//    }
//}
////    public void showBookings()
////    {
////        for(int i = 0; i < this.size(); i++)
////        {
////            System.out.println(this.get(i).toString());
////        }
////    }
////
////
////    public void showBookedRooms() {
//////        ArrayList<Room> occupied = new ArrayList<>();
//////        for (int i = 0; i < this.size(); i++) {
//////            if (this.get(i).available == false) {
//////                occupied.add(this.get(i));
//////            }
//////        }
//////        for (int j = 0; j < occupied.size(); j++) {
//////            System.out.println(occupied.get(j));
//////        }
////
////    }
