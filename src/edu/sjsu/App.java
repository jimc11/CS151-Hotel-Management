package edu.sjsu;

import edu.sjsu.Controller.HotelController;
import edu.sjsu.Model.BookingList;
import edu.sjsu.Model.HotelModel;
import edu.sjsu.Model.RoomList;
import edu.sjsu.Model.UserList;
import edu.sjsu.View.HotelView;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

 //Main class used to run the application
public class App {


        private static BlockingQueue queue = new LinkedBlockingQueue<>();
        private static HotelModel m ;
        private static HotelView v = new HotelView(queue);

     /**
      * Main method which initializes objects needed and uses the blocking queue
      * to perform tasks
      */
     public static void main(String[] args) {
            RoomList rl =  RoomList.getInstance();
            UserList ul = new UserList();

            v = v.init(queue);
            m =  new HotelModel(rl, ul);
            HotelController c = new HotelController(m, v, queue);

            try {
                c.mainLoop();
            } catch (InterruptedException e){
                e.printStackTrace();
            }

            //HotelView.dispose(); // ...?
            queue.clear();
        }
    }


