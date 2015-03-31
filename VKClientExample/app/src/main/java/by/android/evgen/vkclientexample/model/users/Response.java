package by.android.evgen.vkclientexample.model.users;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;

/**
 * Created by evgen on 28.03.2015.
 */
public class Response extends Model{

    public String id;

    @Column(name = "first_name")
    public String first_name;

    @Column(name = "last_name")
    public String last_name;

    @Column(name = "photo_200_orig")
    public String photo_200_orig;

    @Column(name = "deactivated")
    public String deactivated;

}
