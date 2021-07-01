package edu.sjsu.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//testing for userlist
class UserListTest {

    @Test //sees if it will add the user if credentials are valid
    void addUser() {
        UserList ul = new UserList();
        assertFalse(ul.addUser("Bob Smith", "password", "notreal"));
        assertTrue(ul.addUser("Bob Smith", "password", "password"));
    }

    @Test //Will search the current list to see if name is already there
    void nameExists() {
        User u1 = new User ("Bob Smith", "password");

        UserList ul = new UserList();
        ul.add(u1);
        assertFalse(ul.nameExists("Kobe Bryant"));
        assertTrue(ul.nameExists("Bob Smith"));
    }

    @Test //validates user login
    void validLogin() {
        User u1 = new User ("Bob Smith", "password");

        UserList ul = new UserList();
        ul.add(u1);
        assertTrue(ul.validLogin("Bob Smith", "password"));
        assertFalse(ul.validLogin("Bob Smith", "testing"));
    }
}
