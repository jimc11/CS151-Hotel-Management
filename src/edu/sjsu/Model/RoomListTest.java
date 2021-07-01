package edu.sjsu.Model;

import edu.sjsu.DateException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomListTest {

    //RoomList tester to see if we can check availibilty and book room
    @Test
    void checkAvailability() throws DateException {

        boolean [] avail = new boolean[365];
        for (int i = 0; i < 90; i++){
            avail[i] = false;
        }
        for (int i = 90; i < 180; i++){
            avail[i] = Math.floor(Math.random() * 3) == 0;
        }
        for (int i = 180; i < 365; i++){
            avail[i] = true;
        }
        Room r1 = new Room(12,"Suite",Math.random()*500 + 350, 2, avail);

        RoomList rlist = RoomList.getInstance();
        rlist.add(r1);
        assertFalse(rlist.checkAvailability(new int[]{1, 19, 2021}, new int[]{1, 21, 2021}, r1));
        assertTrue(rlist.checkAvailability(new int[]{9, 1, 2021}, new int[]{9, 7, 2021}, r1));
        assertTrue(rlist.checkAvailability(new int[]{11, 19, 2021}, new int[]{11, 21, 2021}, r1));

    }

    @Test
    void bookRoom() throws DateException {
        boolean [] avail = new boolean[365];
        for (int i = 0; i < 180; i++){
            avail[i] = false;
        }
        for (int i = 180; i<365; i++){
            avail[i] = true;
        }
        Room r1 = new Room(5,"Single Room",Math.random()*120 + 80, 1, avail);
        User u1 = new User ("Bob Smith");
        RoomList rlist = RoomList.getInstance();
        rlist.add(r1);

        assertFalse(rlist.bookRoom(new int[]{1, 5, 2021}, new int[]{1, 10, 2021}, r1.number, u1)); //date unavailable, so cant book room
        assertTrue(rlist.bookRoom(new int[]{10, 5, 2021}, new int[]{10, 8, 2021}, r1.number, u1));


    }
}
