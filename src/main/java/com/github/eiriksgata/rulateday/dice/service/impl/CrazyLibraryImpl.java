package com.github.eiriksgata.rulateday.dice.service.impl;

import com.github.eiriksgata.rulateday.mapper.CrazyDescribeMapper;
import com.github.eiriksgata.rulateday.mapper.CrazyOverDescribeMapper;
import com.github.eiriksgata.rulateday.pojo.CrazyDescribe;
import com.github.eiriksgata.rulateday.pojo.CrazyOverDescribe;
import com.github.eiriksgata.rulateday.service.CrazyLibraryService;
import com.github.eiriksgata.rulateday.utlis.MyBatisUtil;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;

/**
 * author: create by Keith
 * version: v1.0
 * description: com.github.eiriksgata.rulateday.service.impl
 * date: 2020/11/4
 **/
public class CrazyLibraryImpl implements CrazyLibraryService {

    private static final CrazyDescribeMapper crazyMapper = MyBatisUtil.getSqlSession().getMapper(CrazyDescribeMapper.class);
    private static final CrazyOverDescribeMapper overMapper = MyBatisUtil.getSqlSession().getMapper(CrazyOverDescribeMapper.class);

    @Override
    public String getRandomCrazyDescribe() {
        List<CrazyDescribe> result = crazyMapper.selectAll();
        int random = RandomUtils.nextInt(0, result.size());
        return result.get(random).getDescribe();
    }

    @Override
    public String getCrazyOverDescribe() {
        List<CrazyOverDescribe> result = overMapper.selectAll();
        int random = RandomUtils.nextInt(0, result.size());
        return result.get(random).getDescribe();
    }


}
