package com.artemget.oil_service.utils;


import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

@UtilityClass
public class PatternUtil {
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
}
