package edu.sjsu.Controller;

import edu.sjsu.DateException;
import edu.sjsu.Messages.*;
import edu.sjsu.Model.*;
import edu.sjsu.Messages.CancelReservationSubmitMessage;
import edu.sjsu.Messages.ConfirmBookingMessage;
import edu.sjsu.View.HotelView;


import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.*;

/**
 * This class serves as the controller component. We use valves to also execute the
 * messages which are passed to the blockingqueue.
 */
public class HotelController {
    private BlockingQueue<Message> queue;

    private HotelView hotelView;
    private HotelModel hotelModel;

    private List<Valve> valves = new LinkedList<Valve>();

    /**
     * Constructor which takes in arguments which updates the variables and adds a new valve
     * for the messages
     * @param hotelModel - Model object
     * @param hotelView - View object
     * @param queue - blocking queue used
     */
    public HotelController(HotelModel hotelModel, HotelView hotelView, BlockingQueue<Message> queue)
    {
        this.hotelModel = hotelModel;
        this.hotelView = hotelView;
        this.queue = queue;
        this.valves.add(new GreetingToLoginValve());
        this.valves.add(new GreetingToCreateValve());
        this.valves.add(new BackToGreetingValve());

        this.valves.add(new LoginSubmitValve());
        this.valves.add(new CreateAccountSubmitValve());

        this.valves.add(new HomeToBookingValve());

        this.valves.add(new TimeAndTypeValve());
        this.valves.add(new SubmitBookingValve());
        this.valves.add(new BackToHomeValve());

        this.valves.add(new CancelReservationValve());
        this.valves.add(new ConfirmBookingValve() );
        this.valves.add(new CancelReservationSubmitValve() );


        hotelView.greeting();
    }

