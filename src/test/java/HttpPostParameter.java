import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jack on 2016/12/8.
 */
public class HttpPostParameter {

    public static void main(String args[]) {
        //无参数
//        String url = "http://www.Atestyun.com";
//        String urlll = "http://www.163.com";
//        sendHttpPost(urlll);
//        有参数
        sendHttpPost("http://localhost:8080/GetUserByName", "userpasswod=123456");
//        Map<String, String> maps = new HashMap<String, String>();
//        maps.put("userName", "JackWangHao");
//        sendHttpPost("http://localhost:8080/GetUserByName", maps);
//        sendHttpPostJson("http://localhost:8080/GetUserByNameJson");
        sendHttpPostJsonArray("http://localhost:8080/GetUserByNameJsonArray");
    }


    /**
     * 调用 httppost 形式数据是json 对象
     * @param httpUrl
     */
    public static void sendHttpPostJson(String httpUrl) {
        HttpPost httpPost = new HttpPost(httpUrl);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userName1", "JackWangHao");
        jsonObject.put("passWord1", "12345678");
        System.out.println(JSON.toJSONString(jsonObject));
        System.out.println(jsonObject.toString());
        StringEntity entity = new StringEntity(jsonObject.toString(), "utf-8");//解决中文乱码问题
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        HttpPostTest(httpPost);
    }

    /**
     * 调用 httppost 形式数据是json 数组
     * @param httpUrl
     */
    public static void sendHttpPostJsonArray(String httpUrl) {
        HttpPost httpPost = new HttpPost(httpUrl);
        Map map1 = new HashMap();
        map1.put("userName","jack");
        map1.put("passWord","Liming");
        Map map2 = new HashMap();
        map2.put("userName","jack1");
        map2.put("passWord","Liming1");
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(map1);
        jsonArray.add(map2);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userName", "JackWangHao");
        jsonObject.put("passWord", "12345678");
        jsonObject.put("test",jsonArray);
        System.out.println(JSON.toJSONString(jsonObject));
        StringEntity entity = new StringEntity(jsonObject.toString(), "utf-8");//解决中文乱码问题
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        HttpPostTest(httpPost);
    }

    /**
     * 无法参数的调用 httppost 形式
     *
     * @param httpUrl
     */
    public static void sendHttpPost(String httpUrl) {
        HttpPost httpPost = new HttpPost(httpUrl);
        HttpPostTest(httpPost);
    }

    /**
     * 有参数httpPost 调用形式 参数(格式:key1=value1&key2=value2)
     *
     * @param httpUrl
     * @param params
     */
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

    /**
     * 有参数httpPost 调用形式 map参数本质还是(格式:key1=value1&key2=value2)
     *
     * @param httpUrl
     * @param maps
     */
    public static void sendHttpPost(String httpUrl, Map<String, String> maps) {
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


    public static String HttpPostTest(HttpPost httpPost) {
//        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = HttpClients.createDefault();
            httpPost.setConfig(HttpRequestConfig.requestConfig);
            // 执行请求
            response = httpClient.execute(httpPost);
            System.out.println(response.getStatusLine());
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
}
