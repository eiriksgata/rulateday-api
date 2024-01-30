package com.github.eiriksgata.rulateday.platform.dice.instruction;

import com.github.eiriksgata.rulateday.platform.dice.config.GlobalData;
import com.github.eiriksgata.rulateday.platform.dice.dto.DiceMessageDTO;
import com.github.eiriksgata.rulateday.platform.dice.service.BotControlService;
import com.github.eiriksgata.rulateday.platform.websocket.api.ShamrockService;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.EventEnum;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.api.FriedInfoVo;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.api.GroupInfoVo;
import com.github.eiriksgata.trpg.dice.injection.InstructReflex;
import com.github.eiriksgata.trpg.dice.injection.InstructService;
import com.github.eiriksgata.trpg.dice.reply.CustomText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@InstructService
@Component("BotInstruct")
public class BotInstruct {

    @Autowired
    BotControlService botControlService;

    @Autowired
    ShamrockService shamrockService;


    @InstructReflex(value = {"blacklist-group-add"}, priority = 3)
    public String addBlacklistByGroup(DiceMessageDTO data) {
        //验证骰主
        String number = GlobalData.configData.getString("master.QQ.number");
        if (number.equals("")) {
            return CustomText.getText("dice.master.number.no.set");
        }
        if (data.getSanderId() == Long.parseLong(number)) {
            long groupId;
            try {
                groupId = Long.parseLong(data.getBody().trim());
                botControlService.groupBlacklistEnable(groupId);
            } catch (Exception e) {
                return CustomText.getText("blacklist.group.id.error");
            }
            return CustomText.getText("blacklist.group.add.success", groupId);
        } else {
            return CustomText.getText("blacklist.no.permission");
        }
    }


    @InstructReflex(value = {"blacklist-group-del"}, priority = 3)
    public String deleteBlacklistByGroup(DiceMessageDTO data) {
        //验证骰主
        String number = GlobalData.configData.getString("master.QQ.number");
        if (number.equals("")) {
            return CustomText.getText("dice.master.number.no.set");
        }
        if (data.getSanderId() == Long.parseLong(number)) {
            long groupId;
            try {
                groupId = Long.parseLong(data.getBody().trim());
                botControlService.groupBlacklistDisable(groupId);
            } catch (Exception e) {
                return CustomText.getText("blacklist.group.id.error");
            }
            return CustomText.getText("blacklist.group.delete.success");
        } else {
            return CustomText.getText("blacklist.no.permission");
        }
    }

    @InstructReflex(value = {"blacklist-friend-add"}, priority = 3)
    public String addBlacklistByFriend(DiceMessageDTO data) {
        //验证骰主
        String number = GlobalData.configData.getString("master.QQ.number");
        if (number.equals("")) {
            return CustomText.getText("dice.master.number.no.set");
        }
        if (data.getSanderId() == Long.parseLong(number)) {
            if (number.equals(data.getBody().trim())) {
                return CustomText.getText("blacklist.friend.cannot.master");
            }
            long friendId;
            try {
                friendId = Long.parseLong(data.getBody().trim());
                botControlService.groupBlacklistEnable(-friendId);
            } catch (Exception e) {
                return CustomText.getText("blacklist.friend.id.error");
            }
            return CustomText.getText("blacklist.friend.add.success", friendId);
        } else {
            return CustomText.getText("blacklist.no.permission");
        }
    }

    @InstructReflex(value = {"blacklist-friend-del"}, priority = 3)
    public String deleteBlacklistByFriend(DiceMessageDTO data) {
        //验证骰主
        String number = GlobalData.configData.getString("master.QQ.number");
        if (number.equals("")) {
            return CustomText.getText("dice.master.number.no.set");
        }
        if (data.getSanderId() == Long.parseLong(number)) {
            if (number.equals(data.getBody().trim())) {
                return null;
            }
            long friendId;
            try {
                friendId = Long.parseLong(data.getBody().trim());
                botControlService.groupBlacklistDisable(-friendId);
            } catch (Exception e) {
                return CustomText.getText("blacklist.friend.id.error");
            }
            return CustomText.getText("blacklist.friend.add.success", friendId);
        } else {
            return CustomText.getText("blacklist.no.permission");
        }
    }


