package com.artemget.oil_service.utils;

import org.apache.commons.io.FileUtils;

import java.io.*;

public class Parser {
    public static String parseFileToString(String path) {
        try {
            return FileUtils.readFileToString(new File(path), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
