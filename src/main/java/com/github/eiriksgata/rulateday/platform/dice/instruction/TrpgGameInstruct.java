package com.github.eiriksgata.rulateday.platform.dice.instruction;

import com.github.eiriksgata.rulateday.platform.dice.dto.DiceMessageDTO;
import com.github.eiriksgata.rulateday.platform.dice.trpggame.GameData;
import com.github.eiriksgata.rulateday.platform.dice.trpggame.TrpgGameUtils;
import com.github.eiriksgata.rulateday.platform.dice.trpggame.utils.PlayerRoleAttributeSetUtil;
import com.github.eiriksgata.rulateday.platform.websocket.api.ShamrockService;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.EventEnum;
import com.github.eiriksgata.trpg.dice.injection.InstructReflex;
import com.github.eiriksgata.trpg.dice.injection.InstructService;
import com.github.eiriksgata.trpg.dice.reply.CustomText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@InstructService
@Component
public class TrpgGameInstruct {

    @Autowired
    ShamrockService shamrockService;

    @InstructReflex(value = {"trpg-list"}, priority = 3)
    public String trpgGameList(DiceMessageDTO data) {
        return TrpgGameUtils.getAllTrpgModelFiles();
    }

    @InstructReflex(value = {"trpg-role-get"}, priority = 3)
    public String trpgRoleDataGet(DiceMessageDTO data) {
        return PlayerRoleAttributeSetUtil.roleDataShow(data.getSanderId());
    }

    @InstructReflex(value = {"trpg-role-set"}, priority = 3)
    public String trpgRoleDataSet(DiceMessageDTO data) {
        String[] inputText = data.getBody().split(",");
        return CustomText.getText("trpg.role.card.set.title") + "\n1. " +
                PlayerRoleAttributeSetUtil.nameCheck(data.getSanderId(), inputText[0]) + "\n2. "
                + PlayerRoleAttributeSetUtil.skillCheck(data.getSanderId(), inputText[1]) + "\n3. "
                + PlayerRoleAttributeSetUtil.attributeCheck(data.getSanderId(), inputText[2]);
    }

    @InstructReflex(value = {"trpg-reload"}, priority = 3)
    public String trpgGameLoad(DiceMessageDTO data) {
        //TODO: 检测玩家数据是否满足要求
        if (GameData.playerRoleSaveDataMap.get(data.getSanderId()) == null
                || GameData.playerRoleSaveDataMap.get(data.getSanderId()).getName() == null
                || GameData.playerRoleSaveDataMap.get(data.getSanderId()).getAttribute() == null
                || GameData.playerRoleSaveDataMap.get(data.getSanderId()).getSkill() == null
                || GameData.playerRoleSaveDataMap.get(data.getSanderId()).getName().equals("")
                || GameData.playerRoleSaveDataMap.get(data.getSanderId()).getAttribute().equals("")
                || GameData.playerRoleSaveDataMap.get(data.getSanderId()).getSkill().equals("")) {
            return CustomText.getText("trpg.reload.not.found.role.attribute");
        }
        if (data.getMessageEvent().getSub_type().equals(EventEnum.MessageSubType.NORMAL.getName())) {
            shamrockService.sendGroupMessage(
                    data.getSanderId(),
                    data.getMessageEvent().getGroup_id(),
                    CustomText.getText("trpg.reload.start.help"),
                    data.getWsServerEndpoint()
            );
            shamrockService.sendGroupMessage(
                    data.getSanderId(),
                    data.getMessageEvent().getGroup_id(),
                    CustomText.getText("trpg.reload.start.waring"),
                    data.getWsServerEndpoint()
            );
            shamrockService.sendGroupMessage(
                    data.getSanderId(),
                    data.getMessageEvent().getGroup_id(),
                    TrpgGameUtils.loadScriptData(data.getSanderId(), data.getBody()),
                    data.getWsServerEndpoint()
            );
        }
        return TrpgGameUtils.loadEventText(data.getSanderId());
    }


    @InstructReflex(value = {"trpg-quit"}, priority = 3)
    public String quitGameModel(DiceMessageDTO data) {
        TrpgGameUtils.playerQuitGame(data.getSanderId());
        return CustomText.getText("trpg.game.quit");
    }


    @InstructReflex(value = {"trpg-option-"}, priority = 3)
    public String trpgOptionSelect(DiceMessageDTO data) {
        if (GameData.TrpgGamePlayerList.get(data.getSanderId()) == null
                || !GameData.TrpgGamePlayerList.get(data.getSanderId())) {
            return CustomText.getText("trpg.option.no.found.staring.model");
        } else {
            //TODO: 进行选项的动作
            if (data.getMessageEvent().getSub_type().equals(EventEnum.MessageSubType.NORMAL.getName())) {

                shamrockService.sendGroupMessage(
                        data.getSanderId(),
                        data.getMessageEvent().getGroup_id(),
                        TrpgGameUtils.optionSelect(data.getSanderId(), data.getBody()),
                        data.getWsServerEndpoint()
                );
            }
            return TrpgGameUtils.loadEventText(data.getSanderId());
        }
    }


}
