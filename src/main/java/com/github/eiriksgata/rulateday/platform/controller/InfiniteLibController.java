package com.github.eiriksgata.rulateday.platform.controller;

import com.github.eiriksgata.rulateday.platform.mapper.InfiniteLibLuMapper;
import com.github.eiriksgata.rulateday.platform.mapper.InfiniteLibOperationRecordMapper;
import com.github.eiriksgata.rulateday.platform.pojo.InfiniteLibOperationRecord;
import com.github.eiriksgata.rulateday.platform.pojo.QueryDataBase;
import com.github.eiriksgata.rulateday.platform.service.InfiniteDataCollectionService;
import com.github.eiriksgata.rulateday.platform.vo.ResponseBean;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "无限团规则书库")
@Slf4j
@RequestMapping("/api/v1")
public class InfiniteLibController {

    @Autowired
    InfiniteDataCollectionService infiniteDataCollectionService;

    @Autowired
    InfiniteLibOperationRecordMapper recordMapper;

    @Autowired
    InfiniteLibLuMapper infiniteLibLuMapper;

    @PostMapping("/infinite/lib")
    public ResponseBean<QueryDataBase> infiniteLibDataCollection(@Validated @RequestBody QueryDataBase queryDataBase) {
        return ResponseBean.success(
                infiniteDataCollectionService.dataUpdate(queryDataBase));

    }

    @DeleteMapping("/infinite/lib")
    public ResponseBean<?> infiniteLibDataDelete(@RequestBody QueryDataBase queryDataBase) {
        infiniteDataCollectionService.dataDelete(queryDataBase);
        return ResponseBean.success();
    }

    @GetMapping("/infinite/lib")
    public ResponseBean<List<QueryDataBase>> infiniteLibAllData() {
        return ResponseBean.success(
                infiniteDataCollectionService.getAllNameData()
        );
    }

    @PostMapping("/infinite/lib/name")
    public ResponseBean<QueryDataBase> infiniteLibDataByName(@RequestBody QueryDataBase queryDataBase) {
        return ResponseBean.success(
                infiniteDataCollectionService.getDataByName(queryDataBase.getName())
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

    @PostMapping("/infinite/lib/record")
    public ResponseBean<List<InfiniteLibOperationRecord>> infiniteLibRecord(@RequestBody QueryDataBase queryDataBase) {
        return ResponseBean.success(
                recordMapper.selectAllByName(
                        queryDataBase.getName()
                )
        );
    }


}
