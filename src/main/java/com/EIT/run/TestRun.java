package com.EIT.run;

import com.EIT.commonUtil.Data;
import com.EIT.commonUtil.ExcelUtil;
import com.EIT.service.BaseDriver;
import com.EIT.serviceImpl.BaseDriverImpl;

import java.util.List;

/**
 * Created by Jack on 2016/12/13.
 */
public class TestRun {
    public static void main(String args[]) {
        BaseDriver baseDriver = new BaseDriverImpl();
        List<Data> list = ExcelUtil.getExcelData();
        for (int i = 0; i < list.size(); i++) {
            Data data = list.get(i);
            if (data.getFlag() != true) {
                baseDriver.workFlow(data);
            } else {
                baseDriver.writeResult(data.getNumberSheet());
            }
        }
    }
}
