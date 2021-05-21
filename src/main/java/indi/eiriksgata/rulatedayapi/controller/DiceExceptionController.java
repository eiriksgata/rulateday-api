package indi.eiriksgata.rulatedayapi.controller;

import com.github.pagehelper.PageHelper;
import indi.eiriksgata.rulatedayapi.service.FeedbackService;
import indi.eiriksgata.rulatedayapi.vo.DiceExceptionVo;
import indi.eiriksgata.rulatedayapi.vo.PageHelperBean;
import indi.eiriksgata.rulatedayapi.vo.ResponseBean;
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
    public ResponseBean feedbackException(@RequestBody DiceExceptionVo diceExceptionVo) {
        feedbackService.addDiceExceptionRecord(
                diceExceptionVo.getTitle(),
                diceExceptionVo.getContent(),
                diceExceptionVo.getQqId()
        );
        return ResponseBean.success();
    }


    @PostMapping("/feedback/query")
    public ResponseBean feedbackQuery(@RequestBody PageHelperBean<String> data) {
        return ResponseBean.success(
                feedbackService.diceExceptionQuery(data.getPageNumber(), data.getPageSize())
        );
    }


}
