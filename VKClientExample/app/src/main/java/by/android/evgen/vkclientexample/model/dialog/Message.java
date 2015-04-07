package by.android.evgen.vkclientexample.model.dialog;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

/**
 * Created by evgen on 28.03.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message extends Model {

    public String id;

    @Column(name = "out")
    public String out;

    @Column(name = "date")
    public String date;

    @Column(name = "title")
    public String title;

    @Column(name = "read_state")
    public String read_state;

    @Column(name = "body")
    public String body;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Column(name = "attachments")
    public Attachments[] attachments;

    @Column(name = "user_id")
    public String user_id;

}
