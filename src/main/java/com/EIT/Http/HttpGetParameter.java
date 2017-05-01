package com.EIT.Http;

import com.EIT.commonUtil.ChangeUrl;
import com.EIT.commonUtil.Data;
import com.EIT.commonUtil.ExcelUtil;
import com.EIT.commonUtil.OperateType;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Jack on 2016/12/8.
 */
public class HttpGetParameter {

    public static String httpGetTest(String url, Data data) {
        //创建get请求
        String type = data.getParameterType();
        if (type.equalsIgnoreCase(OperateType.typeJson)) {
            try {
                url = data.getAddress()+ ChangeUrl.Change(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(url);
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
//            System.out.println("status:" + response.getStatusLine());
            responseContent = EntityUtils.toString(entity, "UTF-8");
            System.out.println(responseContent);
            if (responseContent.equalsIgnoreCase(data.getExpectResult())) {
                ExcelUtil.writeResultToList(true);
            } else {
                ExcelUtil.writeResultToList(false);
            }
        } catch (IOException e) {
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
