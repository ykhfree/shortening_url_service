package com.ykh.Utils;

public class UrlUtils {

    static final int RADIX = 62;
    static final String CODEC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String encoding(int param) {
        StringBuffer sb = new StringBuffer();
        while(param > 0) {
            sb.append(CODEC.charAt((int) (param % RADIX)));
            param /= RADIX;
        }
        return sb.toString();
    }

    public static int decoding(String param) {
        int sum = 0;
        int power = 1;
        for (int i = 0; i < param.length(); i++) {
            sum += CODEC.indexOf(param.charAt(i)) * power;
            power *= RADIX;
        }
        return sum;
    }

}
