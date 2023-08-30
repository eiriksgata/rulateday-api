package indi.eiriksgata.rulateday.api.service;

import indi.eiriksgata.rulateday.api.exception.CommonBaseException;
import indi.eiriksgata.rulateday.api.vo.openapi.ClientAiDrawTaskResultVo;
import indi.eiriksgata.rulateday.api.websocket.vo.AiTextDrawGenVo;
import indi.eiriksgata.rulateday.api.pojo.AiTextDrawTask;
import indi.eiriksgata.rulateday.api.websocket.vo.WsDataBean;
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