    /**
     * Uses valves to execute messages and throws an error if it is unable to
     * @throws InterruptedException
     */
    public void mainLoop() throws InterruptedException {
        ValveResponse response = ValveResponse.EXECUTED;
        Message message = null;

        while (response != ValveResponse.FINISH) {
            try {
                message = (Message) queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (Valve valve : valves) {
                response = valve.execute(message);
            }

        }
    }

    /**
     * Class to execute the login message. Once this passes, we go to the login
     * page and will show that page.
     */
    private class GreetingToLoginValve implements Valve {
        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() == GreetingToLoginMessage.class) {
                queue.clear();
                hotelView.login();
                return ValveResponse.GREETING_TO_LOGIN;
            }
            return ValveResponse.EXECUTED;
        }
    }

    /**
     * Will use valve to execute the message and will output the create account page from
     * hotelView.
     */
    private class GreetingToCreateValve implements Valve {
        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() == GreetingToCreateMessage.class) {
                queue.clear();
                hotelView.createAccount();
                return ValveResponse.GREETING_TO_CREATE;
            }
            return ValveResponse.EXECUTED;
        }
    }

    /**
     * Will go back to the main guest portal for the specified user
     */
    private class BackToGreetingValve implements Valve {
        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() == BackToGreetingMessage.class) {
                queue.clear();
                hotelView.greeting();
                return ValveResponse.BACK_TO_GREETING;
            }
            return ValveResponse.EXECUTED;
        }
    }

    /**
     * Will output messages depending on whether or not the login is successful. Will check if the
     * login is valid using the userList from hotelModel. Once we can authenticate the user, they go to
     * the guest portal to do other actions.
     */
    private class LoginSubmitValve implements Valve {
        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() == LoginSubmitMessage.class) {


                if(hotelModel.getUserList().validLogin(((LoginSubmitMessage) message).getUsername(),((LoginSubmitMessage) message).getPassword())){
                    queue.clear();
                    User user = hotelModel.getUserList().login(((LoginSubmitMessage) message).getUsername(),((LoginSubmitMessage) message).getPassword());
                    hotelView.guestHomepage(user);
                    return ValveResponse.LOGIN_SUCCESS;
                }
                else{
                    queue.clear();
                    hotelView.loginFail();
                    return ValveResponse.LOGIN_FAIL;
                }
            }
            return ValveResponse.EXECUTED;
        }
    }

    /**
     * Will ensure that the user creates an account properly and if they do, they get a success message pop up.
     * If something goes wrong, an error message pops up instead
     */
    private class CreateAccountSubmitValve implements Valve {
        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() == CreateAccountSubmitMessage.class) {
                queue.clear();

                //Create c = new Create();

                if(hotelModel.getUserList().addUser( ((CreateAccountSubmitMessage) message).getUsername() ,((CreateAccountSubmitMessage) message).getPassword(),  ((CreateAccountSubmitMessage) message).getPassword2()) ){
                    queue.clear();
                    hotelView.createSuccess();
                    return ValveResponse.CREATE_SUCCESS;
                }
                else{

                    queue.clear();
                    hotelView.createFail();
                    return ValveResponse.CREATE_FAIL;
                }
            }
            return ValveResponse.EXECUTED;
        }
    } // end CreateAccountValve

    /**
     * Used for booking a room. We get the user that is logged in and then use the book room method
     * from HotelView
     */
    private class HomeToBookingValve implements Valve {
        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() == HomeToBookingMessage.class) {
                User user = ((HomeToBookingMessage) message).getUser();
                hotelView.bookRoom(new ArrayList<Room>(), 0,0,0,0,"" ,user);
                queue.clear();
                return ValveResponse.BOOKING;

            }
            return ValveResponse.EXECUTED;
        }
    }

    /**
     * Finds if a given room is avaliable and, if it is, the user will be able to book the room
     * for the room number specified.
     */
    private class TimeAndTypeValve implements Valve {
        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() == TimeAndTypeMessage.class) {
                String [] mdytt = ((TimeAndTypeMessage) message).getMdytt();

                int month = Integer.parseInt(mdytt[0]);
                int day =   Integer.parseInt(mdytt[1]);
                int year =  Integer.parseInt(mdytt[2]);
                int days = Integer.parseInt(mdytt[3]);
                ArrayList<Room> available = new ArrayList<Room>();

                String type = mdytt[4];
                User user = ((TimeAndTypeMessage) message).getUser();

                try {
                    available = hotelModel.getRoomList().checkAvailability(new int[]{month, day, year}, days, mdytt[4]);
                }
                catch(DateException d){
                    if (d.getType() == "before"){
                        hotelView.dateError(1);
                    } else
                    {
                        hotelView.dateError(2);
                    }
                }
                //test loop
//                for (Room r: available){
//                    System.out.println(r.toString());
//                }
                if (available.isEmpty()) {
                    hotelView.noRoomsPopup();
                }
                try {
                    hotelView.bookRoom(available, month, day, year, days, type, user);
                } catch (Exception e) {
                    e.printStackTrace();
                    hotelView.nullPopup();
                }

                queue.clear();
                return ValveResponse.TYPE_TIME;
            }
            return ValveResponse.EXECUTED;
        }
    }

    /**
     * Will use roomList from hotelModel to book a room for the specified user for
     * the start and end date for the room they selected.
     */
    private class SubmitBookingValve implements Valve{
        public ValveResponse execute(Message message) {
            if (message.getClass() == SubmitBookingMessage.class) {
                int [] start = ((SubmitBookingMessage) message).getDateStart();
                int length = ((SubmitBookingMessage) message).getEndDate();
                int roomNum = ((SubmitBookingMessage) message).getRoomNum();
                User user = ((SubmitBookingMessage) message).getUser();
                String type = ((SubmitBookingMessage) message).getType();
                int [] end;
                LocalDate startDate = LocalDate.of(start[2], start[0], start[1]);
                LocalDate endDate = startDate.plusDays(length);

                end = new int []{endDate.getMonthValue(),endDate.getDayOfMonth(),endDate.getYear()};

                Room rm = hotelModel.getRoomList().get(roomNum);
                try {
                    hotelModel.getRoomList().checkAvailability(start, end, rm);
                }
                catch(DateException d){
                    if (d.getType() == "before"){
                        hotelView.dateError(1);


                    } else
                    {
                        hotelView.dateError(2);

                    }
                }



                hotelView.BookRoomConfirmation(start[0], start[1], start[2], length, rm, user,type);
                queue.clear();
                return ValveResponse.SUBMIT_BOOKING;
            }
            return ValveResponse.EXECUTED;
        }
    }

    /**
     * Will go to the guest portal for the specific user.
     */
    private class BackToHomeValve implements Valve{
        public ValveResponse execute(Message message) {
            if (message.getClass() == BackToHomeMessage.class) {

                User user = ((BackToHomeMessage) message).getUser();
                hotelView.guestHomepage(user);

                return ValveResponse.BACK_TO_HOME;
            }
            return ValveResponse.EXECUTED;
        }
    }



    private class ConfirmBookingValve implements Valve{
        public ValveResponse execute(Message message) {
            if (message.getClass() == ConfirmBookingMessage.class) {

                int [] start = ((ConfirmBookingMessage) message).getStart();
                int length = ((ConfirmBookingMessage) message).getDuration();
                Room room = ((ConfirmBookingMessage) message).getRoom();
                User user = ((ConfirmBookingMessage) message).getUser();
                int [] end;
                LocalDate startDate = LocalDate.of(start[2], start[0], start[1]);
                LocalDate endDate = startDate.plusDays(length);
                end = new int []{endDate.getMonthValue(),endDate.getDayOfMonth(),endDate.getYear()};


                try {
                    hotelModel.getRoomList().bookRoom(start,end, room.getNumber(), user);
                }
                catch(DateException d){
                    if (d.getType() == "before"){
                        hotelView.dateError(1);
                    } else
                    {
                        hotelView.dateError(2);
                    }
                }
                User user2 = ((ConfirmBookingMessage) message).getUser();
                hotelView.guestHomepage(user2);

                return ValveResponse.BACK_TO_HOME;
            }
            return ValveResponse.EXECUTED;
        }
    }


    private class CancelReservationValve implements Valve{
        public ValveResponse execute(Message message){
            if(message.getClass() == CancelReservationMessage.class){
                User user = ((CancelReservationMessage) message).getUser();
                ArrayList<Booking> bookings = new ArrayList<Booking>();
                ArrayList<Room> bookedRooms = new ArrayList<Room>();

                bookings = user.getRooms();
                for(int i = 0; i <bookings.size(); i++)
                {
                    Room r = bookings.get(i).getRoom();
                    bookedRooms.add(r);
                }

                hotelView.cancelReservation(bookedRooms, user);
                queue.clear();
                return ValveResponse.CANCEL_BOOKING_SUCCESS;
            }
            return ValveResponse.EXECUTED;
        }
    }

    private class CancelReservationSubmitValve implements Valve{
        public ValveResponse execute(Message message){
            if(message.getClass() == CancelReservationSubmitMessage.class){

                int [] start =((CancelReservationSubmitMessage) message).getStart();
                int [] end = ((CancelReservationSubmitMessage) message).getEnd();
                int roomnum = ((CancelReservationSubmitMessage) message).getRoomNum();
                User user = ((CancelReservationSubmitMessage) message).getUser();

                hotelModel.getRoomList().cancelRoom(start,end, roomnum, user);

                hotelView.guestHomepage(user);
                queue.clear();
                return ValveResponse.CANCEL_BOOKING_SUCCESS;
            }
            return ValveResponse.EXECUTED;
        }
    }

} // end class

