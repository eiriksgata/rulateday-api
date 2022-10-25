package indi.eiriksgata.rulatedayapi.controller;

import com.github.kevinsawicki.http.HttpRequest;
import indi.eiriksgata.rulatedayapi.service.RandomPictureService;
import indi.eiriksgata.rulatedayapi.service.SystemSetService;
import indi.eiriksgata.rulatedayapi.service.impl.RandomPictureServiceImpl;
import indi.eiriksgata.rulatedayapi.utils.FileUtil;
import indi.eiriksgata.rulatedayapi.utils.HexConvertUtil;
import indi.eiriksgata.rulatedayapi.utils.RegularExpressionUtils;
import indi.eiriksgata.rulatedayapi.utils.RestUtil;
import indi.eiriksgata.rulatedayapi.vo.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * author: create by Keith
 * version: v1.0
 * description: indi.eiriksgata.rulatedayapi.controller
 * date: 2021/6/8
 **/
@RestController
public class CollectionController {

    @Autowired
    RandomPictureService randomPictureService;

    @GetMapping("/picture/random")
    public ResponseBean<?> pictureRandom() {
        return randomPictureService.collectionPicture();
    }




}
