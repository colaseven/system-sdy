package com.sdy.modular.system.util;

import com.sdy.core.support.BeanKit;
import com.sdy.core.util.CommonUtil;
import com.sdy.core.util.DateUtil;
import com.sdy.core.util.ExcelXlsReader;
import com.sdy.core.util.ExcelXlsxReader;
import com.sdy.core.util.SpringUtil;
import com.sdy.core.util.ToolUtil;
import com.sdy.modular.system.model.Order;
import com.sdy.modular.system.service.IOrderService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.*;


public class ExcelReaderUtil {


    @Autowired
    private static IOrderService orderService;

    private static List<Order> orderList = new ArrayList<>();

    private final static Logger logger = LoggerFactory.getLogger(ExcelReaderUtil.class);
    private static Map<String, String> map = new HashMap<>();


    private static Map<String, String> phoneNumberMap = new HashMap<>();
    private static Set<String> ignoreSet = new HashSet<>();

    static {
        Properties properties = loadProperties(ExcelReaderUtil.class, "/orderField.properties");
        Enumeration<Object> enumeration = properties.keys();
        try {
            while (enumeration.hasMoreElements()) {
                String key = String.valueOf(enumeration.nextElement());
                String val = properties.getProperty(key).trim();
                String[] valArr = val.split("_");
                map.put(valArr[1], valArr[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Properties loadProperties(Class<?> clazz, String pathFile) {
        Properties prop = new Properties();

        try {
            Throwable var3 = null;
            Object var4 = null;

            try {
                InputStream is = clazz.getResourceAsStream(pathFile);

                try {
                    if (is == null) {
                        throw new IOException("文件流无法获取。");
                    }

                    prop.load(is);
                } finally {
                    if (is != null) {
                        is.close();
                    }

                }
            } catch (Throwable var13) {
                if (var3 == null) {
                    var3 = var13;
                } else if (var3 != var13) {
                    var3.addSuppressed(var13);
                }
            }
        } catch (Exception var14) {
            logger.info("获取Properties文件失败。" + var14);
        }
        return prop;
    }

    //excel2003扩展名
    public static final String EXCEL03_EXTENSION = ".xls";
    //excel2007扩展名
    public static final String EXCEL07_EXTENSION = ".xlsx";

    /**
     * 每获取一条记录，即打印
     * 在flume里每获取一条记录即发送，而不必缓存起来，可以大大减少内存的消耗，这里主要是针对flume读取大数据量excel来说的
     *
     * @param cellList
     * @param headerList
     */
    public static void sendRows(List<String> cellList, List<String> headerList) {
        Order order = new Order();
        Class clazz = order.getClass();
        try {
            for (int i = 0; i < cellList.size(); i++) {
                String value = cellList.get(i);
                String field = map.get(headerList.get(i));
                if (ToolUtil.isEmpty(field)) continue;
                if (field.equals("totalPayment")) {
                    value = ToolUtil.isEmpty(value) ? "0" : String.valueOf(Math.round(Double.parseDouble(value)));
                }

                if (field.endsWith("Time") && ToolUtil.isNotEmpty(value)) {
                    value = DateUtil.convertDateFormat(value, DateUtil.YYYY_MM_DD_HH_MM_SS);
                    if (field.equals("finishTime")) {
                        Date date = DateUtil.parseToDate(value);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH) + 1;
                        Method yearMethod = clazz.getMethod("setFinishYear", String.class);
                        Method monthMethod = clazz.getMethod("setFinishMonth", String.class);
                        yearMethod.invoke(order, String.valueOf(year));
                        monthMethod.invoke(order, String.valueOf(month));
                    }
                }
                Method method = clazz.getMethod("set" + CommonUtil.formatMethodName(field), String.class);
                if (method == null) continue;
                method.invoke(order, value);
                if (field.equals("totalPayment")) {
                    Method method1 = clazz.getMethod("setCredits", String.class);
                    method1.invoke(order, value);
                }

            }
            if (ToolUtil.isEmpty(order.getPhoneNumber())) return;
            orderList.add(order);
            if (orderList.size() > 300) {
                saveData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readExcel(InputStream inputStream, String fileName) throws Exception {
        int totalRows = 0;
        if (fileName.endsWith(EXCEL03_EXTENSION)) { //处理excel2003文件
            ExcelXlsReader excelXls = new ExcelXlsReader();
            totalRows = excelXls.process(inputStream);
        } else if (fileName.endsWith(EXCEL07_EXTENSION)) {//处理excel2007文件
            ExcelXlsxReader excelXlsxReader = new ExcelXlsxReader();
            totalRows = excelXlsxReader.process(inputStream);
        } else {
            throw new Exception("文件格式错误，fileName的扩展名只能是xls或xlsx。");
        }
        saveData();
        System.out.println("发送的总行数：" + totalRows);
    }

    public static void saveData() {
        if (orderService == null) {
            orderService = (IOrderService) SpringUtil.getBean("orderService");
        }
        if (orderService == null) {
            logger.error("orderService is null");
            orderList.clear();
            return;
        }
        for (Order tempOrder : orderList) {
            String phoneNumber = tempOrder.getPhoneNumber().split("/")[0];
            if (ignoreSet.contains(phoneNumber)) continue;
            String openId = map.get(phoneNumber);
            if (ToolUtil.isEmpty(openId)) {
                Order boundOrder = orderService.selectOrderByBindAccount(phoneNumber);
                if (boundOrder == null) {
                    ignoreSet.add(phoneNumber);
                    continue;
                }
                openId = boundOrder.getOpenId();
            }
            tempOrder.setBindingPhoneNumber(phoneNumber);
            tempOrder.setOpenId(openId);
        }
        orderService.saveAll(orderList);
        orderList.clear();
    }


    public static List<Order> readDataFromXls(InputStream inputStream) {

        List<Order> list = new ArrayList<>();
        try {
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStream);

            int size = hssfWorkbook.getNumberOfSheets();

            // 循环每一页，并处理当前循环页
            for (int numSheet = 0; numSheet < size; numSheet++) {
                // HSSFSheet 标识某一页
                HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }

                //获取当前页的第一行作为表头
                HSSFRow firstRow = hssfSheet.getRow(0);
                // 处理当前页，循环读取每一行
                for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    // HSSFRow表示行
                    HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                    int minColIx = hssfRow.getFirstCellNum();
                    int maxColIx = hssfRow.getLastCellNum();
                    Order order = null;
                    // 遍历改行，获取处理每个cell元素
                    for (int colIx = minColIx; colIx < maxColIx; colIx++) {
                        // HSSFCell 表示单元格
                        HSSFCell cell = hssfRow.getCell(colIx);
                        if (cell == null) {
                            continue;
                        }
                        HSSFCell headerCell = firstRow.getCell(colIx);
                        if (headerCell == null || ToolUtil.isEmpty(getStringVal(headerCell))) continue;
                        if (ToolUtil.isEmpty(getStringVal(cell))) continue;
                        String header = getStringVal(headerCell);
                        if (ToolUtil.isEmpty(map.get(header))) continue;
                        if (order == null) order = new Order();
                        String field = map.get(header);
                        String value = getStringVal(cell);
                        if (field.equals("totalPayment")) {
                            value = ToolUtil.isEmpty(value) ? "0" : String.valueOf(Math.round(Double.parseDouble(value)));
                        }

                        Class clazz = order.getClass();
                        if (field.endsWith("Time") && ToolUtil.isNotEmpty(value)) {
                            value = DateUtil.convertDateFormat(value, DateUtil.YYYY_MM_DD_HH_MM_SS);
                            if (field.equals("finishTime")) {
                                Date date = DateUtil.parseToDate(value);
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(date);
                                int year = calendar.get(Calendar.YEAR);
                                int month = calendar.get(Calendar.MONTH) + 1;
                                Method yearMethod = clazz.getMethod("setFinishYear", String.class);
                                Method monthMethod = clazz.getMethod("setFinishMonth", String.class);
                                yearMethod.invoke(order, String.valueOf(year));
                                monthMethod.invoke(order, String.valueOf(month));
                            }
                        }
                        Method method = clazz.getMethod("set" + CommonUtil.formatMethodName(field), String.class);
                        if (method == null) continue;
                        method.invoke(order, value);
                        if (field.equals("totalPayment")) {
                            Method method1 = clazz.getMethod("setCredits", String.class);
                            method1.invoke(order, value);
                        }
                    }
                    System.out.println(BeanKit.beanToMap(order).toString());
                    if (order == null || ToolUtil.isEmpty(order.getPhoneNumber())) continue;
                    list.add(order);
                    if (list.size() > 999) {
                        //TODO
                        Map<String, String> map = new HashMap<>();
                        for (Order tempOrder : list) {
                            String phoneNumber = order.getPhoneNumber().split("/")[0];
                            String openId = map.get(phoneNumber);
                            if (ToolUtil.isEmpty(openId)) {
                                Order boundOrder = orderService.selectOrderByBindAccount(phoneNumber);
                                if (boundOrder == null) continue;
                                openId = boundOrder.getOpenId();
                            }
                            tempOrder.setBindingPhoneNumber(phoneNumber);
                            tempOrder.setOpenId(openId);
                        }
                        orderService.saveAll(list);
                        list.clear();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List<Order> readDataFromXlsx(InputStream inputStream) {
        List<Order> result = new ArrayList<Order>();
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
            // 循环每一页，并处理当前循环页
            for (XSSFSheet xssfSheet : xssfWorkbook) {
                if (xssfSheet == null) {
                    continue;
                }
                // 处理当前页，循环读取每一行
                XSSFRow firstRow = xssfSheet.getRow(0);
                for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                    XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                    int minColIx = xssfRow.getFirstCellNum();
                    int maxColIx = xssfRow.getLastCellNum();
                    Order order = null;
                    for (int colIx = minColIx; colIx < maxColIx; colIx++) {
                        XSSFCell cell = xssfRow.getCell(colIx);
                        if (cell == null) {
                            continue;
                        }

                        XSSFCell headerCell = firstRow.getCell(colIx);
                        if (headerCell == null || ToolUtil.isEmpty(headerCell.toString())) continue;
                        String header = headerCell.toString();
                        if (ToolUtil.isEmpty(map.get(header))) continue;
                        if (order == null) order = new Order();
                        String field = map.get(header);
                        String value = cell.toString();
                        if (field.equals("totalPayment")) {
                            value = ToolUtil.isEmpty(value) ? "0" : String.valueOf(Math.round(Double.parseDouble(value)));
                        }

                        Class clazz = order.getClass();
                        if (field.endsWith("Time") && ToolUtil.isNotEmpty(value)) {
                            String newValue = DateUtil.convertDateFormat(value, DateUtil.YYYY_MM_DD_HH_MM_SS);
                            value = newValue;
                            if (field.equals("finishTime")) {
                                Date date = DateUtil.parse(value, DateUtil.YYYY_MM_DD_HH_MM_SS);
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(date);
                                int year = calendar.get(Calendar.YEAR);
                                int month = calendar.get(Calendar.MONTH) + 1;
                                Method yearMethod = clazz.getMethod("setFinishYear", String.class);
                                Method monthMethod = clazz.getMethod("setFinishMonth", String.class);
                                yearMethod.invoke(order, String.valueOf(year));
                                monthMethod.invoke(order, String.valueOf(month));
                            }
                        }
                        if (ToolUtil.isEmpty(value)) continue;
                        Method method = clazz.getMethod("set" + CommonUtil.formatMethodName(field), String.class);
                        if (method == null) continue;
                        method.invoke(order, value);
                        if (field.equals("totalPayment")) {
                            Method method1 = clazz.getMethod("setCredits", String.class);
                            method1.invoke(order, value);

                        }
                    }
                    if (order == null || ToolUtil.isEmpty(order.getPhoneNumber())) continue;
                    result.add(order);
                    if (result.size() > 999) {
                        //TODO
                        Map<String, String> map = new HashMap<>();
                        for (Order tempOrder : result) {
                            String phoneNumber = order.getPhoneNumber().split("/")[0];
                            String openId = map.get(phoneNumber);
                            if (ToolUtil.isEmpty(openId)) {
                                Order boundOrder = orderService.selectOrderByBindAccount(phoneNumber);
                                if (boundOrder == null) continue;
                                openId = boundOrder.getOpenId();
                            }
                            tempOrder.setBindingPhoneNumber(phoneNumber);
                            tempOrder.setOpenId(openId);
                        }
                        orderService.saveAll(result);
                        result.clear();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 改造poi默认的toString（）方法如下
     *
     * @param @param  cell
     * @param @return 设定文件
     * @return String    返回类型
     * @throws
     * @Title: getStringVal
     * @Description: 1.对于不熟悉的类型，或者为空则返回""控制串
     * 2.如果是数字，则修改单元格类型为String，然后返回String，这样就保证数字不被格式化了
     */
    public static String getStringVal(HSSFCell cell) {
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue() ? "TRUE" : "FALSE";
            case Cell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();
            case Cell.CELL_TYPE_NUMERIC:
                cell.setCellType(Cell.CELL_TYPE_STRING);
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            default:
                return "";
        }
    }

}
