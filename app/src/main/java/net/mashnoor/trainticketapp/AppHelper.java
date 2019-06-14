package net.mashnoor.trainticketapp;

import com.orhanobut.hawk.Hawk;

public class AppHelper {

    public static void saveUser(User user)
    {
        Hawk.put("user", user);
    }

    public static User getUser()
    {
        return Hawk.get("user");

    }

    public static boolean isLoggedIn()
    {
        return Hawk.contains("user");
    }

    public static void logOut()
    {
        Hawk.delete("user");
    }
}
