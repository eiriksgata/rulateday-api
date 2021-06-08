package indi.eiriksgata.rulatedayapi;

import indi.eiriksgata.rulatedayapi.utils.RegularExpressionUtils;
import indi.eiriksgata.rulatedayapi.utils.RestUtil;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

/**
 * author: create by Keith
 * version: v1.0
 * description: indi.eiriksgata.rulatedayapi
 * date: 2021/6/8
 **/
public class CollectionTest {

    @Test
    void run() {

        String url = "https://safebooru.donmai.us/posts?page=" + new Random().nextInt(1000);
        String resultHtml = RestUtil.get(url);
        List<String> formatText = RegularExpressionUtils.getMatchers("\\<a href=\"/posts/[0-9]+\">", resultHtml);
        String pictureId = RegularExpressionUtils.getMatcher("[0-9]+",
                formatText.get(new Random().nextInt(20)));

        //查找大图
        url = "https://safebooru.donmai.us/posts/" + pictureId;
        resultHtml = RestUtil.get(url);
        String formatItem = RegularExpressionUtils.getMatcher("\\<img width=\"[0-9]+\" height=\"[0-9]+\".*>", resultHtml);
        formatItem = RegularExpressionUtils.getMatcher("src=\".*\"", formatItem);
        String pictureUrl = formatItem.substring(5, formatItem.length() - 1);



        System.out.println(pictureUrl);

    }


    @Test
    void getPosts() {
        String url = "https://safebooru.donmai.us/posts/4543885";
        String resultHtml = RestUtil.get(url);
        System.out.println(resultHtml);
    }

    @Test
    void substring() {
        String url = "src=\"https://danbooru.donmai.us/data/original/2e/f9/__ethan_winters_resident_evil_and_1_more_drawn_by_lesle_kieu__2ef905d0def35e3127e8e2d2f60bcd8e.jpg\"";
        System.out.println(url.substring(4, url.length() - 1));
    }

}
