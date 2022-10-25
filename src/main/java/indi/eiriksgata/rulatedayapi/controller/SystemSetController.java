package indi.eiriksgata.rulatedayapi.controller;

import indi.eiriksgata.rulatedayapi.service.SystemSetService;
import indi.eiriksgata.rulatedayapi.vo.PictureCookieVo;
import indi.eiriksgata.rulatedayapi.vo.ResponseBean;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping("/api/v1")
public class SystemSetController {

    @Autowired
    SystemSetService systemSetService;

    @PutMapping("/system/set/picture/cookie")
    public ResponseBean<?> setPictureRandomCookie(@RequestBody PictureCookieVo pictureCookieVo) {
        systemSetService.setPictureCookieSession(pictureCookieVo.getCookieSession());
        systemSetService.setPictureClearance(pictureCookieVo.getClearance());
        return ResponseBean.success();
    }

}
