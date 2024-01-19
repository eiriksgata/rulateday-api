package com.github.eiriksgata.rulateday.dice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.eiriksgata.rulateday.dice.config.GlobalData;
import com.github.eiriksgata.rulateday.dice.dto.ChatRecordDTO;
import com.github.eiriksgata.rulateday.dice.dto.DiceMessageDTO;
import com.github.eiriksgata.rulateday.dice.service.ChatRecordService;
import com.github.eiriksgata.rulateday.platform.websocket.api.ShamrockService;
import com.github.eiriksgata.trpg.dice.reply.CustomText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Service
public class ChatRecordServiceImpl implements ChatRecordService {

    @Autowired
    ShamrockService shamrockService;

    @Override
    public void groupRecordHandler(DiceMessageDTO data) {
        Long startTime = GlobalData.groupChatRecordEnableMap.get(data.getWsRequestBean().getParams().getGroup_id() + "");
        if (startTime != null) {
            if (System.currentTimeMillis() - startTime > 1000 * 60 * 60 * 5) {
                shamrockService.sendGroupMessage(
                        data.getSanderId(),
                        data.getWsRequestBean().getParams().getGroup_id(),
                        CustomText.getText("chat.record.cache.timeout")
                );

                String filename = recordFileUpload(data.getWsRequestBean().getParams().getGroup_id());
                String resultText = CustomText.getText("chat.record.close") + "\n" +
                        "http://localhost:12030/server/resources/chat/record/" + filename;
                shamrockService.sendGroupMessage(data.getSanderId(), data.getWsRequestBean().getParams().getGroup_id(), resultText);
                GlobalData.groupChatRecordEnableMap.remove(data.getWsRequestBean().getParams().getGroup_id() + "");
                GlobalData.groupChatRecordDataMap.remove(data.getWsRequestBean().getParams().getGroup_id() + "");

            } else {
                ChatRecordDTO chatRecordDTO = new ChatRecordDTO();
                chatRecordDTO.setSenderId(chatRecordDTO.getSenderId());
                String senderName = data.getWsRequestBean().getParams().getSender().getCard();

                chatRecordDTO.setSenderName(senderName);
                chatRecordDTO.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                chatRecordDTO.setContent(data.getWsRequestBean().getParams().getMessage().toString());
                GlobalData.groupChatRecordDataMap.get(data.getWsRequestBean().getParams().getGroup_id() + "")
                        .getRecords().add(chatRecordDTO);
            }
        }
    }

    @Override
    public void botSelfMessageRecord(DiceMessageDTO data) {
        long groupId = data.getWsRequestBean().getParams().getGroup_id();
        Long startTime = GlobalData.groupChatRecordEnableMap.get(groupId + "");
        if (startTime != null) {
            ChatRecordDTO chatRecordDTO = new ChatRecordDTO();
            chatRecordDTO.setSenderId(data.getSanderId());

            String senderName = data.getWsRequestBean().getParams().getSender().getCard();
            if (senderName.equals("")) {
                senderName = data.getWsRequestBean().getParams().getSender().getNickname();
            }

            chatRecordDTO.setSenderName(senderName);
            chatRecordDTO.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            chatRecordDTO.setContent(data.getWsRequestBean().getParams().getMessage().toString());
            GlobalData.groupChatRecordDataMap.get(groupId + "").getRecords().add(chatRecordDTO);
        }
    }

    public File recordsFileCreate(String text) throws IOException {
        String fileName = "group-record-" + System.currentTimeMillis() + ".json";
        String path = "resources/chat/record" + fileName;
        File file = new File(path);
        file.createNewFile();
        CustomText.fileOut(file, text);
        return file;
    }

    @Override
    public String recordFileUpload(Long id) {
        try {
            File file = recordsFileCreate(JSONObject.toJSONString(
                    GlobalData.groupChatRecordDataMap.get(id + "")));
            return file.getName();
        } catch (IOException e) {
            return null;
        }
    }

}
