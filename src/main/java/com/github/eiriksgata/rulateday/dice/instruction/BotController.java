package com.github.eiriksgata.rulateday.dice.instruction;

import com.github.eiriksgata.rulateday.dice.config.GlobalData;
import com.github.eiriksgata.rulateday.dice.dto.DiceMessageDTO;
import com.github.eiriksgata.rulateday.dice.service.BotControlService;
import com.github.eiriksgata.trpg.dice.injection.InstructReflex;
import com.github.eiriksgata.trpg.dice.injection.InstructService;
import com.github.eiriksgata.trpg.dice.reply.CustomText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@InstructService
@Component
public class BotController {

    @Autowired
    BotControlService botControlService;


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
            //TODO: 获取群列表
//            for (Group group : Bot.getInstances().get(0).getGroups()) {
//                stringBuilder.append("[").append(group.getName()).append("]").append(" ").append(group.getId()).append("\n");
//            }
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

            //TODO: 获取好友列表
//            for (Friend friend : Bot.getInstances().get(0).getFriends()) {
//                stringBuilder.append("[").append(friend.getNick()).append("]").append(" ").append(friend.getId()).append("\n");
//            }
            return stringBuilder.toString();
        } else {
            return CustomText.getText("blacklist.no.permission");
        }
    }

    @InstructReflex(value = {"friend-list-del"}, priority = 3)
    public String deleteFriend(DiceMessageDTO data) {
        String number = GlobalData.configData.getString("master.QQ.number");
        if (number.equals("")) {
            return CustomText.getText("dice.master.number.no.set");
        }
        if (data.getSanderId() == Long.parseLong(number)) {
            try {
                //TODO: 删除好友列表

//                long deleteFriendId = Long.parseLong(data.getMessage());
//                Friend friend = Bot.getInstances().get(0).getFriend(deleteFriendId);
//                if (friend == null) {
//                    return CustomText.getText("friend.delete.no.found");
//                }
//                friend.delete();
                return CustomText.getText("friend.delete.success");
            } catch (Exception e) {
                e.printStackTrace();
                return CustomText.getText("friend.delete.fail");
            }
        } else {
            return CustomText.getText("blacklist.no.permission");
        }
    }


    @InstructReflex(value = {"dismiss"}, priority = 3)
    public String dismissCurrentGroup(DiceMessageDTO data) {
        String number = GlobalData.configData.getString("master.QQ.number");
        //TODO: 退出当前群聊
//        EventUtils.eventCallback(data.getEvent(), new EventAdapter() {
//            @Override
//            public void group(GroupMessageEvent event) {
//                event.getGroup().sendMessage(CustomText.getText("bot.group.dismiss"));
//
//                if (event.getSender().getPermission().getLevel() == MemberPermission.ADMINISTRATOR.getLevel() ||
//                        event.getSender().getPermission().getLevel() == MemberPermission.OWNER.getLevel() ||
//                        data.getId() == Long.parseLong(number)
//                ) {
//                    event.getGroup().quit();
//                } else {
//                    event.getGroup().sendMessage(CustomText.getText("bot.group.dismiss.no.permission"));
//                }
//            }
//        });
        return null;
    }

    @InstructReflex(value = {"quitGroup", ".quitgroup"}, priority = 4)
    public String quitGroupByMaster(DiceMessageDTO data) {
        String number = GlobalData.configData.getString("master.QQ.number");
        if (!number.equals("" + data.getSanderId())) {
            return CustomText.getText("bot.group.quit.no.permission");
        }
        int groupId;
        if (data.getBody().matches("^\\d{1,20}$")) {
            groupId = Integer.parseInt(data.getBody());
        } else {
            return CustomText.getText("bot.group.quit.id.error");
        }
        //TODO: 退出指定群聊
//        EventUtils.eventCallback(data.getEvent(), new EventAdapter() {
//            @Override
//            public void group(GroupMessageEvent event) {
//                Group group = event.getBot().getGroup(groupId);
//                if (group == null) {
//                    event.getGroup().sendMessage(CustomText.getText("bot.group.quit.not.found", groupId));
//                } else {
//                    if (group.quit()) {
//                        event.getGroup().sendMessage(CustomText.getText("bot.group.quit.success", groupId));
//                    } else {
//                        event.getGroup().sendMessage(CustomText.getText("bot.group.quit.fail", groupId));
//                    }
//                }
//            }
//
//            @Override
//            public void friend(FriendMessageEvent event) {
//                Group group = event.getBot().getGroup(groupId);
//                if (group == null) {
//                    event.getSender().sendMessage(CustomText.getText("bot.group.quit.not.found", groupId));
//                } else {
//                    if (group.quit()) {
//                        event.getSender().sendMessage(CustomText.getText("bot.group.quit.success", groupId));
//                    } else {
//                        event.getSender().sendMessage(CustomText.getText("bot.group.quit.fail", groupId));
//                    }
//                }
//            }
//        });

        return null;
    }
}
