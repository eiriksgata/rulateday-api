package com.github.eiriksgata.rulateday.platform;

import indi.eiriksgata.dice.operation.impl.RollBasicsImpl;
import com.github.eiriksgata.rulateday.platform.utils.RestUtil;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CollectionTest {

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

    @Test
    void uuidTest() {

        System.out.println(
                new Timestamp(System.currentTimeMillis()).toString().substring(0, 19)
        );

    }

    @Test
    void stringHandler() {
        String str = "123456,";
        String[] strList = str.split(",");
        for (String temp : strList) {
            System.out.println(temp);
        }

    }

    @Test
    void handlerSetCookie() {
        String text = "_danbooru2_session=9aAM22nzV%2FuYmL5kCT9bX%2BlLM8gaSw%2BhTzjtAVgdLiRdXB5iyw3GlAd2OcZ1mrVbSCgTGwbCOfamLnpTky0fLmdkxkcncnTx1524BXhKBXIgPNMI%2BNSzOFhmkSZWBYk0R43vpM7qOLIk%2BVxhlJyRRUZ4uNDzHZ5oWopkmOrK%2BT47ieLWbeGWlKJhYHTQc3BD6aliGV1y8HVa94dI0TtQP7RtTrZCQr%2BEcl0NMuAZjaQxYEK3jkLU7P1T8eEpaZZAulFxYRtm4IR4kORrL0SeQbn9Km0myCViMNDxIN3bQ0%2BGBTRXKmu5cnhurUJvwTAXyMqTjXXU8u53ROpdNbmgDkvExNHEOX%2FtLmA1%2FddfmdhLUB1B6Zg%2FJY54mLbPl%2FcQ2goRlg%3D%3D--fmhCmWKnaO5bnt4m--AtgIffQVjtWylD%2BvP6JAsA%3D%3D; domain=.donmai.us; path=/; expires=Tue, 21 Oct 2042 02:23:49 GMT; secure; HttpOnly; SameSite=Lax";
        String[] result = text.trim().split(";");
        System.out.println(result[0]);
    }


    @Test
    void encode() {
        String result = BCrypt.hashpw("5014138c10816c653dcee9ad9c011a4132d14a6cdca9950851f1eb8fd039b66d", BCrypt.gensalt());
        System.out.println(result);
    }


    @Test
    void getRandomText() {

    }


    @Test
    void dice() {
        String result = new RollBasicsImpl().rollRandom("d+5d+3d2+1d3+d+5d6+d", 1000000L);
        System.out.println(result);
    }


    @Test
    void time() {
        String openTimeDateFortString = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(
                1685504575L * 1000
        ));
        System.out.println(openTimeDateFortString);

    }

    @Test
    void pathTest() {
        System.out.println(System.getProperty("user.dir") + java.io.File.separator + "images");
    }

    @Test
    void formatCardNo() {
        String serverDataCardNo = "0018347184";
        DecimalFormat cardNoFormat = new DecimalFormat("0000000000");
        System.out.println("自动补0:" + cardNoFormat.format(Long.parseLong(serverDataCardNo)));
    }


}
