package indi.eiriksgata.rulatedayapi.service;

import indi.eiriksgata.rulateday.pojo.QueryDataBase;
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
