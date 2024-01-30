package com.github.eiriksgata.rulateday.platform.controller;

import com.github.eiriksgata.rulateday.platform.dice.dto.DiceMessageDTO;
import com.github.eiriksgata.rulateday.platform.dice.instruction.QueryInstruct;
import com.github.eiriksgata.rulateday.platform.mapper.Dnd5ePhbDataMapper;
import com.github.eiriksgata.rulateday.platform.utils.SpringContextUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.eiriksgata.rulateday.platform.vo.PageHelperBean;
import com.github.eiriksgata.rulateday.platform.vo.ResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

/**
 * author: create by Keith
 * version: v1.0
 * description: com.github.eiriksgata.rulatedayapi.controller
 * date: 2021/5/12
 **/
@RestController
@Api(tags = "查询控制器")
public class QueryController {

    @Autowired
    Dnd5ePhbDataMapper dnd5ePhbDataMapper;

    @Autowired
    ApplicationContext applicationContext;

    @PostMapping("/dnd5e/spell")
    @ApiOperation(value = "dnd5e法术列表", httpMethod = "POST")
    public ResponseBean<PageInfo<?>> dnd5eLibQuery(@RequestBody PageHelperBean<String> data) {
        PageHelper.startPage(data.getPageNumber(), data.getPageSize());
        return ResponseBean.success(new PageInfo<>(dnd5ePhbDataMapper.selectAllSkillPhb()));
    }

    @GetMapping("/test/ti/{class}")
    public ResponseBean<?> testTi(@PathVariable("class")String name) {
        QueryInstruct queryInstruct = applicationContext.getBean( QueryInstruct.class);
        return ResponseBean.success(
                queryInstruct.getCrazyState(new DiceMessageDTO())
        );
    }


}