    @InstructReflex(value = {"group-list-get"}, priority = 3)
    public String getGroupList(DiceMessageDTO data) {
        String number = GlobalData.configData.getString("master.QQ.number");
        if (number.equals("")) {
            return CustomText.getText("dice.master.number.no.set");
        }
        if (data.getSanderId() == Long.parseLong(number)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(CustomText.getText("bot.group.list.title")).append("\n");
            for (GroupInfoVo group : shamrockService.getGroupList(data.getWsServerEndpoint())) {
                stringBuilder.append("[").append(group.getGroupName()).append("]").append(" ").append(group.getGroup_id()).append("\n");
            }
            return stringBuilder.toString();
        } else {
            return CustomText.getText("blacklist.no.permission");
        }
    }

    @InstructReflex(value = {"friend-list-get"}, priority = 3)
    public String getFriendList(DiceMessageDTO data) {
        String number = GlobalData.configData.getString("master.QQ.number");
        if (number.equals("")) {
            return CustomText.getText("dice.master.number.no.set");
        }
        if (data.getSanderId() == Long.parseLong(number)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(CustomText.getText("bot.friend.list.title")).append("\n");
            for (FriedInfoVo friend : shamrockService.getFriendList(data.getWsServerEndpoint())) {
                stringBuilder.append("[").append(
                        friend.getUser_name()).append("]").append(" ").append(friend.getUser_id()).append("\n");
            }
            return stringBuilder.toString();
        } else {
            return CustomText.getText("blacklist.no.permission");
        }
    }

    @InstructReflex(value = {"friend-list-del"}, priority = 3)
    public String deleteFriend(DiceMessageDTO data) {
        return "功能未实现";
//        String number = GlobalData.configData.getString("master.QQ.number");
//        if (number.equals("")) {
//            return CustomText.getText("dice.master.number.no.set");
//        }
//        if (data.getSanderId() == Long.parseLong(number)) {
//            try {
//                //TODO: 删除好友列表
////                long deleteFriendId = Long.parseLong(data.getMessage());
////                Friend friend = Bot.getInstances().get(0).getFriend(deleteFriendId);
////                if (friend == null) {
////                    return CustomText.getText("friend.delete.no.found");
////                }
////                friend.delete();
//                return CustomText.getText("friend.delete.success");
//            } catch (Exception e) {
//                e.printStackTrace();
//                return CustomText.getText("friend.delete.fail");
//            }
//        } else {
//            return CustomText.getText("blacklist.no.permission");
//        }
    }


    @InstructReflex(value = {"dismiss"}, priority = 3)
    public String dismissCurrentGroup(DiceMessageDTO data) {
        // String number = GlobalData.configData.getString("master.QQ.number");
        if (data.getMessageEvent().getSub_type().equals(EventEnum.MessageSubType.NORMAL.getName())) {
            shamrockService.sendGroupMessage(
                    data.getSanderId(),
                    data.getMessageEvent().getGroup_id(),
                    CustomText.getText("bot.group.dismiss"),
                    data.getWsServerEndpoint()
            );
            shamrockService.quitGroup(data.getMessageEvent().getGroup_id(), data.getWsServerEndpoint());
        }
        return null;
    }

    @InstructReflex(value = {"quitGroup", ".quitgroup"}, priority = 4)
    public String quitGroupByMaster(DiceMessageDTO data) {
        String number = GlobalData.configData.getString("master.QQ.number");
        if (!number.equals("" + data.getSanderId())) {
            return CustomText.getText("bot.group.quit.no.permission");
        }
        Long groupId;
        if (data.getBody().matches("^\\d{1,20}$")) {
            groupId = Long.parseLong(data.getBody());
        } else {
            return CustomText.getText("bot.group.quit.id.error");
        }
        if (data.getMessageEvent().getSub_type().equals(EventEnum.MessageSubType.FRIEND.getName())) {
            shamrockService.sendPrivateMessage(
                    data.getSanderId(),
                    CustomText.getText("bot.group.quit.success", groupId),
                    data.getWsServerEndpoint()

            );
            shamrockService.quitGroup(groupId, data.getWsServerEndpoint());

        } else {
            shamrockService.sendGroupMessage(
                    data.getSanderId(),
                    data.getMessageEvent().getGroup_id(),
                    CustomText.getText("bot.group.quit.success", groupId),
                    data.getWsServerEndpoint()
            );
            shamrockService.quitGroup(groupId, data.getWsServerEndpoint());
        }
        return null;
    }
}
