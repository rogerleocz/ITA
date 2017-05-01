package com.EIT.commonUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Jack on 2016/12/20.
 */
public class ChangeUrl {

    public static String Change(String url) {
        String arr[] = url.split("&");
        String info ="";
        if (arr.length > 1) {
            for (int i = 0; i < arr.length; i++) {
                String brr[] = arr[i].split("=");
                if(brr.length>1){
                    try {
                        info += brr[0]+"="+ URLEncoder.encode(url, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else{
            String brr[] = url.split("=");
            if(brr.length==2){
                try {
                    info += brr[0]+"="+ URLEncoder.encode(brr[1], "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }else{

            }
        }
        return info;
    }
}
