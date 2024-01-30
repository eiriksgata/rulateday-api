package com.github.eiriksgata.rulateday.platform.dice;

import com.github.eiriksgata.rulateday.platform.dice.config.GlobalData;
import com.github.eiriksgata.rulateday.platform.dice.dto.DiceMessageDTO;
import com.github.eiriksgata.rulateday.platform.dice.service.ApiReportService;
import com.github.eiriksgata.rulateday.platform.dice.service.BotControlService;
import com.github.eiriksgata.rulateday.platform.dice.service.ChatRecordService;
import com.github.eiriksgata.rulateday.platform.dice.service.UserConversationService;
import com.github.eiriksgata.rulateday.platform.mapper.DiceConfigMapper;
import com.github.eiriksgata.rulateday.platform.utils.ExceptionUtils;
import com.github.eiriksgata.rulateday.platform.websocket.api.ShamrockService;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.EventEnum;
import com.github.eiriksgata.trpg.dice.exception.DiceInstructException;
import com.github.eiriksgata.trpg.dice.exception.ExceptionEnum;
import com.github.eiriksgata.trpg.dice.message.handle.InstructHandle;
import org.apache.ibatis.exceptions.PersistenceException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class DiceMessageEventHandlerImpl implements DiceMessageEventHandler {

    @Autowired
    InstructHandle instructHandle;

    @Autowired
    BotControlService botControl;

    @Autowired
    ApiReportService apiReport;

    @Autowired
    ChatRecordService chatRecordService;

    @Autowired
    ShamrockService shamrockService;

    @Autowired
    DiceConfigMapper diceConfigMapper;

    @Autowired
    UserConversationService userConversationService;

    @Override
    public void onBotGroupRequest(DiceMessageDTO diceMessageDTO) {
        //检测是否是黑名单用户
        if (botControl.isBlacklist(diceMessageDTO.getMessageEvent().getGroup_id())) {
            return;
        }

        //收到邀请自动加入
        if (GlobalData.configData.getBooleanValue("auto.accept.group.request")) {
            //TODO: 同意请求 不需要实现，用户可以通过模拟器自主实现
            //  event.accept();
        }

    }

    @Override
    public void onFriendRequest(DiceMessageDTO diceMessageDTO) {
        if (GlobalData.configData.getBooleanValue("auto.accept.friend.request")
                && !botControl.isBlacklist(-diceMessageDTO.getSanderId())) {
            //TODO: 不需要实现，用户可以通过模拟器自主实现
            //  event.accept();
        }
    }

    @Override
    public void OnDiceMessage(DiceMessageDTO data) {

        if (Objects.equals(data.getMessageEvent().getSub_type(), EventEnum.MessageSubType.FRIEND.getName())) {
            onFriendMessage(data);
        }

        if (Objects.equals(data.getMessageEvent().getSub_type(), EventEnum.MessageSubType.NORMAL.getName())) {
            onGroupMessage(data);
        }
    }


    @Override
    public void onGroupMessage(DiceMessageDTO event) {
        groupMessageHandle(event);
    }

    public void onFriendMessage(DiceMessageDTO data) {
        if (diceConfigMapper.selectById().getPrivate_chat() == 0) {
            return;
        }

        //判断群是否是黑名单，具体功能尚未实现
        if (botControl.isBlacklist(-data.getSanderId())) {
            return;
        }

        String conversationResult = userConversationService.checkInputQuery(data);
        if (conversationResult != null) {
            shamrockService.sendPrivateMessage(
                    data.getSanderId(),
                    conversationResult,
                    data.getWsServerEndpoint()
            );

            return;
        }
        List<String> result = personalMessageEventHandler(data);
        if (result != null) {
            result.forEach((text) ->
                    shamrockService.sendPrivateMessage(
                            data.getSanderId(), text,
                            data.getWsServerEndpoint()));
        }

    }

    //处理在处理事件中发生的未捕获异常
    @Override
    public void handleException(@NotNull Throwable exception) {
        throw new RuntimeException("在事件处理中发生异常", exception);
    }


    private void groupMessageHandle(DiceMessageDTO data) {
        //判断群是否是黑名单，具体功能尚未实现
        if (botControl.isBlacklist(data.getMessageEvent().getGroup_id())) {
            return;
        }

        //群消息的回复
        //回复群的筛选
        if (botControl.groupBotOff(data) || botControl.groupBotOn(data)) {
            return;
        }

        //群记录处理
        chatRecordService.groupRecordHandler(data);

        if (!botControl.isSpeakers(data)) {
            return;
        }

        //检测对话模式，具有最高优先级
        String conversationResult = userConversationService.checkInputQuery(data);
        if (conversationResult != null) {
            shamrockService.sendGroupMessage(
                    data.getSanderId(),
                    data.getMessageEvent().getGroup_id(),
                    conversationResult,
                    data.getWsServerEndpoint()
            );
            return;
        }

        String prefix = botControl.isPrefixMatch(data.getBody());
        if (prefix == null) {
            return;
        }
        data.setBody(data.getBody().substring(prefix.length()));
//        data.setBody(data.getBody().toUpperCase());

        String result;
        try {
            result = (String) instructHandle.instructCheck(data);
        } catch (DiceInstructException e) {
            if (e.getErrCode().equals(ExceptionEnum.DICE_INSTRUCT_NOT_FOUND.getErrCode())) {
                return;
            }
            result = e.getErrMsg();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            result = e.getMessage();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            result = e.getCause().toString();
        } catch (PersistenceException e) {
            e.printStackTrace();
            result = "数据库操作异常，可以尝试重启程序，如果还存在这种情况，可以联系开发人员进行反馈";
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        if (result == null) return;
        String senderName = data.getMessageEvent().getSender().getCard();
        if (senderName.trim().equals("")) {
            senderName = data.getMessageEvent().getSender().getNickname();
        }
        if (GlobalData.configData.getBooleanValue("reply.at.user")) {
            shamrockService.sendGroupMessage(
                    data.getSanderId(),
                    data.getMessageEvent().getGroup_id(),
                    result,
                    data.getWsServerEndpoint()
            );
        } else {
            shamrockService.sendGroupMessage(
                    data.getSanderId(),
                    data.getMessageEvent().getGroup_id(),
                    "[" + senderName + "]" + result,
                    data.getWsServerEndpoint()
            );
        }
    }


    @SuppressWarnings("rawtypes")
    public List<String> personalMessageEventHandler(DiceMessageDTO data) {
        String sourceMessage = data.getBody();
        List<String> result = new ArrayList<>();

        String prefix = botControl.isPrefixMatch(data.getBody());
        if (prefix == null) {
            return null;
        }

        data.setBody(data.getBody().substring(prefix.length()));
        data.setBody(data.getBody().toUpperCase());

        try {
            String returnText = (String) instructHandle.instructCheck(data);
            if (returnText == null) return null;
            //对于私聊的消息需要进行分割长度发送
            while (true) {
                if (returnText.length() > 200) {
                    result.add(returnText.substring(0, 200));
                    returnText = returnText.substring(200);
                } else {
                    result.add(returnText);
                    break;
                }
            }
        } catch (DiceInstructException e) {
            if (e.getErrCode().equals(ExceptionEnum.DICE_INSTRUCT_NOT_FOUND.getErrCode())) {
                return null;
            }
            result.add(e.getErrMsg());
            result.add("\n参数异常，请输入正确的参数范围，或联系QQ:2353686862");
            apiReport.exceptionReport(sourceMessage, ExceptionUtils.getExceptionAllInfo(e), data.getSanderId());
        } catch (IllegalAccessException | InstantiationException e) {
            result.add(e.getMessage());
            result.add("\n实例化异常或非法访问，可联系QQ:2353686862");
            apiReport.exceptionReport(sourceMessage, ExceptionUtils.getExceptionAllInfo(e), data.getSanderId());
        } catch (InvocationTargetException e) {
            result.add(e.getCause().toString());
            result.add("\n反射调用异常，可联系QQ:2353686862");
            apiReport.exceptionReport(sourceMessage, ExceptionUtils.getExceptionAllInfo(e), data.getSanderId());
        } catch (Exception e) {
            e.printStackTrace();
            apiReport.exceptionReport(sourceMessage, ExceptionUtils.getExceptionAllInfo(e), data.getSanderId());
            return null;
        }
        return result;
    }

}
