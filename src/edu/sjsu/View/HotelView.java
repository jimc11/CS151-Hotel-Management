package edu.sjsu.View;

import edu.sjsu.Messages.*;
import edu.sjsu.Model.Room;
import edu.sjsu.Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

/**
 * This class represents the view component of MVC. We have most of the logic of all the components that
 * the user interacts with
 */
public class HotelView extends JFrame {

    private JButton confirmCancel, confirmApprove;
    private JPanel confirmLeft, confirmRight, confirmBottom;
    private JFrame confirmationFrame;

    private BlockingQueue queue;

    /**
     * method to return a new object of HotelView using the blocking queue passed
     * @param queue - Blocking queue storing the messages needing to be executed
     * @return new HotelView object using the queue parameter
     */
    public static HotelView init(BlockingQueue<Message> queue){
        return new HotelView(queue);
    }

    /**
     * constructor which takes the blocking queue and sets the frame for the view
     * component
     * @param queue - Blocking queue storing the messages needing to be executed
     */
    public HotelView(BlockingQueue<Message> queue){
        this.queue = queue;
        int frameWidth  = 800;
        int frameHeight = 450;

        this.setSize(frameWidth,frameHeight);
    }

    /**
     * greeting() method is the initial screen which prompts the user to either login
     * or signup for a account. When the buttons are clicked, we add it as a message for
     * the blocking queue so that we can process it
     */
    public void greeting(){
        this.refreshStart();
        this.setTitle("Welcome!");

        Panel panel = new Panel();

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(event ->{
            try {
                this.queue.put(new GreetingToLoginMessage());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });

        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.addActionListener(event ->{
            try {
                this.queue.put(new GreetingToCreateMessage());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        loginButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        createAccountButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        createAccountButton.setAlignmentY(Component.CENTER_ALIGNMENT);

        panel.add(loginButton);
        panel.add(createAccountButton);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        this.add(panel);

        // ----- Layout -----
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.refreshEnd();
    }


    /**
     * login() method will check the credentials of the user and contains the GUI logic of the
     * page as well. It will allow the user to go to the portal if they are authenticated
     */
    public void login(){
        refreshStart();
        this.setTitle("Login");

        JLabel username= new JLabel("Username: ");
        JLabel password= new JLabel("Password: ");

        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);

        JPanel buttonPanel = new JPanel();
        JPanel mainPanel = new JPanel();

        JButton submit = new JButton("Submit");
        submit.addActionListener(event ->{
            try{
                this.queue.put(new LoginSubmitMessage(usernameField.getText(), passwordField.getPassword() ) );
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        });
        JButton back = new JButton("Back");
        back.addActionListener(event ->{
            try {
                this.queue.put(new BackToGreetingMessage());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        buttonPanel.setLayout(new FlowLayout());

        mainPanel.add(username);
        mainPanel.add(usernameField);

        mainPanel.add(password);
        mainPanel.add(passwordField);

        buttonPanel.add(submit);
        buttonPanel.add(back);

        Panel master = new Panel();
        master.add(mainPanel);
        master.add(buttonPanel);

        this.add(master);

        this.refreshEnd();
    }

    /**
     * createAccount() method contains the logic of creating a new account. It asks for
     * a username and password with verification. Once you click submit, the action listener
     * will add the credentials and the login page will work with the account.
     */
    public void createAccount(){
        this.setTitle("Create a New Account");
        this.refreshStart();

        JLabel username;
        JLabel password;
        JLabel password2;

        JTextField usernameTextfield;
        JPasswordField passwordTextfield;
        JPasswordField passwordTextfield2;

        JButton submit;
        JButton back;
        JPanel buttonPanel, mainPanel;

        this.setPreferredSize(new Dimension(800,450));
        buttonPanel = new JPanel();
        mainPanel = new JPanel();

        username = new JLabel("Username: ");
        password = new JLabel("Password: ");
        password2 = new JLabel("Re-Enter Password: ");

        usernameTextfield = new JTextField("");
        passwordTextfield = new JPasswordField("");
        passwordTextfield2 = new JPasswordField("");

        submit = new JButton("Submit");
        submit.addActionListener(event ->{
            try {

//                System.out.print("us: "  + usernameTextfield.getText() + ", pw1: ");
//                for(char ch: passwordTextfield.getPassword())
//                    System.out.print(ch);
//                System.out.print(", pw2: ");
//                for(char ch: passwordTextfield2.getPassword())
//                    System.out.print(ch);
//                System.out.println();
                this.queue.put(new CreateAccountSubmitMessage(usernameTextfield.getText(), passwordTextfield.getPassword(), passwordTextfield2.getPassword()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        back = new JButton("Back");
        back.addActionListener(event ->{
            try {
                this.queue.put(new BackToGreetingMessage());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        buttonPanel.setLayout(new FlowLayout());

        mainPanel.add(username);
        mainPanel.add(usernameTextfield);

        mainPanel.add(password);
        mainPanel.add(passwordTextfield);

        mainPanel.add(password2);
        mainPanel.add(passwordTextfield2);

        buttonPanel.add(submit);
        buttonPanel.add(back);

        Panel master = new Panel();
        master.add(mainPanel);
        master.add(buttonPanel);

        this.add(master);

        this.refreshEnd();

    }

    /**
     * The main portal page for guests. Has actions to book a room, cancel reservation,
     * and logout. The logic for these methods are explained later.
     * @param user - current user that is logged in
     */
    public void guestHomepage(User user) {

        this.refreshStart();
        this.setTitle("Guest Portal");

        JButton bookRoom;
        JButton cancelReservation;
        JButton logout;
        JLabel home;
        JPanel buttonPanel, mainPanel;

        buttonPanel = new JPanel();
        mainPanel = new JPanel();

        home = new JLabel("Guest Portal", JLabel.CENTER);
        home.setFont(new Font("Georgia", Font.PLAIN, 30));
        home.setAlignmentX(Component.CENTER_ALIGNMENT);
        home.setAlignmentY(Component.CENTER_ALIGNMENT);

        bookRoom = new JButton("Book Room");
        bookRoom.addActionListener(event ->{
            try {
                this.queue.put(new HomeToBookingMessage(user));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        cancelReservation = new JButton("Cancel Reservation");

        cancelReservation.addActionListener(event ->{
            try {
                this.queue.put(new CancelReservationMessage(user));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        logout = new JButton("Logout");
        logout.addActionListener(event ->{
            JOptionPane.showMessageDialog(this, "Thanks for the visit!  Have a nice day.");
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        });


        mainPanel.add(home);
        buttonPanel.add(bookRoom);
        buttonPanel.add(cancelReservation);
        buttonPanel.add(logout);

        Panel master = new Panel();
        master.setLayout(new BoxLayout(master,1));
        master.add(mainPanel, BorderLayout.CENTER);
        master.add(buttonPanel, BorderLayout.PAGE_END);
        this.add(master);

        this.add(mainPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.PAGE_END);

        this.refreshEnd();
    }

    /**
     * bookRoom contains the logic required to book a room according to the requirements passed by the user
     * It will take in the preferences of the user and give all available rooms from which the user can select from.
     * Once the user books this specific room, it is marked as unavailable and cannot be booked again for the
     * same time frame.
     * @param available - Uses the Room arraylist to check whether or not a given room is occupied or open to book
     * @param monthB - month selected
     * @param dayB - day selected
     * @param yearB - year selected
     * @param daysB - duration of visit
     * @param type2 - type of room selected
     * @param user - the logged in user
     */
    public void bookRoom(ArrayList<Room> available, int monthB, int dayB, int yearB, int daysB, String type2, User user) throws NumberFormatException{

        this.refreshStart();
        this.setTitle("Book Room");

            UIManager.getLookAndFeelDefaults().put("TableHeader.background", new Color(136,136,136));
            UIManager.getLookAndFeelDefaults().put("TableHeader.foreground", new Color(136,0,0));
            UIManager.getLookAndFeelDefaults().put("TableHeader.font", new Font("Arial", Font.BOLD, 12));


        int RoomNum;
        String[] monthOptionslist = {"Month?", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };
        String[] dayOptionslist = {"Day?", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };
        String[] yearOptionslist = {"Year?", "2020", "2021"};
        String[] numDayOptionslist = {"How Many Days?", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
        String[] typeOptionslist = {"Select Type of Room", "Single Room", "Family", "Suite", "Executive Suite"};

        JComboBox monthOptions = new JComboBox(monthOptionslist);
        JComboBox dayOptions = new JComboBox(dayOptionslist);
        JComboBox yearOptions = new JComboBox(yearOptionslist);
        JComboBox numDayOptions = new JComboBox(numDayOptionslist);
        JComboBox typeOptions = new JComboBox(typeOptionslist);




        JButton time, rType, both, back;
        JLabel main, bookPrompt;
        JPanel selectOptions, titlePanel, submitPanel;

        String[] selectedMonth = new String[1];
        String[] selectedDay = new String[1];
        String[] selectedYear = new String[1];
        String[] selectedNumDays = new String[1];
        String[] selectedType = new String[1];

        JTextField month = new JTextField(15);
        JTextField day = new JTextField(15);
        JTextField year = new JTextField(15);
        JTextField numDays = new JTextField(15);
        JTextField type = new JTextField(15);


        //mainFrame.getContentPane().setBackground(Color.BLUE);
        selectOptions = new JPanel();
        titlePanel = new JPanel();
        submitPanel = new JPanel();

        main = new JLabel("Book Room", JLabel.CENTER);
        main.setFont(new Font("Georgia", Font.PLAIN, 30));
        main.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.setAlignmentY(Component.CENTER_ALIGNMENT);

        monthOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedMonth[0] = (String)monthOptions.getSelectedItem();
                month.setText(selectedMonth[0]);
            }
        });

        dayOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedDay[0] = (String)dayOptions.getSelectedItem();
                day.setText((String) selectedDay[0]);
            }
        });

        yearOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedYear[0] = (String)yearOptions.getSelectedItem();
                year.setText(selectedYear[0]);
            }
        });

        numDayOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedNumDays[0] = (String)numDayOptions.getSelectedItem();
                numDays.setText(selectedNumDays[0]);
            }
        });

        typeOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedType[0] = (String)typeOptions.getSelectedItem();
                type.setText(selectedType[0]);
            }
        });

        final int[] roomNum = new int[]{-1};
        both = new JButton("Search");
        both.addActionListener(event ->{
            int [] dates = new int[]{monthB, dayB, yearB };
            if (selectedNumDays[0]== null || user == null || selectedType[0] == null || selectedMonth[0] == null || selectedDay[0] == null || selectedYear[0] == null )
                nullPopup();
            else {
                try {
                    this.queue.put(new TimeAndTypeMessage(selectedMonth[0], selectedDay[0], selectedYear[0], selectedNumDays[0], selectedType[0], user));

                } catch ( InterruptedException e) {
                }
            }

        });


        String [] columnNames = {"Beds", "Price", "Type", "Room Number", "Start Date", "Nights"};

        Object [][] data = new Object[available.size()][columnNames.length];
        String[] start = new String[0];

        for (int i = 0 ; i < available.size(); i++){
            Object[] obj = available.get(i).toArray();
            Object[] obj2 = new Object[available.get(i).toArray().length+2];
            for(int j = 0 ; j < obj.length; j++){
                obj2[j] = obj[j];
            }
            start = new String[]{ selectedMonth[0],
                    selectedDay[0],
                    selectedYear[0] };


            String startString = monthB + "/" + dayB + "/" + yearB;

            obj2[available.get(i).toArray().length] = startString; // start date
            obj2[available.get(i).toArray().length+1] = daysB; // end date

            data[i] = obj2;
        }


        JTable table = new JTable(data, columnNames);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);


        back = new JButton("Back");
        back.addActionListener(event ->{
            try {
                this.queue.put(new BackToHomeMessage(user));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
//        submitPanel.add(time);
//        submitPanel.add(rType);
        submitPanel.add(both);
        submitPanel.add(back);

        titlePanel.add(main);
        selectOptions.add(monthOptions);
        selectOptions.add(dayOptions);
        selectOptions.add(yearOptions);
        selectOptions.add(numDayOptions);
        selectOptions.add(typeOptions);

        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.PAGE_AXIS));
        selectOptions.setLayout(new FlowLayout());



        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                int col = 3;
                if (row >= 0) {
                    roomNum[0] = (int) table.getModel().getValueAt(row,col);
                }
            }
        });
        bookPrompt = new JLabel("Click on the row that you would like to book ");
        JButton submit = new JButton("Submit");

        submit.addActionListener(event ->{
            int [] dates = new int[]{monthB, dayB, yearB };
            if (dates == null || (Integer)daysB == null || (Integer)roomNum[0] == -1 || user == null || type2 == null || monthB == 0 || dayB == 0 || yearB == 0 )
                nullSubmitPopup();
            else {
                try {
                    this.queue.put(new SubmitBookingMessage(dates,
                            daysB, roomNum[0], user, type2));
                } catch ( InterruptedException e) {
                }
            }
        });


        Panel master = new Panel();

        master.add(titlePanel);
        master.add(selectOptions);

        master.setLayout(new BoxLayout(master, 1));

        this.add(master);

        this.add(submitPanel);

        this.add(scrollPane);
        this.add(bookPrompt);
        this.add(submit);



        this.refreshEnd();
    }

    private void nullSubmitPopup() {
        JOptionPane.showMessageDialog(this, "Please select a room.");
    }


    /**
     * The BookRoomConfirmation acts as a confirmation page for users once they have selected the room
     * It prints out the data, room type, and duration for the room that they have selected. Here we used
     * GridBagLayout so that we can get all the elements aligned and placed properly on the frame. The user
     * has the option to confirm (leads them back to thier portal), or cancel which will cancel the transaction
     * and the user can book another room instead.
     * @param month - selected month
     * @param day - selected day
     * @param year - selected year
     * @param duration - length of stay
     * @param type - type of room selected
     * @param user - user logged in
     */
    public void BookRoomConfirmation(int month, int day, int year, int duration, Room room, User user, String type)
    {
        confirmationFrame = new JFrame("Confirmation Page");
        confirmationFrame.setPreferredSize(new Dimension(800,450));
        confirmationFrame.setLayout(new GridBagLayout());

        confirmLeft = new JPanel();
        confirmRight = new JPanel();
        confirmBottom = new JPanel();

        JLabel roomNum = new JLabel("Room Number: ");
        roomNum.setFont(new Font("Georgia", Font.PLAIN, 20));
        JLabel startConfirm = new JLabel("Start date: ");
        startConfirm.setFont(new Font("Georgia", Font.PLAIN, 20));
        JLabel endConfirm = new JLabel("Nights booked: ");
        endConfirm.setFont(new Font("Georgia", Font.PLAIN, 20));
        JLabel typeText = new JLabel("Room Type: ");
        typeText.setFont(new Font("Georgia", Font.PLAIN, 20));

        JLabel roomNumL = new JLabel(String.valueOf(room.getNumber()));
        roomNumL.setFont(new Font("Georgia", Font.PLAIN, 20));
        JLabel dateConfirm = new JLabel(month + "/" + day + "/" + year);
        dateConfirm.setFont(new Font("Georgia", Font.PLAIN, 20));
        JLabel nightsBooked = new JLabel(String.valueOf(duration));
        nightsBooked.setFont(new Font("Georgia", Font.PLAIN, 20));
        JLabel roomType = new JLabel(type);
        roomType.setFont(new Font("Georgia", Font.PLAIN, 20));

        confirmLeft.add(roomNum);
        confirmLeft.add(startConfirm);
        confirmLeft.add(endConfirm);
        confirmLeft.add(typeText);

        confirmRight.add(roomNumL);
        confirmRight.add(dateConfirm);
        confirmRight.add(nightsBooked);
        confirmRight.add(roomType);

        confirmLeft.setLayout(new BoxLayout(confirmLeft, BoxLayout.Y_AXIS));
        confirmRight.setLayout(new BoxLayout(confirmRight, BoxLayout.Y_AXIS));


        confirmCancel = new JButton("Cancel");
        confirmBottom.add(confirmCancel);
        confirmCancel.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                confirmationFrame.setVisible(false);
            }
        });
        confirmApprove = new JButton("Confirm");
        confirmBottom.add(confirmApprove);
        confirmApprove.addActionListener(event ->{
            try {

                this.queue.put(new ConfirmBookingMessage(new int[]{month, day, year },
                        duration, room, user));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        confirmationFrame.add(confirmLeft);
        confirmationFrame.add(confirmRight);

        confirmationFrame.add(confirmBottom, new GridBagConstraints(0, 0, 2, 1, 0, 1.0,
                GridBagConstraints.SOUTH, GridBagConstraints.SOUTH, new Insets(0, 0, 0, 0), 0, 0));

        confirmationFrame.pack();
        confirmationFrame.setVisible(true);
        confirmationFrame.setLocationRelativeTo(null);
        confirmationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }


    public void nullPopup() {
        JOptionPane.showMessageDialog(this, "Please make sure a month, day, year, number of days, and type are selected.");
    }

    /**
     * acts a refresh page
     */
    public void refreshStart(){
        this.getContentPane().removeAll();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void refreshEnd(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameWidth  = screenSize.width;
        int frameHeight = screenSize.height;
        this.setSize(frameWidth/2,frameHeight/2);
        this.setLocation(screenSize.width/2 - this.getSize().width/2, screenSize.height/2-this.getSize().height/2); // makes the application start in the center of the screen
        this.setVisible(true);
    }

    /**
     * If there is an issue with login, we have a message popup
     */
    public void loginFail() {
        JOptionPane.showMessageDialog(this, "Login failed!  Please try again, or make an account.");
    }

    /**
     * Once you have entered details, you get a popup message saying that your account was created
     */
    public void createSuccess() {
        login();
        JOptionPane.showMessageDialog(this, "Account Creation Success!  You may now login.");
    }

    /**
     * If there was an issue with creating an account, error message pops up
     */
    public void createFail() {
        createAccount();
        JOptionPane.showMessageDialog(this, "Account Creation Failed!  Make sure password matches or pick a more unique username.");
    }

    public void cancelReservation(ArrayList<Room> bookedRooms, User user)
    {
        this.refreshStart();
        this.setTitle("Cancel Reservation");

        String [] columnNames = {"Beds", "Price", "Type", "Room Number", "Start Date", "End Date"};

        Object [][] data = new Object[bookedRooms.size()][columnNames.length];
        int [] end= new int[0];
        int [] start = new int[0];
        for (int i = 0 ; i < user.getRooms().size(); i++){
            Object[] obj = user.getRooms().get(i).getRoom().toArray();
            Object[] obj2 = new Object[user.getRooms().get(i).getRoom().toArray().length+2];
            for(int j = 0 ; j < obj.length; j++){
                obj2[j] = obj[j];
            }
              start = new int[]{ user.getRooms().get(i).getBookingDates().getStartDate().getMonthValue(),
                                      user.getRooms().get(i).getBookingDates().getStartDate().getDayOfMonth(),
                                      user.getRooms().get(i).getBookingDates().getStartDate().getYear() };

             end = new int[]{ user.getRooms().get(i).getBookingDates().getEndDate().getMonthValue(),
                                    user.getRooms().get(i).getBookingDates().getEndDate().getDayOfMonth(),
                                    user.getRooms().get(i).getBookingDates().getEndDate().getYear() };

            String startString = start[0] + "/" +start[1] + "/"+start[2];
            String endString = end[0] + "/" +end[1] + "/"+end[2];

            obj2[user.getRooms().get(i).getRoom().toArray().length] = startString; // start date
            obj2[user.getRooms().get(i).getRoom().toArray().length+1] = endString; // end date

            data[i] = obj2;
        }

        JTable table = new JTable(data, columnNames);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        JButton submit, back;
        JLabel main;
        JPanel titlePanel, submitPanel;

        titlePanel = new JPanel();
        submitPanel = new JPanel();

        main = new JLabel("Cancel Reservation", JLabel.CENTER);
        main.setFont(new Font("Georgia", Font.PLAIN, 30));
        main.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.setAlignmentY(Component.CENTER_ALIGNMENT);

        back = new JButton("Back");
        back.addActionListener(event ->{
            guestHomepage(user);
        });

        submitPanel.add(back);

        titlePanel.add(main);

        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.PAGE_AXIS));

        int[] roomNum = new int[]{-1};
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                int col = 3;
                if (row >= 0) {
                    roomNum[0] = (int) table.getModel().getValueAt(row,col);
                }
            }
        });



        submit = new JButton("Submit");

        int[] finalStart = start;
        int[] finalEnd = end;
        submit.addActionListener(event ->{

            if ((Integer)roomNum[0] == -1)
                nullCancelPopup();
            else {
                try {
                    JOptionPane.showMessageDialog(this, "Reservation canceled!  Sorry to see you go.");

                    this.queue.put(new CancelReservationSubmitMessage(finalStart, finalEnd, roomNum[0], user));


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        Panel master = new Panel();

        master.add(titlePanel);

        master.setLayout(new BoxLayout(master, 1));

        this.add(master);

        this.add(submitPanel);

        this.add(scrollPane);
        this.add(submit);

        this.refreshEnd();
    }

    private void nullCancelPopup() {
        JOptionPane.showMessageDialog(new Frame(), "Please select a reservation that you would like to cancel.");

    }


    public void dateError(int i) {
        if (i ==1)
            JOptionPane.showMessageDialog(new Frame(), "Dates cannot be before today.");
        else
            JOptionPane.showMessageDialog(new Frame(), "Dates must be less than 365 days from now.");

    }

    public void noRoomsPopup() {
        JOptionPane.showMessageDialog(new Frame(), "There are no matching rooms, please try a later date or fewer nights.");
    }
}
