import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Jack on 2016/12/6.
 */
public class MyInterfaceTest {
    private static RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(5000)
            .setConnectTimeout(5000)
            .setConnectionRequestTimeout(5000)
            .build();

    public static void main(String... args) {
        String url = "http://www.163.com";
        String atest = "http://www.atestyun.com";
        String url1 = "http://www.atestyun.com/Login?userName=jack&userPassword=123456";
        String urlTest = "http://localhost:8080/GetUserByName?userName=jack";
//        httpGetTest(urlTest);
//        sendHttpPost("http://localhost:8080/GetUserByName","userName=jack");
//        Map map = new HashMap();
//        map.put("userName", "jack123");
////        map.put("passWord","123456");
//        sendHttpPost("http://localhost:8080/GetUserByName",map);
        JSONObject json = new JSONObject();
        Map a = new HashMap();
        Map b = new HashMap();
        b.put("test",2);
        b.put("test1",3);
        a.put("1", b);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("userName", "jack"));
        params.add(new BasicNameValuePair("userPassword", "123456"));
        System.out.println(JSONObject.toJSONString(a));
        //要传递的参数.
        String uu = "http://www.----aspx?" + URLEncodedUtils.format(params, "UTF-8");
        System.out.println(uu);
    }

    public static String  HttpPostTest(HttpPost httpPost){
//        HttpPost httpPost = new HttpPost(httpUrl);
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = HttpClients.createDefault();
            httpPost.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpPost);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
            System.out.println(responseContent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseContent;
        }
    }
    //sendHttpPost("http://localhost:8080/GetUserByName","userName=jack")
    public static void sendHttpPost(String httpUrl, String params) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        try {
            //设置参数
            StringEntity stringEntity = new StringEntity(params, "UTF-8");
            stringEntity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(stringEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpPostTest(httpPost);
    }

    public static void  sendHttpPost(String httpUrl, Map<String, String> maps) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        // 创建参数队列
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (String key : maps.keySet()) {
            nameValuePairs.add(new BasicNameValuePair(key, maps.get(key)));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpPostTest(httpPost);
    }
}
