package indi.eiriksgata.rulateday.api.controller;

import indi.eiriksgata.rulateday.api.service.FeedbackService;
import indi.eiriksgata.rulateday.api.vo.DiceExceptionVo;
import indi.eiriksgata.rulateday.api.vo.FeedbackVo;
import indi.eiriksgata.rulateday.api.vo.PageHelperBean;
import indi.eiriksgata.rulateday.api.vo.ResponseBean;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: create by Keith
 * version: v1.0
 * description: indi.eiriksgata.rulatedayapi.controller
 * date: 2021/5/20
 **/
@RestController
@Api(tags = "骰子系统异常记录管理")
public class DiceExceptionController {

    @Autowired
    FeedbackService feedbackService;

    @PostMapping("/feedback/exception")
    public ResponseBean<?> feedbackException(@RequestBody DiceExceptionVo diceExceptionVo) {
        if (!diceExceptionVo.getTitle().equals("在事件处理中发生异常")) {
            feedbackService.addDiceExceptionRecord(
                    diceExceptionVo.getTitle(),
                    diceExceptionVo.getContent(),
                    diceExceptionVo.getQqId()
            );
        }
        return ResponseBean.success();
    }


    @PostMapping("/api/v1/feedback/query")
    public ResponseBean<?> feedbackQuery(@RequestBody PageHelperBean<String> data) {
        return ResponseBean.success(
                feedbackService.diceExceptionQuery(data.getPageNumber(), data.getPageSize())
        );
    }


    @PostMapping("/api/v1/feedback/delete")
    public ResponseBean<?> feedbackDelete(@RequestBody FeedbackVo feedbackVo) {
        feedbackService.deleteFeedback(feedbackVo.getId());
        return ResponseBean.success();
    }


}
