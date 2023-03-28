package indi.eiriksgata.rulateday.api.service;

import indi.eiriksgata.rulateday.api.vo.ResponseBean;

public interface RandomPictureService {
    ResponseBean<String> collectionPictureBySafebooru();

    ResponseBean<String> collectionPictureByYinhua();
}
