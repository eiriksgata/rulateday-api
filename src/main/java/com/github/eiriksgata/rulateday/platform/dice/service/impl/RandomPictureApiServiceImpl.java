package com.github.eiriksgata.rulateday.platform.dice.service.impl;

import com.github.eiriksgata.rulateday.platform.dice.config.GlobalData;
import com.github.eiriksgata.rulateday.platform.dice.dto.DiceMessageDTO;
import com.github.eiriksgata.rulateday.platform.dice.service.RandomPictureApiService;
import com.github.eiriksgata.rulateday.platform.utils.FileUtil;
import com.github.eiriksgata.rulateday.platform.websocket.api.ShamrockService;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.EventEnum;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.MessageContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RandomPictureApiServiceImpl implements RandomPictureApiService {


    @Autowired
    ShamrockService shamrockService;

    @Override
    public String urlEncodeAPI(DiceMessageDTO data, String url) {
        List<MessageContent> messageContentList = new ArrayList<>();
        String fileName = UUID.randomUUID() + ".jpg";

        String address = GlobalData.configData.getString("server-address");
        try {
            FileUtil.downLoadFromUrl(url, "resources/images/" + fileName);
            String serverUrl = address + "/resources/images/" + fileName;
            messageContentList.add(new MessageContent().setTypeByImages(serverUrl));

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
        } catch (Exception e) {
            return "获取图片失败，请重试或者切换线路";
        }

        return null;
    }


}
