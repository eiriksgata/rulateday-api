package com.github.eiriksgata.rulateday.platform.controller;

import com.github.eiriksgata.rulateday.platform.mapper.Dnd5ePhbDataMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.eiriksgata.rulateday.platform.vo.PageHelperBean;
import com.github.eiriksgata.rulateday.platform.vo.ResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/dnd5e/spell")
    @ApiOperation(value = "dnd5e法术列表", httpMethod = "POST")
    public ResponseBean<PageInfo<?>> dnd5eLibQuery(@RequestBody PageHelperBean<String> data) {
        PageHelper.startPage(data.getPageNumber(), data.getPageSize());
        return ResponseBean.success(new PageInfo<>(dnd5ePhbDataMapper.selectAllSkillPhb()));
    }


}
