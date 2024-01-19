package com.github.eiriksgata.rulateday.dice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.eiriksgata.rulateday.dice.dto.DiceMessageDTO;
import com.github.eiriksgata.rulateday.dice.service.RandomPictureApiService;
import com.github.eiriksgata.rulateday.platform.websocket.api.ShamrockService;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.EventEnum;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.MessageContent;
import com.github.eiriksgata.trpg.dice.reply.CustomText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class RandomPictureApiServiceImpl implements RandomPictureApiService {


    @Autowired
    ShamrockService shamrockService;

    @Override
    public String urlEncodeAPI(DiceMessageDTO data, String url) {

        if (data.getWsRequestBean().getParams().getSub_type().equals(EventEnum.MessageSubType.FRIEND.getName())) {
            shamrockService.sendPrivateMessage(
                    data.getSanderId(),
                    new MessageContent().setTypeByImages(url)
            );
        } else {
            shamrockService.sendGroupMessage(
                    data.getSanderId(),
                    data.getWsRequestBean().getParams().getGroup_id(),
                    new MessageContent().setTypeByImages(url)
            );
        }
        return null;
    }


}
