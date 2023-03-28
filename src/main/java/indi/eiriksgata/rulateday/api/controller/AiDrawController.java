package indi.eiriksgata.rulateday.api.controller;

import indi.eiriksgata.rulateday.pojo.AiTextDrawTask;
import indi.eiriksgata.rulateday.api.service.AiDrawService;
import indi.eiriksgata.rulateday.api.vo.ResponseBean;
import indi.eiriksgata.rulateday.api.vo.openapi.ClientAiDrawTaskResultVo;
import indi.eiriksgata.rulateday.api.vo.openapi.GetAiDrawTaskVo;
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
