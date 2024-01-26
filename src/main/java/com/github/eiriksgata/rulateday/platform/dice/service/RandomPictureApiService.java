package com.github.eiriksgata.rulateday.platform.dice.service;


import com.github.eiriksgata.rulateday.platform.dice.dto.DiceMessageDTO;

public interface RandomPictureApiService {

    String urlEncodeAPI(DiceMessageDTO data, String url);
}
