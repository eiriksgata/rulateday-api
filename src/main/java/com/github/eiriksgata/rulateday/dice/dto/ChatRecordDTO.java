package com.github.eiriksgata.rulateday.dice.dto;

import lombok.Data;

@Data
public class ChatRecordDTO {

    private long senderId;
    private String senderName;
    private String content;
    private String date;

}
