package com.example.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static Date transferDate(String dateString) throws ParseException {

            System.out.println(dateString);
            // 建立SimpleDateFormat物件，指定日期格式

            SimpleDateFormat formatter = dateString.contains(":") ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"): new SimpleDateFormat("yyyy-MM-dd");

            // 將字串轉換成Date物件
            Date date = formatter.parse(dateString);

            // 印出結果
            System.out.println("轉換後的Date物件: " + date);
            return date;

    }
}
