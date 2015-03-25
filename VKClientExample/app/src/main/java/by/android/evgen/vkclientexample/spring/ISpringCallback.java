package by.android.evgen.vkclientexample.spring;

/**
 * Created by evgen on 25.03.2015.
 */
public interface ISpringCallback<T> {

    void onDataLoadStart();

    void onDone(T data);

    void onError(Exception e);
}
