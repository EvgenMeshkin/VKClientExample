package by.android.evgen.vkclientexample.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Created by evgen on 25.03.2015.
 */

public class Response {

    public String count;
    public Items[] items;

}



