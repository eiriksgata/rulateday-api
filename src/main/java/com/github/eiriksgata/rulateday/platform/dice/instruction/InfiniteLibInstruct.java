package com.github.eiriksgata.rulateday.platform.dice.instruction;

import com.github.eiriksgata.rulateday.platform.dice.dto.DiceMessageDTO;
import com.github.eiriksgata.rulateday.platform.dice.service.UserConversationService;
import com.github.eiriksgata.rulateday.platform.mapper.DiceConfigMapper;
import com.github.eiriksgata.rulateday.platform.pojo.QueryDataBase;
import com.github.eiriksgata.rulateday.platform.vo.ResponseBean;
import com.github.eiriksgata.trpg.dice.injection.InstructReflex;
import com.github.eiriksgata.trpg.dice.injection.InstructService;
import com.github.eiriksgata.trpg.dice.reply.CustomText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@InstructService
@Component
public class InfiniteLibInstruct {

    @Autowired
    UserConversationService conversationService;

    @Autowired
    DiceConfigMapper diceConfigMapper;

    @Autowired
    com.github.eiriksgata.rulateday.platform.controller.InfiniteLibController infiniteLibController;

    @InstructReflex(value = {"ir2"})
    public String infiniteLibOnlineQuery(DiceMessageDTO data) {
        if (diceConfigMapper.selectById().getBeta_version() == 0) {
            return CustomText.getText("infinite.lib.ir2.no.enable");
        }
        //如果输入的数据是无关键字的
        if (data.getBody().equals("") || data.getBody().equals(" ")) {
            return CustomText.getText("dr5e.rule.not.parameter");
        }

        QueryDataBase queryDataBase = new QueryDataBase();
        queryDataBase.setName(data.getBody());
        ResponseBean<List<QueryDataBase>> result = infiniteLibController.infiniteLibDataLikeName(queryDataBase);
        List<QueryDataBase> saveData = new ArrayList<>();
        if (result.getData() != null) {
            if (result.getData().size() > 1) {
                StringBuilder text = new StringBuilder(CustomText.getText("dr5e.rule.lib.result.list.title"));
                int count = 0;
                for (QueryDataBase temp : result.getData()) {
                    if (count >= 20) {
                        text.append(CustomText.getText("dr5e.rule.lib.result.list.tail"));
                        break;
                    } else {
                        text.append("\n").append(count).append(". ").append(temp.getName());
                        saveData.add(temp);
                    }
                    count++;
                }
                //将记录暂时存入数据库
                conversationService.saveConversation(data.getSanderId(), saveData);
                return text.toString();
            } else {
                if (result.getData().size() == 0) {
                    return CustomText.getText("dr5e.rule.lib.result.list.not.found");
                }
                return "\n" + result.getData().get(0).getName() + "\n" + result.getData().get(0).getDescribe();

            }
        }
        return CustomText.getText("infinite.lib.online.query.error");
    }

}
