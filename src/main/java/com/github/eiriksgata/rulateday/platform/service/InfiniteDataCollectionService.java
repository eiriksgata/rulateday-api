package com.github.eiriksgata.rulateday.platform.service;

import com.github.eiriksgata.rulateday.platform.pojo.QueryDataBase;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface InfiniteDataCollectionService {
    @Transactional(rollbackFor = RuntimeException.class)
    QueryDataBase dataUpdate(QueryDataBase queryDataBase);

    @Transactional(rollbackFor = RuntimeException.class)
    void dataDelete(QueryDataBase queryDataBase);

    List<QueryDataBase> getAllNameData();

    QueryDataBase getDataByName(String name);
}
