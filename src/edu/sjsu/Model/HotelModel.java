package edu.sjsu.Model;

/**
 * Serves as the model component where we use the RoomList and UserList
 * which is needed for completing the actions from HotelController
 */
public class HotelModel {
    private RoomList roomList;
    private UserList userList;

    /**
     * Constructor which passes in RoomList and UserList objects to update the
     * variables
     * @param roomList - list of rooms
     * @param userList - list of users
     */
    public HotelModel(RoomList roomList,UserList userList){
        this.roomList = roomList;
        this.userList = userList;
    }

    /**
     * Getter method to return userList
     * @return userList
     */
    public UserList getUserList() {
        return userList;
    }

    /**
     * Getter method to return the list of rooms
     * @return roomList
     */
    public RoomList getRoomList() {
        return roomList;
    }
}
