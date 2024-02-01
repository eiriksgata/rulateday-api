package com.github.eiriksgata.rulateday.platform.dice.instruction;

import com.github.eiriksgata.rulateday.platform.dice.dto.DiceMessageDTO;
import com.github.eiriksgata.rulateday.platform.mapper.CardsGroupDataMapper;
import com.github.eiriksgata.rulateday.platform.mapper.CardsTypeListMapper;
import com.github.eiriksgata.rulateday.platform.pojo.CardsGroupData;
import com.github.eiriksgata.rulateday.platform.pojo.CardsTypeList;
import com.github.eiriksgata.rulateday.platform.websocket.api.ShamrockService;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.EventEnum;
import com.github.eiriksgata.trpg.dice.injection.InstructReflex;
import com.github.eiriksgata.trpg.dice.injection.InstructService;
import com.github.eiriksgata.trpg.dice.reply.CustomText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * author: create by Keith
 * version: v1.0
 * description: com.github.eiriksgata.rulateday.instruction
 * date: 2021/4/19
 **/
@InstructService
@Component("CardsInstruct")
public class CardsInstruct {

    @Autowired
    CardsGroupDataMapper cardsGroupDataMapper;

    @Autowired
    CardsTypeListMapper cardsTypeListMapper;

    @Autowired
    ShamrockService shamrockService;

    @InstructReflex(value = {"cards"})
    public String cardsList(DiceMessageDTO data) {
        List<CardsTypeList> lists = cardsTypeListMapper.selectAll();
        if (lists.size() == 0) {
            return CustomText.getText("cards.type.not.found");
        }
        StringBuilder result = new StringBuilder();
        result.append(CustomText.getText("cards.type.list.title"));
        AtomicInteger count = new AtomicInteger();
        lists.forEach((cardsTypeList) -> {
            count.getAndIncrement();
            result.append("\n").append(count.get()).append(". ").append(cardsTypeList.getName());
        });
        return result.toString();
    }

    @InstructReflex(value = {"cardsAdd", "cardsadd"}, priority = 3)
    public String cardsAdd(DiceMessageDTO data) {
        String[] parsingData = data.getBody().split(" ");
        if (parsingData.length < 2) {
            return CustomText.getText("cards.add.parameter.format.error");
        }
        CardsTypeList cardsTypeList = new CardsTypeList();
        cardsTypeList.setName(parsingData[0]);
        cardsTypeList.setContent(data.getBody().substring(parsingData[0].length() + 1));
        try {
            cardsTypeListMapper.insert(cardsTypeList);
        } catch (Exception e) {
            return CustomText.getText("cards.add.error");
        }
        return CustomText.getText("cards.add.success");
    }

    @InstructReflex(value = {"cardsDel", "cardsdel"}, priority = 3)
    public String cardsDel(DiceMessageDTO data) {
        cardsTypeListMapper.deleteByName(data.getBody());
        return CustomText.getText("cards.delete.success");
    }


    @InstructReflex(value = {"drawAdd", "drawadd"}, priority = 3)
    public String drawAdd(DiceMessageDTO data) {
        if (data.getBody().equals("") || data.getBody() == null) {
            return CustomText.getText("cards.draw.not.parameter");
        }
        CardsTypeList cardsTypeList = cardsTypeListMapper.selectByName(data.getBody());
        if (cardsTypeList == null) {
            return CustomText.getText("cards.draw.not.found");
        }
        String[] addDataArr = cardsTypeList.getContent().split(",");
        Long groupId;
        if (data.getMessageEvent().getSub_type().equals(EventEnum.MessageSubType.NORMAL.getName())) {
            groupId = data.getMessageEvent().getGroup_id();
        } else {
            groupId = -data.getMessageEvent().getUser_id();
        }

        for (String anAddDataArr : addDataArr) {
            CardsGroupData cardsGroupData = new CardsGroupData();
            cardsGroupData.setTypeId(cardsTypeList.getId());
            cardsGroupData.setGroupId(groupId);
            cardsGroupData.setValue(anAddDataArr);
            cardsGroupDataMapper.insert(cardsGroupData);
        }
        return CustomText.getText("cards.draw.add.success", cardsTypeList.getName());
    }

    @InstructReflex(value = {"drawList", "drawlist"}, priority = 3)
    public String drawList(DiceMessageDTO data) {
        boolean isGroupMessage = Objects.equals(
                data.getMessageEvent().getSub_type(),
                EventEnum.MessageSubType.NORMAL.getName());
        Long groupId = isGroupMessage ? data.getMessageEvent().getGroup_id() :
                -data.getMessageEvent().getUser_id();

        List<CardsGroupData> list = cardsGroupDataMapper.getGroupCardsList(
                groupId
        );
        if (list.size() <= 0) {
            return CustomText.getText("cards.draw.not.data");
        }
        StringBuilder result = new StringBuilder();

        result.append(CustomText.getText("cards.draw.list")).append("[");
        list.forEach(cardsGroupData -> result.append(cardsGroupData.getValue()).append(","));
        result.delete(result.length() - 1, result.length());
        result.append("]");
        return result.toString();
    }


    @InstructReflex(value = {"drawHide", "drawhide"}, priority = 3)
    public String drawHideOut(DiceMessageDTO data) {
        Long groupId;
        if (data.getMessageEvent().getSub_type().equals(EventEnum.MessageSubType.NORMAL.getName())) {
            groupId = data.getMessageEvent().getGroup_id();
            CardsGroupData result = cardsGroupDataMapper.randomGetCard(groupId);
            shamrockService.sendGroupMessage(
                    data.getSanderId(),
                    data.getMessageEvent().getGroup_id(),
                    CustomText.getText(
                            "cards.draw.hide.group.result",
                            groupId, result.getValue()),
                    data.getWsServerEndpoint()
            );
        } else {
            groupId = -data.getSanderId();
            CardsGroupData result = cardsGroupDataMapper.randomGetCard(groupId);
            shamrockService.sendPrivateMessage(
                    data.getSanderId(),
                    CustomText.getText(
                            "cards.draw.hide.group.result",
                            groupId, result.getValue()),
                    data.getWsServerEndpoint()
            );
        }
        CardsGroupData result = cardsGroupDataMapper.randomGetCard(groupId);
        if (result == null) {
            return CustomText.getText("cards.draw.not.data");
        }
        cardsGroupDataMapper.deleteById(result.getId());
        return CustomText.getText("cards.draw.hide.success");
    }

    @InstructReflex(value = {"draw"}, priority = 2)
    public String drawOut(DiceMessageDTO data) {
        Long groupId;
        if (data.getMessageEvent().getSub_type().equals(EventEnum.MessageSubType.NORMAL.getName())) {
            groupId = data.getMessageEvent().getGroup_id();
        } else {
            groupId = -data.getSanderId();

        }

        CardsGroupData result = cardsGroupDataMapper.randomGetCard(groupId);
        if (result == null) {
            return CustomText.getText("cards.draw.not.data");
        }
        cardsGroupDataMapper.deleteById(result.getId());
        return CustomText.getText("cards.draw.success", result.getValue());
    }

    @InstructReflex(value = {"drawclear", "drawClear"}, priority = 3)
    public String drawClear(DiceMessageDTO data) {
        Long groupId;
        if (data.getMessageEvent().getSub_type().equals(EventEnum.MessageSubType.NORMAL.getName())) {
            groupId = data.getMessageEvent().getGroup_id();
        } else {
            groupId = -data.getSanderId();

        }
        cardsGroupDataMapper.clearByGroupId(groupId);
        return CustomText.getText("cards.draw.clear");
    }


}
