package indi.eiriksgata.rulatedayapi.service;

import indi.eiriksgata.rulateday.pojo.AiTextDrawTask;
import indi.eiriksgata.rulatedayapi.exception.CommonBaseException;
import indi.eiriksgata.rulatedayapi.vo.openapi.ClientAiDrawTaskResultVo;
import indi.eiriksgata.rulatedayapi.websocket.vo.AiTextDrawGenVo;
import indi.eiriksgata.rulatedayapi.websocket.vo.WsDataBean;
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
