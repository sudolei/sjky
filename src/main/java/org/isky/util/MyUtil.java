package org.isky.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyUtil {


    public static String[] resetArr(String[] args) {
        List<String> list = new ArrayList<>();
        for (String str : args) {
            int num = getPdfCount(str);
            for (int i = 0; i < num; i++) {
                list.add(str);
            }
        }
        String[] result = new String[list.size()];
        list.toArray(result);
        return result;
    }

    public static int getPdfCount(String fileName) {
        fileName = fileName.toUpperCase(Locale.ROOT);
        String regex = "\\d+FEN";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fileName);
        String count = null;
        if (matcher.find()) {
            count = matcher.group();
        }
        int result = 1;
        if (count != null) {
            result = getStrMath(count);
        }
        return result;
    }

    public static int getStrMath(String input) {
        Pattern pattern = Pattern.compile("\\d+"); // 匹配一个或多个数字
        Matcher matcher = pattern.matcher(input);
        int result = 0;
        while (matcher.find()) {
            // 打印匹配到的数字
//            System.out.println(matcher.group());
            result = Integer.parseInt(matcher.group());
        }
        return result;
    }

}
