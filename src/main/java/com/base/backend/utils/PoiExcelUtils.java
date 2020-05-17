package com.base.backend.utils;

import com.google.common.collect.Lists;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class PoiExcelUtils {
    private static final Logger logger = LoggerFactory.getLogger(PoiExcelUtils.class);

    public List<Row> parseXls(File file) throws Exception {
        return parseXlsx(new FileInputStream(file));
    }

    public List<Row> parseXls(InputStream s) throws Exception {
        List<Row> rows = Lists.newLinkedList();

        HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(s);
        } catch (Exception e) {

        }
        return rows;
    }

    public List<Row> parseXlsx(File file) throws Exception {
        return parseXlsx(new FileInputStream(file));
    }

    public List<Row> parseXlsx(InputStream s) throws Exception {
        List<Row> rows = Lists.newLinkedList();

        XSSFWorkbook wb = null;
        try {
            wb = new XSSFWorkbook(s);
        } catch (Exception e) {

        }
        return rows;
    }

    private List<Row> readExcel(Workbook wb) {
        List<Row> rows = Lists.newLinkedList();

        int count = wb.getNumberOfSheets();

        Sheet sheet = null;

        // 循环遍历所有工作表
        for (int t = 0; t < count; t++) {
            sheet = wb.getSheetAt(t);

            // 获取最后行号
            int lastRowNum = sheet.getLastRowNum();

            // 最后行号大于0表示有数据
            if (lastRowNum > 0) {
            }

            Row row = null;
            // 循环读取
            for (int i = 0; i <= lastRowNum; i++) {
                row = sheet.getRow(i);
                if (row != null) {
                    rows.add(row);
                }
            }
        }
        return rows;
    }

    private String getCellValue(Cell cell) {
        Object result = "";
        if (cell != null) {
            String cellTypeName = cell.getCellTypeEnum().name();
            if (CellType.STRING.name().equalsIgnoreCase(cellTypeName)) {
                result = cell.getStringCellValue();
            } else if (CellType.NUMERIC.name().equalsIgnoreCase(cellTypeName)) {
                result = cell.getNumericCellValue();
            } else if (CellType.BOOLEAN.name().equalsIgnoreCase(cellTypeName)) {
                result = cell.getBooleanCellValue();
            } else if (CellType.FORMULA.name().equalsIgnoreCase(cellTypeName)) {
                result = cell.getCellFormula();
            } else if (CellType.ERROR.name().equalsIgnoreCase(cellTypeName)) {
                result = cell.getErrorCellValue();
            } else if (CellType.BLANK.name().equalsIgnoreCase(cellTypeName)) {
                result = "";
            }
        }
        return result.toString();
    }

    /**
     * 获取HSSFWorkbook
     *
     * @param file 文件
     * @return HSSFWorkbook
     * @throws Exception
     */
    public static HSSFWorkbook getHSSFWorkbook(File file) throws Exception {
        FileInputStream fs = null;
        try {
            return new HSSFWorkbook(fs = new FileInputStream(file));
        } catch (Exception e) {
            logger.error("get excel error.", e);
            throw e;
        } finally {
            try {
                if (fs != null) {
                    fs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 检查工作表是否存在
     *
     * @param wb        HSSFWorkbook
     * @param sheetName 工作表名字
     * @return boolean
     */
    public static boolean checkSheet(HSSFWorkbook wb, String sheetName) {
        try {
            for (int numSheets = 0; numSheets < wb.getNumberOfSheets(); numSheets++) {
                HSSFSheet sheet = wb.getSheetAt(numSheets);
                if (sheetName.equals(sheet.getSheetName())) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取所有列数据
     *
     * @param file      文件
     * @param sheetName 工作表名字
     * @return "List<List<String>>"
     */
    public static List<List<String>> getHSSFRows(File file, String sheetName) {
        List<List<String>> list = Lists.newArrayList();

        try {
            HSSFWorkbook wb = getHSSFWorkbook(file);

            if (checkSheet(wb, sheetName)) {
                // 获取工作表
                HSSFSheet sheet = wb.getSheet(sheetName);

                for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
                    if (sheet.getRow(i) != null) {
                        HSSFRow row = sheet.getRow(i);

                        List<String> rowList = Lists.newArrayList();

                        for (int cellNumOfRow = 0; cellNumOfRow < row.getLastCellNum(); cellNumOfRow++) {
                            if (null != row.getCell(cellNumOfRow)) {
                                HSSFCell cell = row.getCell(cellNumOfRow);

                                if (cell.getCellTypeEnum() == CellType.STRING) {
                                    rowList.add(cell.getStringCellValue());
                                } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                                    rowList.add(String.valueOf(cell.getNumericCellValue()).replace(".0", ""));
                                }
                            } else {
                                rowList.add("");
                            }
                        }
                        list.add(rowList);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
