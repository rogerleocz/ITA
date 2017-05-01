import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Jack on 2016/12/25.
 */
public class TestJson {
    public static  void main(String...args){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userName", "jack");
        jsonObject.put("userPassword", "123456");
        String json = jsonObject.toJSONString();
        System.out.println(json);
        try {
            String uurl = "http://localhost:8080/Login?"+ URLEncoder.encode(json, "utf-8");
            System.out.println(uurl);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
