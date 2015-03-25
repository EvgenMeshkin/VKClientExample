package by.android.evgen.vkclientexample;

/**
 * Created by evgen on 25.03.2015.
 */
public class Api {

    public static final String BASE_PATH_VK = "https://api.vk.com/method/";
    public static final String V_VK = "5.28";
    public static final String FRIENDS_GET = BASE_PATH_VK + "friends.get?order=name&fields=photo_200_orig, online&v=" + V_VK;

}
