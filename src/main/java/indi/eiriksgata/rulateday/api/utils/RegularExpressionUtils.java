package indi.eiriksgata.rulateday.api.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpressionUtils {

    /**
     * 正则表达式返回内容
     *
     * @param regex 正则表达式
     * @param source 源文本
     * @return 匹配字段
     */
    public static List<String> getMatchers(String regex, String source) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }


    /**
     * 返回单个匹配数据
     * @param regex 正则表达式
     * @param source 源文本
     * @return 匹配字段
     */
    public static String getMatcher(String regex, String source) {
        if (source == null) return null;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        if (!matcher.find()) return null;
        return matcher.group();
    }
}
