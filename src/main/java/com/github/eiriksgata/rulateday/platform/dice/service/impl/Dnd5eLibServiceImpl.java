package com.github.eiriksgata.rulateday.platform.dice.service.impl;

import com.github.eiriksgata.rulateday.platform.dice.dto.DiceMessageDTO;
import com.github.eiriksgata.rulateday.platform.dice.service.Dnd5eLibService;
import com.github.eiriksgata.rulateday.platform.mapper.Dnd5ePhbDataMapper;
import com.github.eiriksgata.rulateday.platform.pojo.QueryDataBase;
import com.github.eiriksgata.rulateday.platform.websocket.api.ShamrockService;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.EventEnum;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.MessageContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * author: create by Keith
 * version: v1.0
 * description: com.github.eiriksgata.rulateday.service.impl
 * date: 2020/11/13
 **/
@Slf4j
@Service
public class Dnd5eLibServiceImpl implements Dnd5eLibService {

    private final String imagesUrl = ResourceBundle.getBundle("resources").getString("resources.mm.images.url");
    private final String localPath = ResourceBundle.getBundle("resources").getString("resources.mm.images.path");

    @Autowired
    ShamrockService shamrockService;

    @Autowired
    Dnd5ePhbDataMapper dnd5ePhbDataMapper;

    @Override
    public List<QueryDataBase> findName(String name) {
        List<QueryDataBase> result = new ArrayList<>();
        result.addAll(dnd5ePhbDataMapper.selectSkillPhb(name));
        result.addAll(dnd5ePhbDataMapper.selectArmorWeapon(name));
        result.addAll(dnd5ePhbDataMapper.selectClasses(name));
        result.addAll(dnd5ePhbDataMapper.selectFeat(name));
        result.addAll(dnd5ePhbDataMapper.selectRaces(name));
        result.addAll(dnd5ePhbDataMapper.selectRule(name));
        result.addAll(dnd5ePhbDataMapper.selectTools(name));
        result.addAll(dnd5ePhbDataMapper.selectSpellList(name));
        result.addAll(dnd5ePhbDataMapper.selectMagicItemsDmg(name));
        result.addAll(dnd5ePhbDataMapper.selectRuleDmg(name));
        result.addAll(dnd5ePhbDataMapper.selectMM(name));
        result.addAll(dnd5ePhbDataMapper.selectBackgroundPhb(name));

        result.addAll(dnd5ePhbDataMapper.selectEgtw(name));
        result.addAll(dnd5ePhbDataMapper.selectBaseModule(name));

        return result;
    }

    @Override
    public QueryDataBase findById(long id) {
        return dnd5ePhbDataMapper.selectSkillPhbById(id);
    }


    @Override
    public QueryDataBase getRandomMMData() {
        return dnd5ePhbDataMapper.selectRandomMM();
    }


    @Override
    public void sendMMImage(DiceMessageDTO data, QueryDataBase result) {
        String mmNameFileName = result.getName().substring(5) + ".png";
        String mmName = result.getName().substring(5) + ".png";
        mmNameFileName = URLEncoder.encode(mmNameFileName, StandardCharsets.UTF_8);
        String url = imagesUrl + mmNameFileName;
        List<MessageContent> messageContentList = new ArrayList<>();
        messageContentList.add(new MessageContent().setTypeByImages(url));
        if (data.getMessageEvent().getSub_type().equals(EventEnum.MessageSubType.NORMAL.getName())) {
            shamrockService.sendGroupMessage(
                    data.getSanderId(),
                    data.getMessageEvent().getGroup_id(),
                    messageContentList,
                    data.getWsServerEndpoint()
            );
        } else {
            shamrockService.sendPrivateMessage(
                    data.getSanderId(),
                    messageContentList,
                    data.getWsServerEndpoint()
            );

        }
    }


}
