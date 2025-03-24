package com.example.demo.bean;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
public class PharmacyVo {

    // 定義星期幾的完整列表與縮寫的對應
    private static final String[] DAYS_OF_WEEK = {"Mon", "Tue", "Wed", "Thur", "Fri", "Sat", "Sun"};

    public PharmacyVo(){}

    public PharmacyVo(JSONObject object){
        this.name = object.getString("name");
        this.cashBalance = object.getBigDecimal("cashBalance");
        String openingHours = object.getString("openingHours");
        List<String> diff = List.of(openingHours.replaceAll(" ", "").split("/"));
        // 提取時間內容
        diff.forEach((e) -> {
            String timePattern = "(\\d{2}:\\d{2}-\\d{2}:\\d{2})";
            Pattern pattern = Pattern.compile(timePattern);
            Pattern datePattern = Pattern.compile("^((?:Mon|Tue|Wed|Thur|Fri|Sat|Sun)(?:,(?:Mon|Tue|Wed|Thur|Fri|Sat|Sun))*(?:-(?:Mon|Tue|Wed|Thur|Fri|Sat|Sun))?)");
            Matcher dateMatcher = datePattern.matcher(e);
            Matcher timeMatcher = pattern.matcher(e);
            String startTime;
            String endTime;
            if (timeMatcher.find()) {
                String time = timeMatcher.group(1); // 提取匹配的時間部分
                System.out.println("原始資料: " + e);
                System.out.println("提取的時間: " + time);

                    // 使用split來分割開始時間和結束時間
                String[] timeParts = time.split("-");
                startTime = timeParts[0] + ":00"; // 開始時間
                endTime = timeParts[1] + ":00";   // 結束時間

                System.out.println("開始時間: " + startTime);
                System.out.println("結束時間: " + endTime);
                } else {
                endTime = "";
                startTime = "";
            }
            if (dateMatcher.find()) {
                String dateStr = dateMatcher.group(1);
                System.out.println("日期部分: " + dateStr);

                // 提取日期細節
                List<String> extractedDays = extractDays(dateStr);
                System.out.println("縮寫日期: " + extractedDays);
                extractedDays.forEach((week) -> {
                    OpeningHourVo openingHourVo = new OpeningHourVo();
                    openingHourVo.setWeek(week);
                    openingHourVo.setStartTime(Time.valueOf(startTime));
                    openingHourVo.setEndTime(Time.valueOf(endTime));
                    this.openingHours.add(openingHourVo);
                });
            }

        });
        JSONArray masks =  object.getJSONArray("masks");
        masks.forEach((e) -> {
            JSONObject obj = new JSONObject(e.toString());
            MaskVo maskVo = new MaskVo(obj);
            this.masks.add(maskVo);
        });
    }

    // 處理日期字串，支持逗號分隔和範圍表示
    private static List<String> extractDays(String dateStr) {
        List<String> days = new ArrayList<>();

        // 檢查是否包含日期範圍（例如Mon-Fri）
        if (dateStr.contains("-")) {
            String[] range = dateStr.split("-");
            String startDay = range[0];
            String endDay = range[1];

            int startIndex = Arrays.asList(DAYS_OF_WEEK).indexOf(startDay);
            int endIndex = Arrays.asList(DAYS_OF_WEEK).indexOf(endDay);

            if (startIndex != -1 && endIndex != -1) {
                for (int i = startIndex; i <= endIndex; i++) {
                    days.add(DAYS_OF_WEEK[i]);
                }
            }
        } else if (dateStr.contains(",")) {
            // 處理逗號分隔的日期
            String[] dayArray = dateStr.split(",");
            days.addAll(Arrays.asList(dayArray));
        } else {
            // 單一日期
            days.add(dateStr);
        }

        return days;
    }

    private String name;

    private BigDecimal cashBalance;

    private List<MaskVo> masks = new ArrayList<>();

    private List<OpeningHourVo> openingHours = new ArrayList<>();
}
