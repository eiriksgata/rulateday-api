package indi.eiriksgata.rulatedayapi.service;

import indi.eiriksgata.rulatedayapi.vo.ResponseBean;

public interface RandomPictureService {
    ResponseBean<String> collectionPictureBySafebooru();

    ResponseBean<String> collectionPictureByYinhua();
}
