package com.EIT.service;

import com.EIT.commonUtil.Data;

/**
 * Created by Jack on 2016/11/28.
 */
public interface HttpDriver {

    public void HttpGet(Data data);

    public void HttpPost(Data data);

    public void HttpsGet(Data data);

    public void HttpsPost(Data data);

}
