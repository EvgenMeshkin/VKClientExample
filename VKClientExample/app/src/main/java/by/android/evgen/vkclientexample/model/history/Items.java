package by.android.evgen.vkclientexample.model.history;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

import by.android.evgen.vkclientexample.model.dialog.*;

/**
 * Created by evgen on 25.03.2015.
 */
@Table(name = "KeyValue")
public class Items {

    //       @Column(name = "_id")
    public String id;

    @Column(name = "body")
    public String body;

    @Column(name = "user_id")
    public String user_id;

    @Column(name = "from_id")
    public String from_id;

    @Column(name = "date")
    public String date;

    @Column(name = "read_state")
    public String read_state;

    @Column(name = "out")
    public Boolean out;

    @Column(name = "online_mobile")
    public String online_mobile;

    @Column(name = "lists")
    public String[] lists;

    @Column(name = "message")
    public Message message;

    @Column(name = "emoji")
    public String emoji;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Column(name = "attachments")
    public by.android.evgen.vkclientexample.model.dialog.Attachments[] attachments;

}
