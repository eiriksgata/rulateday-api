package indi.eiriksgata.rulatedayapi.controller;

import indi.eiriksgata.rulateday.mapper.InfiniteLibLuMapper;
import indi.eiriksgata.rulateday.pojo.QueryDataBase;
import indi.eiriksgata.rulatedayapi.service.HumanNameService;
import indi.eiriksgata.rulatedayapi.vo.ResponseBean;
import indi.eiriksgata.rulatedayapi.vo.openapi.GenNameVo;
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
