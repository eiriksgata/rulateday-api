package indi.eiriksgata.rulatedayapi.service.impl;

import com.github.kevinsawicki.http.HttpRequest;
import indi.eiriksgata.rulatedayapi.service.RandomPictureService;
import indi.eiriksgata.rulatedayapi.service.SystemSetService;
import indi.eiriksgata.rulatedayapi.utils.FileUtil;
import indi.eiriksgata.rulatedayapi.utils.HexConvertUtil;
import indi.eiriksgata.rulatedayapi.utils.RegularExpressionUtils;
import indi.eiriksgata.rulatedayapi.utils.RestUtil;
import indi.eiriksgata.rulatedayapi.vo.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class RandomPictureServiceImpl implements RandomPictureService {

    @Autowired
    SystemSetService systemSetService;

    @Override
    public ResponseBean<String> collectionPicture() {

        try {
            String url = "https://safebooru.donmai.us/posts?page=" + new Random().nextInt(999);
            Map<String, String> head = new HashMap<>();
            head.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36 Edg/106.0.1370.47");
            head.put("cookie", systemSetService.getPictureClearance() + ";" + systemSetService.getPictureCookieSession());

            HttpRequest request = HttpRequest.get(url);
            head.forEach(request::header);
            String resultHtml = request.body();

            String resultCookie = request.header("set-cookie");
            if (resultCookie != null) {
                String[] result = resultCookie.trim().split(";");
                if (result.length > 0) {
                    systemSetService.setPictureCookieSession(result[0]);
                }
            }

            List<String> formatText = RegularExpressionUtils.getMatchers("href=\"/posts/[0-9]+\">", resultHtml);
            String pictureId = RegularExpressionUtils.getMatcher("[0-9]+",
                    formatText.get(new Random().nextInt(10)));

            //查找大图
            url = "https://safebooru.donmai.us/posts/" + pictureId;
            resultHtml = RestUtil.get(url, head);
            String formatItem = RegularExpressionUtils.getMatcher("\\<img width=\"[0-9]+\" height=\"[0-9]+\".*>", resultHtml);
            formatItem = RegularExpressionUtils.getMatcher("src=\".*\"", formatItem);
            String pictureUrl = formatItem.substring(5, formatItem.length() - 1);

            URL requestUrl = new URL(pictureUrl);
            HttpURLConnection conn = (HttpURLConnection) requestUrl.openConnection();
            // 设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);

            // 防止屏蔽程序抓取而返回 403 错误
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36 Edg/106.0.1370.47");
            conn.setRequestProperty("cookie", systemSetService.getPictureClearance() + ";" + systemSetService.getPictureCookieSession());
            // 得到输入流
            InputStream inputStream = conn.getInputStream();

            // 获取自己数组
            byte[] getData = FileUtil.readInputStream(inputStream);
            inputStream.close();
            return ResponseBean.success(HexConvertUtil.bytesToHex(getData));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseBean.error("Server get picture fail.");
    }


}
