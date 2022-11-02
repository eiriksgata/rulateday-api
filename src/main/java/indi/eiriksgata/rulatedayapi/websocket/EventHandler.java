package indi.eiriksgata.rulatedayapi.websocket;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import indi.eiriksgata.rulatedayapi.service.AiDrawService;
import indi.eiriksgata.rulatedayapi.websocket.vo.AiTextDrawGenVo;
import indi.eiriksgata.rulatedayapi.websocket.vo.WsDataBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static indi.eiriksgata.rulatedayapi.websocket.EventType.*;

@Component
public class EventHandler {

    @Autowired
    AiDrawService aiDrawService;

    public void implement(String userId, String text) {
        JSONObject jsonObject = JSONObject.parseObject(text);
        String eventType = jsonObject.getString("eventType");
        switch (eventType) {
            case AI_TEXT_DRAW_TASK_CREATED:
                addAiTextDrawingTask(userId, text);
                break;
            default:
                break;
        }
    }

    private void addAiTextDrawingTask(String userId, String text) {
        WsDataBean<AiTextDrawGenVo> data = JSONObject.parseObject(text, new TypeReference<
                WsDataBean<AiTextDrawGenVo>>() {
        }.getType());

        aiDrawService.addTextDrawingTask(userId, data);
    }

}
