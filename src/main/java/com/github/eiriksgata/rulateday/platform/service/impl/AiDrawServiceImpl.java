package com.github.eiriksgata.rulateday.platform.service.impl;

import cn.hutool.core.util.ReUtil;
import com.github.eiriksgata.rulateday.platform.exception.CommonBaseException;
import com.github.eiriksgata.rulateday.platform.service.AiDrawService;
import com.github.eiriksgata.rulateday.platform.vo.openapi.ClientAiDrawTaskResultVo;
import com.github.eiriksgata.rulateday.platform.websocket.vo.AiTextDrawGenVo;
import com.github.eiriksgata.rulateday.platform.websocket.vo.SendGroupMessageVo;
import com.github.eiriksgata.rulateday.platform.mapper.AiTextDrawMapper;
import com.github.eiriksgata.rulateday.platform.mapper.RobotTokenMapper;
import com.github.eiriksgata.rulateday.platform.pojo.AiTextDrawTask;
import com.github.eiriksgata.rulateday.platform.pojo.RobotToken;
import com.github.eiriksgata.rulateday.platform.websocket.WsServerEndpoint;
import com.github.eiriksgata.rulateday.platform.websocket.EventType;
import com.github.eiriksgata.rulateday.platform.websocket.vo.WsDataBean;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class AiDrawServiceImpl implements AiDrawService {

    @Autowired
    AiTextDrawMapper aiTextDrawMapper;

    @Autowired
    RobotTokenMapper robotTokenMapper;

    @Transactional(rollbackFor = CommonBaseException.class)
    @Override
    public void addTextDrawingTask(String userId, WsDataBean<AiTextDrawGenVo> wsDataBean) {
        //限制用户增加任务
        AiTextDrawTask result = aiTextDrawMapper.selectByCreatedById(wsDataBean.getData().getCreatedById());
        if (result != null) {
            WsServerEndpoint.channelList.get(userId).sendMessage(genGroupMessage(
                    wsDataBean.getEventId(),
                    wsDataBean.getData().getGroupId(), wsDataBean.getData().getCreatedById(),
                    "你已经有一个绘制任务，请等待完成后再创建。", ""
            ));
            return;
        }

        if (aiTextDrawMapper.selectCurrentTaskSize() > 20) {
            WsServerEndpoint.channelList.get(userId).sendMessage(genGroupMessage(
                    wsDataBean.getEventId(),
                    wsDataBean.getData().getGroupId(), wsDataBean.getData().getCreatedById(),
                    "当前绘制任务数量过多，请稍后再试。", ""
            ));
            return;
        }

        RobotToken robotToken = robotTokenMapper.selectByName(userId);
        if (robotToken == null) {
            return;
        }
        AiTextDrawGenVo aiTextDrawGenVo = wsDataBean.getData();

        AiTextDrawTask aiTextDrawTask = new AiTextDrawTask();
        aiTextDrawTask.setId(null);

        aiTextDrawTask.setCode(BCrypt.gensalt());
        aiTextDrawTask.setName(wsDataBean.getEventId());

        setPictureWidthHeight(aiTextDrawGenVo.getPictureShape(), aiTextDrawTask);

        aiTextDrawTask.setGroupId(aiTextDrawGenVo.getGroupId());
        aiTextDrawTask.setCreatedById(aiTextDrawGenVo.getCreatedById());

        aiTextDrawTask.setMachineCode(robotToken.getMachineCode());
        aiTextDrawTask.setCreatedAt(new Date());
        aiTextDrawTask.setUpdatedAt(new Date());

        aiTextDrawTask.setGroupId(aiTextDrawGenVo.getGroupId());
        aiTextDrawTask.setPrompt(aiTextDrawGenVo.getPrompt());
        aiTextDrawTask.setNegativePrompt(aiTextDrawGenVo.getNegativePrompt());
        aiTextDrawTask.setSamplingSteps(aiTextDrawGenVo.getSamplingSteps());

        aiTextDrawTask.setUserId(userId);

        aiTextDrawMapper.insert(aiTextDrawTask);
    }

    @Override
    @Transactional(rollbackFor = CommonBaseException.class)
    public void deviceReceiveTaskStateChange(Integer taskId, String clientId) {
        aiTextDrawMapper.updateTaskReceiveState(clientId, new Date(), taskId);
    }

    @Override
    public AiTextDrawTask getAiTextDrawTask() {
        return aiTextDrawMapper.getTask();
    }

    @Override
    @Transactional(rollbackFor = CommonBaseException.class)
    public void updateAiDrawResult(ClientAiDrawTaskResultVo clientAiDrawTaskResultVo) {
        AiTextDrawTask result = aiTextDrawMapper.selectByCodeAndClientId(
                clientAiDrawTaskResultVo.getCode(), clientAiDrawTaskResultVo.getClientId());
        if (result == null) {
            return;
        }
        result.setIsEnable(0);
        result.setIsComplete(1);
        result.setUpdatedAt(new Date());
        result.setLeaveWord(clientAiDrawTaskResultVo.getDescription());
        aiTextDrawMapper.updateById(result);
        int length = ReUtil.get("^data:image/.*;base64,", clientAiDrawTaskResultVo.getPictureBase64(), 0).length();
        String message = "\ntask id: " + result.getCode() + "\nprompt:" + result.getPrompt() + "\nnegative prompt:" + result.getNegativePrompt() + "\n耗时:" + clientAiDrawTaskResultVo.getGenerationTime();

        String pictureBase64 = clientAiDrawTaskResultVo.getPictureBase64().substring(length);

        WsServerEndpoint.channelList.get(result.getUserId()).sendMessage(
                genGroupMessage(
                        result.getName(), result.getGroupId(), result.getCreatedById(),
                        message, pictureBase64
                )
        );

    }


    private void setPictureWidthHeight(int pictureShape, AiTextDrawTask aiTextDrawTask) {
        switch (pictureShape) {
            case 1:
                aiTextDrawTask.setWidth(768);
                aiTextDrawTask.setHeight(512);
                break;
            case 2:
                aiTextDrawTask.setWidth(512);
                aiTextDrawTask.setHeight(512);
                break;
            default:
                aiTextDrawTask.setWidth(512);
                aiTextDrawTask.setHeight(768);
        }
    }


    public WsDataBean<SendGroupMessageVo> genGroupMessage(
            String eventId, Long groupId, Long senderId, String text, String pictureBase64) {
        WsDataBean<SendGroupMessageVo> wsDataBean = new WsDataBean<>();

        wsDataBean.setEventId(eventId);
        wsDataBean.setCurrentTimestamp(System.currentTimeMillis());
        wsDataBean.setEventType(EventType.SEND_GROUP_MESSAGE);

        SendGroupMessageVo sendGroupMessageVo = new SendGroupMessageVo();
        sendGroupMessageVo.setGroupId(groupId);
        sendGroupMessageVo.setSenderId(senderId);
        sendGroupMessageVo.setText(text);
        sendGroupMessageVo.setPictureBase64(pictureBase64);

        wsDataBean.setData(sendGroupMessageVo);
        return wsDataBean;
    }

}
