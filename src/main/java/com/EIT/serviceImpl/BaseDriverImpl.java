package com.EIT.serviceImpl;
import com.EIT.Http.HttpGetParameter;
import com.EIT.commonUtil.*;
import com.EIT.service.BaseDriver;
import com.EIT.service.HttpDriver;
import org.openqa.selenium.WebDriver;

import java.util.List;

/**
 * Created by Jack on 2016/11/28.
 */
public class BaseDriverImpl implements BaseDriver {

    private HttpDriver httpDriver;
    public BaseDriverImpl() {
        httpDriver = new HttpDriverImpl();
    }

    public void initTest() {

//        System.out.println("jack");
    }

    /**
     * 测试不同的请求,分配到不同的调用方法
     * @param data
     */
    public void workFlow(Data data) {

        //httpGet请求
        if (data.getType().equalsIgnoreCase(OperateType.http)&&data.getMethod().equalsIgnoreCase(OperateType.httpGet)) {
            httpDriver.HttpGet(data);
        }

        //httpPost请求
        if (data.getType().equalsIgnoreCase(OperateType.http)&&data.getMethod().equalsIgnoreCase(OperateType.httpPost)) {
            httpDriver.HttpPost(data);
        }
    }

    /**
     * 写操作结果到文件
     *
     * @param sheetNumber 既case的编号
     */
    public void writeResult(int sheetNumber) {
        List<Boolean> list = Keys.resultList;
        int reportValue = Integer.parseInt(new CommonUtil().getConfigByKey(CommonKeys.ReportKey));
        try{
            if (reportValue == 1) {
                ExcelUtil.writeResult(sheetNumber, list);
            } else if (reportValue == 2 ) {
                ExcelUtil.writeResultToHtml(sheetNumber,list);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            Keys.resultList = null;
        }
    }
}
