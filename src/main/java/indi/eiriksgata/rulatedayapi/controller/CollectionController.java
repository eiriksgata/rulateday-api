package indi.eiriksgata.rulatedayapi.controller;

import indi.eiriksgata.rulatedayapi.utils.FileUtil;
import indi.eiriksgata.rulatedayapi.utils.HexConvertUtil;
import indi.eiriksgata.rulatedayapi.utils.RegularExpressionUtils;
import indi.eiriksgata.rulatedayapi.utils.RestUtil;
import indi.eiriksgata.rulatedayapi.vo.ResponseBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Random;

/**
 * author: create by Keith
 * version: v1.0
 * description: indi.eiriksgata.rulatedayapi.controller
 * date: 2021/6/8
 **/
@RestController
public class CollectionController {


    @GetMapping("/picture/random")
    public ResponseBean pictureRandom() {

        String url = "https://safebooru.donmai.us/posts?page=" + new Random().nextInt(999);
        String resultHtml = RestUtil.get(url);
        List<String> formatText = RegularExpressionUtils.getMatchers("\\<a href=\"/posts/[0-9]+\">", resultHtml);
        String pictureId = RegularExpressionUtils.getMatcher("[0-9]+",
                formatText.get(new Random().nextInt(19)));

        //查找大图
        url = "https://safebooru.donmai.us/posts/" + pictureId;
        resultHtml = RestUtil.get(url);
        String formatItem = RegularExpressionUtils.getMatcher("\\<img width=\"[0-9]+\" height=\"[0-9]+\".*>", resultHtml);
        formatItem = RegularExpressionUtils.getMatcher("src=\".*\"", formatItem);
        String pictureUrl = formatItem.substring(5, formatItem.length() - 1);

        //使用代理服务
//        System.getProperties().put("proxySet", "true");
//        System.getProperties().put("proxyHost", "127.0.0.1");
//        System.getProperties().put("proxyPort", "7890");

        try {

            URL requestUrl = new URL(pictureUrl);
            HttpURLConnection conn = (HttpURLConnection) requestUrl.openConnection();
            // 设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);

            // 防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            // 得到输入流
            InputStream inputStream = conn.getInputStream();

            // 获取自己数组
            byte[] getData = FileUtil.readInputStream(inputStream);
            inputStream.close();
            return ResponseBean.success(HexConvertUtil.bytesToHex(getData));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseBean.error("Server get picture fail.");


    }
}
