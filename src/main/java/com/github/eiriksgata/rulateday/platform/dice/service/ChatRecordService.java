package com.github.eiriksgata.rulateday.platform.dice.service;

import com.github.eiriksgata.rulateday.platform.dice.dto.DiceMessageDTO;

public interface ChatRecordService {

    void groupRecordHandler(DiceMessageDTO data);

    void botSelfMessageRecord(Long groupId,String nickname, Long selfId, String message);

    String recordFileUpload(Long id);
}
