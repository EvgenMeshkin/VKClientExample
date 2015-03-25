package by.android.evgen.vkclientexample.spring;

import android.os.Handler;
import android.util.Log;

import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import by.android.evgen.vkclientexample.model.Response;
import by.android.evgen.vkclientexample.model.Result;

/**
 * Created by evgen on 25.03.2015.
 */
public class SpringParser {

    public void executeInThread(final ISpringCallback callback, final String... params) {
        final Handler handler = new Handler();
        callback.onDataLoadStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = params[0];
                    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
                    mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(new MediaType("json", "text", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET)));
                    RestTemplate restTemplate = new RestTemplate();
                    restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
                    String str = restTemplate.getForObject(url, String.class);
                    Log.d("******", str);
                    final Result result = restTemplate.getForObject(url, Result.class);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onDone(result);
                        }
                    });
                } catch (final Exception e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            e.printStackTrace();
                            callback.onError(e);
                        }
                    });
                }
            }
        }).start();

    }
}
