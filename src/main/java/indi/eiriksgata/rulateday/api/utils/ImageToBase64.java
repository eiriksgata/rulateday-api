package indi.eiriksgata.rulateday.api.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * @author: create by Keith
 * @version: v1.0
 * @description: com.ajb.com.ajb.sdk.utils
 * @date:2020/8/25
 **/
@Slf4j
public class ImageToBase64 {

    /**
     * 本地图片转换Base64的方法
     */
    public static String ImageToBase64(String imagePath) {
        String base64String = "";
        try {
            Path file = Paths.get(imagePath);
            byte[] imageData = Files.readAllBytes(file);
            base64String = Base64.getEncoder().encodeToString(imageData);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
        return base64String.replaceAll("\r|\n", "");
    }

    /**
     * Base64 转图片
     */
    public static boolean base64ToImage(String base64Str, String savePath) {
        if (base64Str.equals("")) {
            return false;
        }
        try (FileOutputStream imageOutFile = new FileOutputStream(savePath)) {
            byte[] imageData = Base64.getDecoder().decode(base64Str);
            imageOutFile.write(imageData);
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
            return false;
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
            return false;
        }
    }


}