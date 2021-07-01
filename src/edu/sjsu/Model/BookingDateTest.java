package edu.sjsu.Model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

//Testing if 2 booking dates are equal
class BookingDateTest {

    @Test
    void testEquals() {

        BookingDate bd1 = new BookingDate(LocalDate.of(2021, 1, 8), LocalDate.of(2021, 1, 12));
        BookingDate bd2 = new BookingDate(LocalDate.of(2022, 1, 8), LocalDate.of(2022, 1, 12));
        BookingDate bd3 = new BookingDate(LocalDate.of(2022, 1, 8), LocalDate.of(2022, 1, 12));

        assertNotEquals(bd2, bd1);
        assertEquals(bd3, bd2);



    }
}
