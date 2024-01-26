package com.github.eiriksgata.rulateday.platform.dice.service.impl;

import com.github.eiriksgata.rulateday.platform.dice.dto.DiceMessageDTO;
import com.github.eiriksgata.rulateday.platform.dice.service.RandomPictureApiService;
import com.github.eiriksgata.rulateday.platform.websocket.api.ShamrockService;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.EventEnum;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.MessageContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RandomPictureApiServiceImpl implements RandomPictureApiService {


    @Autowired
    ShamrockService shamrockService;

    @Override
    public String urlEncodeAPI(DiceMessageDTO data, String url) {
        List<MessageContent> messageContentList = new ArrayList<>();
        messageContentList.add(new MessageContent().setTypeByImages(url));

        if (data.getMessageEvent().getSub_type().equals(EventEnum.MessageSubType.FRIEND.getName())) {

            shamrockService.sendPrivateMessage(
                    data.getSanderId(),
                    messageContentList,
                    data.getWsServerEndpoint()
            );
        } else {
            shamrockService.sendGroupMessage(
                    data.getSanderId(),
                    data.getMessageEvent().getGroup_id(),
                    messageContentList,
                    data.getWsServerEndpoint()
            );
        }
        return null;
    }


}
