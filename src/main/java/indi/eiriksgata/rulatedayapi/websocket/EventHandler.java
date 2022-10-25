package indi.eiriksgata.rulatedayapi.websocket;

import com.alibaba.fastjson.JSONObject;
import indi.eiriksgata.rulatedayapi.service.AiDrawingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventHandler {

    @Autowired
    AiDrawingService aiDrawingService;


    public void implement(String text) {
        JSONObject jsonObject = JSONObject.parseObject(text);
        String eventType = jsonObject.getString("eventType");

        switch (eventType) {
            case "ai_text_drawing_task":

                break;
        }


    }


    public void addAiDrawingTask() {

    }

}
