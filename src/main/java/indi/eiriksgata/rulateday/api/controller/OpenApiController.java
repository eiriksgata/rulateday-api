package indi.eiriksgata.rulateday.api.controller;

import indi.eiriksgata.rulateday.api.service.HumanNameService;
import indi.eiriksgata.rulateday.api.vo.openapi.GenNameVo;
import indi.eiriksgata.rulateday.api.mapper.InfiniteLibLuMapper;
import indi.eiriksgata.rulateday.api.pojo.QueryDataBase;
import indi.eiriksgata.rulateday.api.vo.ResponseBean;
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
