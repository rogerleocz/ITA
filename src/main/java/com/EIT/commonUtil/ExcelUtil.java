package com.EIT.commonUtil;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 2016/11/27.
 */
public class ExcelUtil {

    public static String htmlPath = new CommonUtil().getConfigByKey(CommonKeys.HtmlKeys);
    public static String keys = new CommonUtil().getConfigByKey(CommonKeys.TitleKeys);
    public static List<List<Data>> lists = new ArrayList<List<Data>>();

    /**
     * 读取配置文件下所有测试数据到list并返回(并保存到 lists一份)
     * @return  excel 中所有测试数据的集合List<Data>
     */
    public static List<Data> getExcelData() {

        String dataPath = null;
        InputStream is = null;
        dataPath = new CommonUtil().getConfigByKey(CommonKeys.TestDataKey);
        List<Data> list = new ArrayList<Data>();
        HSSFWorkbook hssfWorkbook = null;
        try {
            is = new FileInputStream(dataPath);
            hssfWorkbook = new HSSFWorkbook(is);
            int numSheet;
            for (numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                // Read the Row
                List<Data> dataList = new ArrayList<Data>();
                for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow != null) {
                        Data data = new Data();
                        HSSFCell id = hssfRow.getCell(0);
                        HSSFCell name = hssfRow.getCell(1);
                        HSSFCell type = hssfRow.getCell(2);
                        HSSFCell method = hssfRow.getCell(3);
                        HSSFCell address = hssfRow.getCell(4);
                        HSSFCell parameterType = hssfRow.getCell(5);
                        HSSFCell testData = hssfRow.getCell(6);
                        HSSFCell result = hssfRow.getCell(7);
                        data.setId(getValue(id));
                        data.setName(getValue(name));
                        data.setType(getValue(type));
                        data.setMethod(getValue(method));
                        data.setAddress(getValue(address));
                        data.setParameterType(getValue(parameterType));
                        if(getValue(testData).length()==0){
                            data.setTestData(null);
                        }else{
                            data.setTestData(getValue(testData));
                        }
                        if(getValue(result).length()==0){
                            data.setExpectResult(null);
                        }else{
                            data.setExpectResult(getValue(result));
                        }
                        data.setNumberSheet(numSheet);
                        list.add(data);
                        dataList.add(data);
                    }
                }
                lists.add(dataList);
                Data data = new Data();
                data.setFlag(true);
                data.setNumberSheet(numSheet);
                list.add(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                hssfWorkbook.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    private static String getValue(HSSFCell hssfCell) {

        try{
            if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
                return String.valueOf(hssfCell.getBooleanCellValue());
            } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
                return String.valueOf((int) hssfCell.getNumericCellValue());
            } else {
                return String.valueOf(hssfCell.getStringCellValue());
            }
        }catch (Exception e){
            System.out.println("字段为空,该方法出现异常,可以忽略");
        }
        return "";
    }

    public static void writeResultToList(Boolean flag) {
        if (Keys.resultList == null) {
            Keys.resultList = new ArrayList<Boolean>();
        }
        Keys.resultList.add(flag);
    }

    /**
     * 将测试结果写入到excel
     *
     * @param sheetNumber
     * @param list        测试结果
     */
    public static void writeResult(int sheetNumber, List<Boolean> list) {

        String dataPath = new CommonUtil().getConfigByKey(CommonKeys.TestDataKey);
        InputStream in = null;
        HSSFWorkbook hssfWorkbook = null;
        HSSFSheet hssfSheet = null;
        FileOutputStream fileOutputStream = null;
        try {
            in = new FileInputStream(dataPath);
            hssfWorkbook = new HSSFWorkbook(in);
            for (int i = 0; i < list.size(); i++) {
                boolean flag = list.get(i);
                hssfSheet = hssfWorkbook.getSheetAt(sheetNumber);
                HSSFRow hssfRow = hssfSheet.getRow(i + 1);
                Cell cell = hssfRow.createCell(8);
                HSSFCellStyle style = hssfWorkbook.createCellStyle();
                style.setAlignment(HorizontalAlignment.CENTER);
                style.setVerticalAlignment(VerticalAlignment.CENTER);
                if (flag == true) {
                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    style.setFillForegroundColor(HSSFColor.GREEN.index);
                } else {
                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    style.setFillForegroundColor(HSSFColor.RED.index);
                }
                cell.setCellValue(flag);
                cell.setCellStyle(style);
            }
            fileOutputStream = new FileOutputStream(dataPath);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                hssfWorkbook.write(fileOutputStream);
                in.close();
                hssfWorkbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将测试结果写到html文件,已case 为分隔,一个case 生成一个html
     * @param sheetNumber
     * @param result
     */
    public static void writeResultToHtml(int sheetNumber, List<Boolean> result) {

        StringBuilder stringBuilder = new StringBuilder();
        PrintStream printStream = null;
        String space = "&nbsp";
        String[] str = keys.split(",");
        String htmlName = htmlPath + "接口测试报告-" + sheetNumber + ".html";
        try {
            printStream = new PrintStream(new FileOutputStream(htmlName, true));
            stringBuilder.append("<html>");
            stringBuilder.append("<head>");
            stringBuilder.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\">");
            stringBuilder.append("<title>接口测试报告</title>");
            stringBuilder.append("</head>");
            stringBuilder.append("<body>");
            stringBuilder.append("<div align=\"center\">");
            stringBuilder.append("<h1 style=\"text-align:center\">接口测试报告</h1>");
            stringBuilder.append("<table border=\"1\" align=\"center\" cellspacing=\"0\" style=\"border-collapse:collapse;\">");
            stringBuilder.append("<tr>");
            for (int i = 0; i < str.length; i++) {
                stringBuilder.append("<th>" + str[i] + "</th>");
            }
            stringBuilder.append("</tr>");
            for (int j = 0; j < lists.get(sheetNumber).size(); j++) {
                Data data = lists.get(sheetNumber).get(j);
                stringBuilder.append("<tr>");
                stringBuilder.append("<td align=\"center\">" + space + data.getId() + space + "</td>");
                stringBuilder.append("<td align=\"center\">" + space + data.getName() + space + "</td>");
                stringBuilder.append("<td align=\"center\">" + space + data.getType() + space + "</td>");
                stringBuilder.append("<td align=\"center\">" + space + data.getMethod() + space + "</td>");
                stringBuilder.append("<td align=\"center\">" + space + data.getAddress() + space + "</td>");
                stringBuilder.append("<td align=\"center\">" + space + data.getParameterType()+ space + "</td>");
                stringBuilder.append("<td align=\"center\">" + space + data.getTestData() + space + "</td>");
                stringBuilder.append("<td align=\"center\">" + space + data.getExpectResult() + space + "</td>");
                String flag = result.get(j).toString();
                if (flag.equalsIgnoreCase("true")) {
                    stringBuilder.append("<td align=\"center\"><font size=\"4\" color=\"green\">" + "<strong>" + space + "成功" + space + "</strong></font></td>");
                } else {
                    stringBuilder.append("<td align=\"center\"><font size=\"4\" color=\"red\">" + "<strong>" + space + "失败" + space + "</strong></font></td>");
                }
                stringBuilder.append("<tr/>");
            }
            stringBuilder.append("</div></body></html>");
            printStream.append(stringBuilder);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            printStream.close();
        }
    }
}
