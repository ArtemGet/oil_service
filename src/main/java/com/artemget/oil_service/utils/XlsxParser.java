package com.artemget.oil_service.utils;

import com.artemget.oil_service.model.Oil;
import com.artemget.oil_service.model.OilData;
import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@UtilityClass
public class XlsxParser {

    public static OilData parseXLSXFileToModel(String path) {
        XSSFWorkbook xssfWorkbook;
        try {
            xssfWorkbook = new XSSFWorkbook(path);
        } catch (IOException e) {
            throw new RuntimeException("Unable to parse file", e);
        }
        return parseXSSFWorkbookToModel(xssfWorkbook);
    }

    private static OilData parseXSSFWorkbookToModel(XSSFWorkbook xssfWorkbook) {
        final List<Oil> oilList = new ArrayList<>();
        long corrupted = 0;

        for (int i = 0; i < xssfWorkbook.getNumberOfSheets(); i++) {
            var oidData = parseXSSFSheetToModel(xssfWorkbook.getSheetAt(i));
            oilList.addAll(oidData.getOilList());
            corrupted += oidData.getCorrupted();
        }
        return new OilData(oilList, corrupted);
    }

    private static OilData parseXSSFSheetToModel(XSSFSheet sheet) {
        var valuePlaces = getOilParamPlaces(sheet);
        List<Oil> oilList = new ArrayList<>();
        long corrupted = 0;

        if (valuePlaces.isEmpty()) {
            return new OilData(List.of(), 0);
        }

        var rowIterator = sheet.rowIterator();
        //avoid first row with naming
        rowIterator.next();

        while (rowIterator.hasNext()) {
            var oil = collectRowData(rowIterator.next(), valuePlaces);
            if (oil != null) {
                oilList.add(oil);
            } else {
                corrupted++;
            }
        }

        return new OilData(oilList, corrupted);
    }

    //TODO add all row check
    private static Map<OilParams, Integer> getOilParamPlaces(XSSFSheet sheet) {
        var firstRow = sheet.getRow(sheet.getFirstRowNum());
        Map<OilParams, Integer> places = new HashMap<>();

        if (firstRow == null) {
            return places;
        }

        for (var iterator = firstRow.cellIterator(); iterator.hasNext(); ) {
            Cell cell = iterator.next();
            String cellValue = new String(cell.getStringCellValue().getBytes(StandardCharsets.UTF_8));

            OilParams param = OilParams.isValue(cellValue);
            if (param != null) {
                places.put(param, cell.getColumnIndex());
            }
        }

        return places;
    }

    //TODO check cell type
    private static Oil collectRowData(Row row, Map<OilParams, Integer> places) {
        try {
            if (row.getCell(places.get(OilParams.NAME)).getStringCellValue().isEmpty() &&
                    row.getCell(places.get(OilParams.OUTPUT_FIELD)).getStringCellValue().isEmpty()) {
                return null;
            }
            return Oil.builder()
                    .name(new String(row.getCell(places.get(OilParams.NAME)).getStringCellValue().getBytes(StandardCharsets.UTF_8)))
                    .outputPlace(new String(row.getCell(places.get(OilParams.OUTPUT_FIELD)).getStringCellValue().getBytes(StandardCharsets.UTF_8)))
                    .density20(row.getCell(places.get(OilParams.P20)).getNumericCellValue())
                    .density50(row.getCell(places.get(OilParams.P50)).getNumericCellValue())
                    .viscosity20(row.getCell(places.get(OilParams.V20)).getNumericCellValue())
                    .viscosity50(row.getCell(places.get(OilParams.V50)).getNumericCellValue())
                    .hk350(row.getCell(places.get(OilParams.HK_350)).getNumericCellValue())
                    .build();
        } catch (Exception e) {
            return null;
        }
    }
}
