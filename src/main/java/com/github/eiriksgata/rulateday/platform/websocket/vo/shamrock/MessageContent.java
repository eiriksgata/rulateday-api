package com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class MessageContent {

    private String type;
    private JSONObject data = new JSONObject();

    public void setType(String type) {
        this.type = type;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public JSONObject getData() {
        return data;
    }

    public MessageContent setTypeByAt(Long qq) {
        this.type = "at";
        data.put("qq", qq);
        return this;
    }

    public MessageContent setTypeByText(String text) {
        this.type = "text";
        data.put("text", text);
        return this;
    }

    public MessageContent setTypeByImages(String url) {
        this.type = "image";
        data.put("url", url);
        return this;
    }

}
