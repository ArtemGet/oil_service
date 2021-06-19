package com.artemget.oil_service.utils;

public enum ExpectedPages {
    EXPECTED_MAIN_PAGE(Parser.parseFileToString("src/test/resources/view_pages/test_main_page.html"));
    private final String page;

    ExpectedPages(String page) {
        this.page = page;
    }

    public String getPage() {
        return page;
    }
}
