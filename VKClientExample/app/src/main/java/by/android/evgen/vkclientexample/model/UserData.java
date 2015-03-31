package by.android.evgen.vkclientexample.model;

import android.os.Parcel;
import android.os.Parcelable;

import by.android.evgen.vkclientexample.model.users.Users;

/**
 * Created by evgen on 29.03.2015.
 */
public class UserData implements Parcelable {

    String User_id;
    String User_name;
    String User_image;

    public String getUser_id() {
        return User_id;
    }

    public String getUser_name() {
        return User_name;
    }

    public String getUser_image() {
        return User_image;
    }

    public UserData(String user_id, String user_name, String user_image) {
        User_id = user_id;
        User_name = user_name;
        User_image = user_image;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(User_id);
        dest.writeString(User_name);
        dest.writeString(User_image);

    }

    public static final Parcelable.Creator<UserData> CREATOR = new Parcelable.Creator<UserData>() {
        // распаковываем объект из Parcel
        public UserData createFromParcel(Parcel in) {
           return new UserData(in);
        }
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };

    private UserData(Parcel parcel) {
        User_id = parcel.readString();
        User_name = parcel.readString();
        User_image = parcel.readString();

    }
}
