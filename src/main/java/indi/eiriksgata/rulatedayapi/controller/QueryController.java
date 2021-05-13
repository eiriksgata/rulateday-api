package indi.eiriksgata.rulatedayapi.controller;

import indi.eiriksgata.rulatedayapi.vo.ResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: create by Keith
 * version: v1.0
 * description: indi.eiriksgata.rulatedayapi.controller
 * date: 2021/5/12
 **/
@RestController
@Api(tags = "查询控制器")
public class QueryController {

    @PostMapping("/dnd5e/query")
    @ApiOperation(value = "Dnd5e数据查询接口", httpMethod = "POST")
    public ResponseBean dnd5eLibQuery() {

        return ResponseBean.success();
    }


}
