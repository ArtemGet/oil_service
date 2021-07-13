package com.artemget.oil_service.unit.parser;

import com.artemget.oil_service.model.Oil;
import com.artemget.oil_service.utils.TestOilProvider;
import com.artemget.oil_service.utils.XlsxParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class XlsxParserTest {

    @Test
    public void shouldParseValidSinglePageFile() {
        List<Oil> expected = List.of(TestOilProvider.provideValidOne(), TestOilProvider.provideValidTwo());
        List<Oil> actual = XlsxParser.parseXLSXFileToModel("src/test/resources/xlsx_files/valid_single_page.xlsx");
        assertEquals(expected, actual);
    }

    @Test
    public void shouldParseValidMultiPageFile() {
        List<Oil> expected = List.of(TestOilProvider.provideValidOne(), TestOilProvider.provideValidTwo(), TestOilProvider.provideValidThree());
        List<Oil> actual = XlsxParser.parseXLSXFileToModel("src/test/resources/xlsx_files/valid_multi_page.xlsx");
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnEmptyOnEmptyFile() {
        List<Oil> actual = XlsxParser.parseXLSXFileToModel("src/test/resources/xlsx_files/empty_multi_page.xlsx");
        assertTrue(actual.isEmpty());
    }

    @Test
    public void shouldReturnOnlyNotCorrupted() {
        List<Oil> expected = List.of(TestOilProvider.provideValidOne(), TestOilProvider.provideValidTwo());
        List<Oil> actual = XlsxParser.parseXLSXFileToModel("src/test/resources/xlsx_files/corrupted_multi_page.xlsx");
        assertEquals(expected, actual);
    }
}

