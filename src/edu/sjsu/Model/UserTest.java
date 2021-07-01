package edu.sjsu.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//test for User class methods
class UserTest {

    @Test //test to see if user's name will be returned properly
    void getName() {
        User u1 = new User ("Bob Smith", "password");
        String name = "Bob Smith";
        assertEquals(u1.getName(), name);

    }

    @Test //method which will compare two strings for password
    void getPassword() {
        User u1 = new User ("Bob Smith", "password");
        String password = "Notreal";
        String password2 = "password";

        assertNotEquals(u1.getPassword(), password);
        assertEquals(u1.getPassword(), password2);


    }
}
