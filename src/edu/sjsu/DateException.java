package edu.sjsu;

/**
 * Date exception which is thrown if date selected is not in bounds
 */
public class DateException extends Exception {
    private String type;

    /**
     * Exception if date is before or after specified range
     * @param i - placement of date exception
     */
    public DateException(boolean i){
        if(i)
            type = "before";
        else
            type = "after";
    }

    /**
     * @return type
     */
    public String getType() {
        return type;
    }
}
