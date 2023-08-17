package com.szzzing.api.util;

import java.text.ParseException;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/*
* 날짜 형식 변환 관련 유틸
*/
public class DateUtil {

    public static Timestamp StringToTimestamp(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = dateFormat.parse(dateString);
            return new Timestamp(parsedDate.getTime());
        } catch(ParseException e) {
            return null;
        }
    }
}
