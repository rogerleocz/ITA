import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 2016/12/8.
 */
public class HttpGetParameter {

    public static void main(String args[]) {
        //无参数
//        String url = "https://www.alipay.com/";
//        httpGetTest(url);
        //有参数
//        String login = "http://localhost:8080/Login?userName=jack&userPassword=123456";
//        httpGetTest(login);
        //手动创建参数
//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("userName", "jack"));
//        params.add(new BasicNameValuePair("userPassword", "1234561"));
//        String manualParameter = "http://localhost:8080/Login?"+ URLEncodedUtils.format(params, "UTF-8");
//        System.out.println(manualParameter);
//        httpGetTest(manualParameter);
//        HttpClientUtil.getInstance().sendHttpsGet("https://www.alipay.com/");
        // json 数据
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userName", "jack");
        jsonObject.put("userPassword", "123456");
        String json = jsonObject.toJSONString();
        try {
            String uurl = "http://localhost:8080/Login?"+ URLEncoder.encode(json,"utf-8");
            System.out.println(uurl);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static String httpGetTest(String url) {
        //创建get请求
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        httpClient = HttpClients.createDefault();
        httpGet.setConfig(HttpRequestConfig.requestConfig);
        try {
            //发送get 请求
            response = httpClient.execute(httpGet);
            //获取响应消息实体
            entity = response.getEntity();
            //响应状态
            System.out.println("status:" + response.getStatusLine());
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
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
}
