package com.github.eiriksgata.rulateday.platform.service.impl;

import com.github.eiriksgata.rulateday.platform.mapper.InfiniteLibLuMapper;
import com.github.eiriksgata.rulateday.platform.mapper.InfiniteLibOperationRecordMapper;
import com.github.eiriksgata.rulateday.platform.pojo.InfiniteLibOperationRecord;
import com.github.eiriksgata.rulateday.platform.pojo.QueryDataBase;
import com.github.eiriksgata.rulateday.platform.service.InfiniteDataCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InfiniteDataCollectionServiceImpl implements InfiniteDataCollectionService {

    @Autowired
    InfiniteLibLuMapper infiniteLibLuMapper;

    @Autowired
    InfiniteLibOperationRecordMapper infiniteLibOperationRecordMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public QueryDataBase dataUpdate(QueryDataBase queryDataBase) {
        //将标点的中文符号转为英文
        queryDataBase.setName(queryDataBase.getName().replaceAll("：", ":"));
        QueryDataBase result = infiniteLibLuMapper.selectByName(queryDataBase.getName());
        InfiniteLibOperationRecord record = new InfiniteLibOperationRecord();
        record.setName(queryDataBase.getName());
        record.setQqid("");
        record.setDescribe(queryDataBase.getDescribe());
        if (result == null) {
            record.setType("insert");
            infiniteLibLuMapper.insert(queryDataBase);
            infiniteLibOperationRecordMapper.insert(record);
            return queryDataBase;
        }
        record.setType("update");
        infiniteLibOperationRecordMapper.insert(record);
        infiniteLibLuMapper.updateById(queryDataBase.getId(), queryDataBase.getName(), queryDataBase.getDescribe());
        return queryDataBase;
    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void dataDelete(QueryDataBase queryDataBase) {
        QueryDataBase selectResult = infiniteLibLuMapper.selectById(queryDataBase.getId());
        if (selectResult == null) {
            return;
        }
        InfiniteLibOperationRecord record = new InfiniteLibOperationRecord();
        record.setName(selectResult.getName());
        record.setQqid("");
        record.setDescribe(selectResult.getDescribe());
        record.setType("delete");
        infiniteLibOperationRecordMapper.insert(record);
        infiniteLibLuMapper.deleteById(queryDataBase.getId());
    }

    @Override
    public List<QueryDataBase> getAllNameData() {
        return infiniteLibLuMapper.selectAllName();
    }

    @Override
    public QueryDataBase getDataByName(String name) {
        return infiniteLibLuMapper.selectByName(name);
    }


}


