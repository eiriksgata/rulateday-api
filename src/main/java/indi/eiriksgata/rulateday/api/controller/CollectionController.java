package indi.eiriksgata.rulateday.api.controller;

import indi.eiriksgata.rulateday.api.service.RandomPictureService;
import indi.eiriksgata.rulateday.api.vo.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return randomPictureService.collectionPictureByYinhua();
    }




}
