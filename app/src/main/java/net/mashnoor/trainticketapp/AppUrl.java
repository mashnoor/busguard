package net.mashnoor.trainticketapp;

public class AppUrl {

    final private static String BASE_URL = "http://192.168.43.162:5003";

    final public static String POST_TICKET_URL = BASE_URL + "/post-ticket";

    final public static String GET_ALL_POSTS_URL = BASE_URL + "/getposts";

    final public static String SIGN_UP_URL = BASE_URL + "/create-user";

    final public static String SIGN_IN_URL = BASE_URL + "/login";

    final public static String getPostDeleteUrl(int id)
    {
        return BASE_URL + "/delete-post/" + id;
    }

    final public static String SEARCH_URL = BASE_URL + "/search";

    final public static String GET_USER_POSTS = BASE_URL + "/user-posts/" + AppHelper.getUser().getId();


}
