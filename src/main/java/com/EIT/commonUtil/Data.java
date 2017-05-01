package com.EIT.commonUtil;

/**
 * Created by Jack on 2016/11/28.
 */
public class Data {
    // 编号
    private String id;
    // 测试接口名称
    private String name;
    // 接口类型
    private String type;
    // 请求方式
    private String method;
    // 请求地址
    private String address;
    // 参数类型
    private String parameterType;
    // 参数值
    private String testData;
    // 预期结果
    private String expectResult;

    private int numberSheet;
    private boolean flag;

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getNumberSheet() {
        return numberSheet;
    }

    public void setNumberSheet(int numberSheet) {
        this.numberSheet = numberSheet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getTestData() {
        return testData;
    }

    public void setTestData(String testData) {
        this.testData = testData;
    }

    public String getExpectResult() {
        return expectResult;
    }

    public void setExpectResult(String expectResult) {
        this.expectResult = expectResult;
    }
}
