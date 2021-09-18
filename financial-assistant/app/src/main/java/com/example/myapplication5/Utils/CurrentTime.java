package com.example.myapplication5.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CurrentTime {
    public static int getCurrentMonth(){
        Calendar c1 = Calendar.getInstance();
        return c1.get(Calendar.MONTH)+1;
    }
    public  static int getCurrentYear(){
        Calendar c1 = Calendar.getInstance();
        return c1.get(Calendar.YEAR);
    }

    public static String getCurrentTime() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH点mm分ss秒");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
}
