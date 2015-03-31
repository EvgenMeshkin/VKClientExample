package by.android.evgen.vkclientexample.model.users;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by evgen on 28.03.2015.
 */
@Table(name = "KeyValue")
public class Users extends Model {
    @Column(name = "response")
    public Response[] response;
}
