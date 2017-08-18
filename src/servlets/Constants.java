package servlets;

/**
 * Created by Tal on 4/2/2017.
 */
public enum Constants {
    ;
    public static final String EMAIL = "email";
    public static final String USER_FIRST_NAME = "firstName";
    public static final String USER_LAST_NAME = "lastName";
    public static final String USER_PASSWORD= "password";
    public static final String ACTION_TYPE = "ActionType";
    public static final int INVALID_EMAIL = 4;
    public static final int WRONG_PASSWORD = 5;
    public static final int USER_NOT_EXIST = 0;
    public static final int USER_EXIST = 2;
    public static final int LOGIN_SUCCESS = 1;
    public static final int SIGNUP_SUCCESS = 3;
    public static final int LOGOUT_SUCCESS = 4;
    public static final String SHOW_ID = "showID";
    public static final String SHOW_NAME = "showName";
    public static final String SHOW_LOCATION = "showLocation";
    public static final String PICTURE_URL = "pictureUrl";
    public static final String SHOW_PRICE = "showPrice";
    public static final String NUMBER_OF_TICKETS = "numOfTickets";
    public static final String SHOW_DATE = "showDate";
    public static final String TICKET_LIST = "ticketList";
    public static final String ADD_SHOW = "addShow";
    public static final String UPDATE_SHOW ="updateShow";
    public static final String DELETE_SHOW ="deleteShow";
    public static final int SHOW_EXIST = 1;
    public static final int SHOW_ADDED_SUCCESSFULLY = 2;
    public static final int SHOW_UPDATE_SUCCESSFULLY = 3;
    public static final int SHOW_NOT_EXIST = 4;
    public static final int SHOW_DELETE_SUCCESSFULLY = 5;
    public static final int UserDB = 1;
    public static final int ShowsDB = 2;
    public static final String LOGIN_USER = "loginUser";
    public static final String SHOW = "currShow";
    public static final String GET_USER = "getUserFromSession";
    public static final String LOGIN = "login";
    public static final String SHOW_ABOUT = "showAbout";
    public static final String LOGOUT = "logout";
    public static final String SIGNUP = "signup";
    public static final int SHOW_TO_SELL = 1;
    public static final int SHOW_TO_BUY = 2;
    public static final String GET_SHOWS = "getShows";
    public static final String GET_SHOW = "getShow";
    public static final String GET_SELL_SHOWS = "getMySellShows";
    public static final String GET_SEARCH_SHOW = "getSearchShow";
    public static final String GET_SHOW_EXIST = "getShowExist";
    public static final boolean SHOW_NAME_EXIST = true;
    public static final boolean SHOW_NAME_NOT_EXIST = false;
    public static final String BUY_TICKET = "buyTicket";
    public static final String GET_BOUGHT_TICKETS = "getMyBoughtShows";
    public static final String NUMBERS_OF_TICKETS_TO_BUY = "numOfTicketsToBuy";
}
