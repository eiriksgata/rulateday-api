package com.github.eiriksgata.rulateday.platform.service;

import com.github.eiriksgata.rulateday.platform.exception.CommonBaseException;
import com.github.eiriksgata.rulateday.platform.vo.openapi.ClientAiDrawTaskResultVo;
import com.github.eiriksgata.rulateday.platform.websocket.vo.AiTextDrawGenVo;
import com.github.eiriksgata.rulateday.platform.pojo.AiTextDrawTask;
import com.github.eiriksgata.rulateday.platform.websocket.vo.WsDataBean;
import org.springframework.transaction.annotation.Transactional;

public interface AiDrawService {
    @Transactional(rollbackFor = CommonBaseException.class)
    void addTextDrawingTask(String userId, WsDataBean<AiTextDrawGenVo> aiTextDrawGenVo);

    @Transactional(rollbackFor = CommonBaseException.class)
    void deviceReceiveTaskStateChange(Integer taskId, String clientId);

    AiTextDrawTask getAiTextDrawTask();

    @Transactional(rollbackFor = CommonBaseException.class)
    void updateAiDrawResult(ClientAiDrawTaskResultVo clientAiDrawTaskResultVo);
}
