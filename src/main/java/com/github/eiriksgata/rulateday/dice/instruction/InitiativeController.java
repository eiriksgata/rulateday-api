package com.github.eiriksgata.rulateday.dice.instruction;

import com.github.eiriksgata.rulateday.dice.dto.DiceMessageDTO;
import com.github.eiriksgata.rulateday.dice.service.UserInitiativeService;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.EventEnum;
import com.github.eiriksgata.trpg.dice.injection.InstructReflex;
import com.github.eiriksgata.trpg.dice.injection.InstructService;
import com.github.eiriksgata.trpg.dice.operation.RollBasics;
import com.github.eiriksgata.trpg.dice.reply.CustomText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * author: create by Keith
 * version: v1.0
 * description: com.github.eiriksgata.rulateday.instruction
 * date: 2021/3/12
 **/

@InstructService
@Component
public class InitiativeController {

    @Autowired
    UserInitiativeService initiativeService;

    @Autowired
    RollBasics rollBasics;

    @InstructReflex(value = {"atklist", "atkList"}, priority = 2)
    public String getAtkList(DiceMessageDTO data) {
        String resultText;
        String groupId;
        if (data.getWsRequestBean().getParams().getSub_type().equals(EventEnum.MessageSubType.FRIEND.getName())) {
            groupId = "-" + data.getWsRequestBean().getParams().getUser_id();
        } else {
            groupId = "" + data.getWsRequestBean().getParams().getGroup_id();
        }
        return initiativeService.showInitiativeList(groupId);
    }


    @InstructReflex(value = {"atkdel", "atkDel", "Atkdel", "AtlDel", "atkdelete"}, priority = 2)
    public String delAtk(DiceMessageDTO data) {
        String tempName = null;
        String resultText = CustomText.getText("initiative.delete.oneself");
        if (data.getBody() != null && !data.getBody().equals("")) {
            tempName = data.getBody().trim();
            resultText = CustomText.getText("initiative.delete.other", tempName);
        }
        String finalTempName = tempName;

        String groupId;
        if (data.getWsRequestBean().getParams().getSub_type().equals(EventEnum.MessageSubType.FRIEND.getName())) {
            groupId = "-" + data.getWsRequestBean().getParams().getUser_id();
        } else {
            groupId = "" + data.getWsRequestBean().getParams().getGroup_id();
        }

        String name = data.getWsRequestBean().getParams().getSender().getNickname();

        if (finalTempName != null) {
            name = finalTempName;
        }
        initiativeService.deleteDice(groupId, data.getWsRequestBean().getParams().getUser_id(), name);

        return resultText;
    }


    @InstructReflex(value = {"atkClear", "clearAtk", "atkclear", "AtkClear"}, priority = 2)
    public String clearAtkList(DiceMessageDTO data) {
        String groupId;
        if (data.getWsRequestBean().getParams().getSub_type().equals(EventEnum.MessageSubType.FRIEND.getName())) {
            groupId = "-" + data.getWsRequestBean().getParams().getUser_id();
        } else {
            groupId = "" + data.getWsRequestBean().getParams().getGroup_id();
        }
        initiativeService.clearGroupDice(groupId);
        return CustomText.getText("initiative.clear");
    }

    @InstructReflex(value = {"atk", "atk"})
    public String generateInitiativeDice(DiceMessageDTO data) {
        final String[] name = {null};
        String[] tempList;
        String diceFace = "d";
        AtomicBoolean isLimit = new AtomicBoolean(false);

        String groupId;
        if (data.getWsRequestBean().getParams().getSub_type().equals(EventEnum.MessageSubType.FRIEND.getName())) {
            groupId = "-" + data.getWsRequestBean().getParams().getUser_id();
        } else {
            groupId = "" + data.getWsRequestBean().getParams().getGroup_id();
        }

        isLimit.set(initiativeService.diceLimit(groupId));
        name[0] = data.getWsRequestBean().getParams().getSender().getNickname();

        if (isLimit.get()) {
            return CustomText.getText("initiative.list.size.max");
        }

        final String[] resultText = {CustomText.getText("initiative.result.title", name[0])};
        if (data.getBody() != null && !data.getBody().equals("")) {
            tempList = data.getBody().split(" ");
            if (tempList.length > 1) {
                name[0] = tempList[1];
                resultText[0] = CustomText.getText("initiative.result.title", name[0]);
            }
            diceFace = tempList[0];
        }
        String finalName = name[0];
        rollBasics.rollRandom(diceFace, data.getSanderId(), (i, s) -> {
            //处理可能会出现小数等其他情况
            int numberValue;
            try {
                numberValue = Integer.parseInt(i);
                resultText[0] += s;
            } catch (NumberFormatException e) {
                resultText[0] = CustomText.getText("initiative.parameter.format.error");
                return;
            }
            String name1 = data.getWsRequestBean().getParams().getSender().getNickname();
            if (finalName != null) name1 = finalName;
            initiativeService.addInitiativeDice(
                    groupId, data.getSanderId(), name1, numberValue);
        });
        return resultText[0];
    }
}
