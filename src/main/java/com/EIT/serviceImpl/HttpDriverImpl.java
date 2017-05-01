package com.EIT.serviceImpl;

import com.EIT.Http.HttpGetParameter;
import com.EIT.Http.HttpPostParameter;
import com.EIT.commonUtil.Data;
import com.EIT.commonUtil.OperateType;
import com.EIT.service.HttpDriver;

/**
 * Created by Jack on 2016/11/28.
 */
public class HttpDriverImpl implements HttpDriver {

    public void HttpGet(Data data) {
        String address = data.getAddress();
        String type = data.getParameterType();
        String url = null;
        String value = data.getTestData();
        if (type.equalsIgnoreCase(OperateType.typeKeyValue)) {
            url = address + value;
        }else if(type.equalsIgnoreCase(OperateType.typeJson)){
            url = value;
        }
        HttpGetParameter.httpGetTest(url, data);
    }

    public void HttpPost(Data data) {
        String address = data.getAddress();
        String type = data.getParameterType();
        String value = data.getTestData();
        if(type.equalsIgnoreCase(OperateType.typeKeyValue)){
            HttpPostParameter.sendHttpPostKeyValue(address,value,data);
        }else if(type.equalsIgnoreCase(OperateType.typeJson)){
            HttpPostParameter.sendHttpPostJson(address,value,data);
        }
    }

    public void HttpsGet(Data data) {

    }

    public void HttpsPost(Data data) {

    }
}
