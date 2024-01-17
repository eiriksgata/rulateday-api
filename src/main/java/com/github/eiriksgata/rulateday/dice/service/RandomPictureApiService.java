package com.github.eiriksgata.rulateday.dice.service;



public interface RandomPictureApiService {
    String yinhuaAPI(DiceMessageDTO data);

    String urlEncodeAPI(DiceMessageDTO data, String url);
}
