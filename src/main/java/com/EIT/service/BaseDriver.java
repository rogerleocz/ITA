package com.EIT.service;

import com.EIT.commonUtil.Data;

/**
 * Created by Jack on 2016/11/28.
 */
public interface BaseDriver {

    public void initTest();

    public void workFlow(Data data);

    public void writeResult(int sheetNumber);

}
