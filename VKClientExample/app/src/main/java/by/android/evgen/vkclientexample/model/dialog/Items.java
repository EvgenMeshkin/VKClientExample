package by.android.evgen.vkclientexample.model.dialog;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by evgen on 25.03.2015.
 */
@Table(name = "KeyValue")
public class Items {

    //       @Column(name = "_id")
    public String id;

    @Column(name = "first_name")
    public String first_name;

    @Column(name = "last_name")
    public String last_name;

    @Column(name = "deactivated")
    public String deactivated;

    @Column(name = "photo_200_orig")
    public String photo_200_orig;

    @Column(name = "online_app")
    public String online_app;

    @Column(name = "online")
    public Boolean online;

    @Column(name = "online_mobile")
    public String online_mobile;

    @Column(name = "unread")
    public String unread;

    @Column(name = "lists")
    public String[] lists;

    @Column(name = "message")
    public Message message;

}
