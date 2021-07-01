
//This class represents the start and end date of a booking
package edu.sjsu.Model;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class BookingDate
{
    LocalDate startDate;
    LocalDate endDate;

    /**
     * Constructor to update variables
     * @param startDate
     * @param endDate
     */
    public BookingDate(LocalDate startDate, LocalDate endDate)
    {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * checks if booking objects are equal
     * @param obj
     * @return boolean if they are equal
     */
    public boolean equals(Object obj)
    {
        BookingDate other = (BookingDate)obj;
        if(other.startDate.equals(this.startDate) && other.endDate.equals(this.endDate))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public String toString()
    {
        String startDateformatted = startDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
        String endDateformatted = startDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));

        return startDateformatted + " to " + endDateformatted;

    }

    /**
     * Getter method to get end date
     * @return end date
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Getter method to get start date
     * @return start date
     */
    public LocalDate getStartDate() {
        return startDate;
    }
}
