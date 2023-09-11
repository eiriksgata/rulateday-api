package com.github.eiriksgata.rulateday.platform.controller;

import com.github.eiriksgata.rulateday.platform.pojo.AiTextDrawTask;
import com.github.eiriksgata.rulateday.platform.service.AiDrawService;
import com.github.eiriksgata.rulateday.platform.vo.ResponseBean;
import com.github.eiriksgata.rulateday.platform.vo.openapi.ClientAiDrawTaskResultVo;
import com.github.eiriksgata.rulateday.platform.vo.openapi.GetAiDrawTaskVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping("/openapi/v1/ai/draw")
public class AiDrawController {

    @Autowired
    AiDrawService aiDrawService;

    @PostMapping("/get/task/text")
    public ResponseBean<?> getAiDrawTextTask(@RequestBody GetAiDrawTaskVo getAiDrawTaskVo) {

        AiTextDrawTask aiTextDrawTask = aiDrawService.getAiTextDrawTask();
        if (aiTextDrawTask == null) {
            return ResponseBean.error("当前系统没有任务");
        }
        aiDrawService.deviceReceiveTaskStateChange(aiTextDrawTask.getId(), getAiDrawTaskVo.getClientId());

        aiTextDrawTask.setId(null);
        aiTextDrawTask.setMachineCode("");
        aiTextDrawTask.setGroupId(null);
        aiTextDrawTask.setUserId("");
        aiTextDrawTask.setCreatedById(null);

        return ResponseBean.success(
                aiTextDrawTask
        );
    }

    @PostMapping("/submit/task/text")
    public ResponseBean<?> submitTaskTextResult(@RequestBody ClientAiDrawTaskResultVo clientAiDrawTaskResultVo) {
        aiDrawService.updateAiDrawResult(clientAiDrawTaskResultVo);
        return ResponseBean.success();
    }

}
