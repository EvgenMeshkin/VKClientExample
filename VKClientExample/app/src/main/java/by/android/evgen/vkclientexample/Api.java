package by.android.evgen.vkclientexample;

/**
 * Created by evgen on 25.03.2015.
 */
public class Api {

    public static final String BASE_PATH_VK = "https://api.vk.com/method/";
    public static final String V_VK = "5.28";
    public static final String FRIENDS_GET = BASE_PATH_VK + "friends.get?order=name&fields=photo_200_orig, online&v=" + V_VK;
    public static final String DIALOG_GET = BASE_PATH_VK + "messages.getDialogs?v=" + V_VK;
    public static final String USERS_GET = BASE_PATH_VK + "users.get?v=" + V_VK+ "&fields=photo_200_orig&user_ids=";
    public static final String USER_GET = BASE_PATH_VK + "users.get?v=" + V_VK+ "&fields=photo_200_orig";
    public static final String HISTORY_GET = BASE_PATH_VK + "messages.getHistory?v=" + V_VK+ "&user_id=";
    public static final String MESSAGE_SEND = BASE_PATH_VK + "messages.send?v=" + V_VK+ "&message=";

    public static String getSendUrl(String id, String message) {
        return MESSAGE_SEND + message + "&user_id=" + id;
    }

}
