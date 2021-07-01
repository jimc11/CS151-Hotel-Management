package edu.sjsu.Controller;

import edu.sjsu.Messages.Message;

public interface Valve {
    /**
     * Performs certain action in response to message
     *
     * @param message
     *            message from the View
     * @return the information if message was processed properly
     */
    public ValveResponse execute(Message message);
}
