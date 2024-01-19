package com.github.eiriksgata.rulateday.dice.service;


import com.github.eiriksgata.rulateday.dice.dto.DiceMessageDTO;

public interface RandomPictureApiService {

    String urlEncodeAPI(DiceMessageDTO data, String url);
}
