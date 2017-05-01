import org.apache.http.client.config.RequestConfig;

/**
 * Created by Jack on 2016/12/8.
 */
public class HttpRequestConfig {
    public  static RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(5000)
            .setConnectTimeout(5000)
            .setConnectionRequestTimeout(5000)
            .build();
}
