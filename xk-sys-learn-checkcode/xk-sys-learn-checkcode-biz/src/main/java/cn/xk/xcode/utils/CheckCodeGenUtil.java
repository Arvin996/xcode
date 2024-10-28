package cn.xk.xcode.utils;

import cn.hutool.core.util.RandomUtil;

/**
 * @Author xuk
 * @Date 2024/8/1 08:50
 * @Version 1.0
 * @Description CheckCodeGenUtils
 */
public class CheckCodeGenUtil {
    public static String genCode(int len){
        if (4 == len){
            return gen4Code();
        }
        return gen6code();
    }

    private static String gen4Code(){
        StringBuilder stringBuilder = new StringBuilder(4);
        char c;
        for (int i = 0; i < 4; i++) {
            c = RandomUtil.randomChar();
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    private static String gen6code(){
        StringBuilder stringBuilder = new StringBuilder(6);
        char c;
        for (int i = 0; i < 6; i++) {
            c = RandomUtil.randomChar();
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }
}
