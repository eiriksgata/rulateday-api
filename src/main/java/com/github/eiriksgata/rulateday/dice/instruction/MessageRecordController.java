package com.github.eiriksgata.rulateday.dice.instruction;

import com.github.eiriksgata.rulateday.dice.config.GlobalData;
import com.github.eiriksgata.rulateday.dice.dto.ChatRecordDTO;
import com.github.eiriksgata.rulateday.dice.dto.DiceMessageDTO;
import com.github.eiriksgata.rulateday.dice.dto.GroupRecordDTO;
import com.github.eiriksgata.rulateday.dice.service.ChatRecordService;
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
public class MessageRecordController {

    @Autowired
    ShamrockService shamrockService;

    @Autowired
    ChatRecordService chatRecordService;

    @InstructReflex(value = {"logon", "log-on"}, priority = 2)
    public String openGroupRecord(DiceMessageDTO data) {
        if (data.getWsRequestBean().getParams().getSub_type().equals(EventEnum.MessageSubType.NORMAL.getName())) {
            if (GlobalData.groupChatRecordEnableMap.get(data.getWsRequestBean().getParams().getGroup_id() + "") == null) {
                GlobalData.groupChatRecordEnableMap.put(data.getWsRequestBean().getParams().getGroup_id() + "", System.currentTimeMillis());
                String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                GroupRecordDTO temp = new GroupRecordDTO();

                temp.setGroupId(data.getWsRequestBean().getParams().getGroup_id());
                temp.setGroupName("NULL");
                temp.setCreatedAt(currentDate);
                temp.setCreatedById(data.getSanderId());

                String senderName = data.getWsRequestBean().getParams().getSender().getNickname();

                ChatRecordDTO chatRecordDTO = new ChatRecordDTO();
                chatRecordDTO.setSenderName(senderName);
                chatRecordDTO.setSenderId(data.getSanderId());
                chatRecordDTO.setDate(currentDate);
                chatRecordDTO.setContent(data.getWsRequestBean().getParams().getMessage().toString());

                temp.getRecords().add(chatRecordDTO);

                GlobalData.groupChatRecordDataMap.put(data.getWsRequestBean().getParams().getGroup_id() + "", temp);

                shamrockService.sendGroupMessage(data.getSanderId(), data.getWsRequestBean().getParams().getGroup_id(), CustomText.getText("chat.record.open"));

            } else {
                shamrockService.sendGroupMessage(data.getSanderId(), data.getWsRequestBean().getParams().getGroup_id(), CustomText.getText("chat.record.opened"));
            }
        }

        return null;
    }

    @InstructReflex(value = {"logoff", "log-off"}, priority = 2)
    public String closeGroupRecord(DiceMessageDTO data) {
        if (data.getWsRequestBean().getParams().getSub_type().equals(EventEnum.MessageSubType.NORMAL.getName())) {

            if (GlobalData.groupChatRecordEnableMap.get(data.getWsRequestBean().getParams().getGroup_id() + "") == null) {
                shamrockService.sendGroupMessage(
                        data.getSanderId(),
                        data.getWsRequestBean().getParams().getGroup_id(),
                        CustomText.getText("chat.record.closed"));
                return null;
            }

            //TODO：上传文件API
            String fileName = chatRecordService.recordFileUpload(data.getWsRequestBean().getParams().getGroup_id());

            String resultText = CustomText.getText("chat.record.close") + "\n" + "http://localhost:12030/server/resources/chat/record/" + fileName;
            shamrockService.sendGroupMessage(data.getSanderId(), data.getWsRequestBean().getParams().getGroup_id(), resultText);
            GlobalData.groupChatRecordEnableMap.remove(data.getWsRequestBean().getParams().getGroup_id() + "");
            GlobalData.groupChatRecordDataMap.remove(data.getWsRequestBean().getParams().getGroup_id() + "");
        }
        return null;
    }

}
