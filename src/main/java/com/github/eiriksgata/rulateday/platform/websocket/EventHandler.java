package com.github.eiriksgata.rulateday.platform.websocket;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.eiriksgata.rulateday.platform.service.AiDrawService;
import com.github.eiriksgata.rulateday.platform.websocket.vo.AiTextDrawGenVo;
import com.github.eiriksgata.rulateday.platform.websocket.vo.WsDataBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.github.eiriksgata.rulateday.platform.websocket.EventType.*;

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
