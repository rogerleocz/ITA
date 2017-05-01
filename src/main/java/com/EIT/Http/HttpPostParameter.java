package com.EIT.Http;

import com.EIT.commonUtil.Data;
import com.EIT.commonUtil.ExcelUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;

/**
 * Created by Jack on 2016/12/8.
 */
public class HttpPostParameter {

    /**
     * 调用 httppost 形式数据是json 对象
     * @param httpUrl
     */
    public static void sendHttpPostJson(String httpUrl,String json,Data data) {
        System.out.println(httpUrl+"--"+json);
        HttpPost httpPost = new HttpPost(httpUrl);
        StringEntity entity = new StringEntity(json, "utf-8");//解决中文乱码问题
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        HttpPostTest(httpPost,data);
    }

    /**
     * 有参数httpPost 调用形式 参数(格式:key1=value1&key2=value2)
     *
     * @param httpUrl
     * @param params
     */
    public static void sendHttpPostKeyValue(String httpUrl, String params,Data data) {
        System.out.println(httpUrl+"--"+params);
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        try {
            //设置参数
            StringEntity stringEntity = new StringEntity(params, "UTF-8");
            stringEntity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(stringEntity);
        } catch (Exception e) {
            ExcelUtil.writeResultToList(false);
            e.printStackTrace();
            return;
        }
        HttpPostTest(httpPost,data);
    }

    public static String HttpPostTest(HttpPost httpPost,Data data) {
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
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
            if (responseContent.equalsIgnoreCase(data.getExpectResult())) {
                ExcelUtil.writeResultToList(true);
            } else {
                ExcelUtil.writeResultToList(false);
            }
        } catch (Exception e) {
            ExcelUtil.writeResultToList(false);
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
