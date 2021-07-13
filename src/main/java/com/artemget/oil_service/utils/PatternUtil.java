package com.artemget.oil_service.utils;


import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

@UtilityClass
public class PatternUtil {
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
//    public static final Pattern SQL_PATTERN = Pattern.compile("/[\t\r\n]|(--[^\r\n]*)|(\\/\\*[\\w\\W]*?(?=\\*)\\*/)/gi", Pattern.CASE_INSENSITIVE);
}
