package com.github.eiriksgata.rulateday.platform.controller;

import com.github.eiriksgata.rulateday.platform.mapper.InfiniteLibLuMapper;
import com.github.eiriksgata.rulateday.platform.pojo.QueryDataBase;
import com.github.eiriksgata.rulateday.platform.service.HumanNameService;
import com.github.eiriksgata.rulateday.platform.vo.openapi.GenNameVo;
import com.github.eiriksgata.rulateday.platform.vo.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/openapi/v1")
public class OpenApiController {

    @Autowired
    HumanNameService humanNameService;

    @Autowired
    InfiniteLibLuMapper infiniteLibLuMapper;

    @PostMapping("/random/human/name")
    public ResponseBean<?> genRandomName(@RequestBody GenNameVo genNameVo) {
        return ResponseBean.success(
                humanNameService.randomName(genNameVo.getNumber())
        );
    }

    @PostMapping("/infinite/lib/query/name")
    public ResponseBean<List<QueryDataBase>> infiniteLibDataLikeName(@RequestBody QueryDataBase queryDataBase) {
        return ResponseBean.success(
                infiniteLibLuMapper.selectLikeName(
                        "%" + queryDataBase.getName() + "%"
                )
        );
    }


}
