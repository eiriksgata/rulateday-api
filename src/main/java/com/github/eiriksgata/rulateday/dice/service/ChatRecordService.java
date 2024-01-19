package com.github.eiriksgata.rulateday.dice.service;

import com.github.eiriksgata.rulateday.dice.dto.DiceMessageDTO;

public interface ChatRecordService {

    void groupRecordHandler(DiceMessageDTO data);


    void botSelfMessageRecord(DiceMessageDTO data);

    String recordFileUpload(Long id);
}
