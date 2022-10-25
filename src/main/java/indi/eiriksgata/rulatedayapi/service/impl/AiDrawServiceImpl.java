package indi.eiriksgata.rulatedayapi.service.impl;

import indi.eiriksgata.rulateday.mapper.AiTextDrawMapper;
import indi.eiriksgata.rulateday.pojo.AiTextDrawTask;
import indi.eiriksgata.rulatedayapi.service.AiDrawingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AiDrawServiceImpl implements AiDrawingService {

    @Autowired
    AiTextDrawMapper aiTextDrawMapper;


    public void addTextDrawingTask(AiTextDrawTask aiTextDrawTask) {

        //限制用户增加任务
        aiTextDrawMapper.
        aiTextDrawTask.getUserId()


    }


}
