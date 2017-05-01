package com.EIT.commonUtil;

import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by Jack on 2016/11/27.
 */
public class CommonUtil {

    /**
     * 读取config.properties中的value
     * @param key
     * @return
     */
    public String getConfigByKey(String key) {
        String value = null;
        try {
            Properties properties = new Properties();
            properties.load(new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream(CommonKeys.CommonKey),"UTF-8"));
            value = properties.getProperty(key);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}
