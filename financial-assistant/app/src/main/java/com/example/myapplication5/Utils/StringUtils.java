package com.example.myapplication5.Utils;
//定义静态方法，做工具类使用
public class StringUtils {

/*    判断字符串是否为空*/
    public static Boolean isBlank(String st){
        Boolean res;
//        字符串为空返回真
    if (st == null || st.equals("")){
        res = true;
    }else{
        res = false;
    }
        return res;
    }

    /*判断字符串能否转成int*/
    public static boolean isInt(String str) {
        try {
            int i = Integer.parseInt(str);
            System.out.println("你输入的整数是" + i);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("你输入的不是整数");
            return false;
        }
    }
}
