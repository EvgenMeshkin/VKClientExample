package by.android.evgen.vkclientexample.spring;

import android.os.Handler;
import android.util.Log;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * Created by evgen on 25.03.2015.
 */
public class SpringParser {

    public void executeInThread(final ISpringCallback callback, final String params, final Class clazz) {
        final Handler handler = new Handler();
        callback.onDataLoadStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = params;
                    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
                    mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(new MediaType("json", "text", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET)));
                    RestTemplate restTemplate = new RestTemplate();
                    restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
                    final Object result = restTemplate.getForObject(url, clazz);
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
