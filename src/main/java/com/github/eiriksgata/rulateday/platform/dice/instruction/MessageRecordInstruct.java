package com.github.eiriksgata.rulateday.platform.dice.instruction;

import com.alibaba.fastjson.JSONObject;
import com.github.eiriksgata.rulateday.platform.dice.config.GlobalData;
import com.github.eiriksgata.rulateday.platform.dice.dto.ChatRecordDTO;
import com.github.eiriksgata.rulateday.platform.dice.dto.DiceMessageDTO;
import com.github.eiriksgata.rulateday.platform.dice.dto.GroupRecordDTO;
import com.github.eiriksgata.rulateday.platform.dice.service.ChatRecordService;
import com.github.eiriksgata.rulateday.platform.websocket.api.ShamrockService;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.EventEnum;
import com.github.eiriksgata.trpg.dice.injection.InstructReflex;
import com.github.eiriksgata.trpg.dice.injection.InstructService;
import com.github.eiriksgata.trpg.dice.reply.CustomText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@InstructService
@Component
public class MessageRecordInstruct {

    @Autowired
    ShamrockService shamrockService;

    @Autowired
    ChatRecordService chatRecordService;

    @InstructReflex(value = {"logon", "log-on"}, priority = 2)
    public String openGroupRecord(DiceMessageDTO data) {
        if (data.getMessageEvent().getSub_type().equals(EventEnum.MessageSubType.NORMAL.getName())) {
            if (GlobalData.groupChatRecordEnableMap.get(data.getMessageEvent().getGroup_id() + "") == null) {
                GlobalData.groupChatRecordEnableMap.put(data.getMessageEvent().getGroup_id() + "", System.currentTimeMillis());
                String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                GroupRecordDTO temp = new GroupRecordDTO();

                temp.setGroupId(data.getMessageEvent().getGroup_id());
                temp.setGroupName("NULL");
                temp.setCreatedAt(currentDate);
                temp.setCreatedById(data.getSanderId());

                String senderName = data.getMessageEvent().getSender().getNickname();

                ChatRecordDTO chatRecordDTO = new ChatRecordDTO();
                chatRecordDTO.setSenderName(senderName);
                chatRecordDTO.setSenderId(data.getSanderId());
                chatRecordDTO.setDate(currentDate);
                chatRecordDTO.setContent(JSONObject.toJSONString(data.getMessageEvent().getMessage()));

                temp.getRecords().add(chatRecordDTO);

                GlobalData.groupChatRecordDataMap.put(data.getMessageEvent().getGroup_id() + "", temp);

                shamrockService.sendGroupMessage(
                        data.getSanderId(),
                        data.getMessageEvent().getGroup_id(),
                        CustomText.getText("chat.record.open"),
                        data.getWsServerEndpoint()
                );

            } else {
                shamrockService.sendGroupMessage(
                        data.getSanderId(),
                        data.getMessageEvent().getGroup_id(),
                        CustomText.getText("chat.record.opened"),
                        data.getWsServerEndpoint());
            }
        }

        return null;
    }

    @InstructReflex(value = {"logoff", "log-off"}, priority = 2)
    public String closeGroupRecord(DiceMessageDTO data) {
        if (data.getMessageEvent().getSub_type().equals(EventEnum.MessageSubType.NORMAL.getName())) {

            if (GlobalData.groupChatRecordEnableMap.get(data.getMessageEvent().getGroup_id() + "") == null) {
                shamrockService.sendGroupMessage(
                        data.getSanderId(),
                        data.getMessageEvent().getGroup_id(),
                        CustomText.getText("chat.record.closed"),
                        data.getWsServerEndpoint());
                return null;
            }

            //TODO：上传文件API
            String fileName = chatRecordService.recordFileUpload(data.getMessageEvent().getGroup_id());

            String resultText = CustomText.getText("chat.record.close") + "\n" + "http://localhost:12030/server/resources/chat/record/" + fileName;
            shamrockService.sendGroupMessage(data.getSanderId(), data.getMessageEvent().getGroup_id(), resultText, data.getWsServerEndpoint());
            GlobalData.groupChatRecordEnableMap.remove(data.getMessageEvent().getGroup_id() + "");
            GlobalData.groupChatRecordDataMap.remove(data.getMessageEvent().getGroup_id() + "");
        }
        return null;
    }

}
