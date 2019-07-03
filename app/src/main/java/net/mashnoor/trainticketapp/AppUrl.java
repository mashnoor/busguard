package net.mashnoor.trainticketapp;

public class AppUrl {

    final private static String BASE_URL = "http://mashnoor.serveo.net/busscanbackend/public/api";

    final public static String SEND_REPORT = BASE_URL + "/makereport";

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
