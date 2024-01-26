package com.github.eiriksgata.rulateday.platform.dice.service.impl;

import com.github.eiriksgata.rulateday.platform.dice.service.CrazyLibraryService;
import com.github.eiriksgata.rulateday.platform.mapper.CrazyDescribeMapper;
import com.github.eiriksgata.rulateday.platform.mapper.CrazyOverDescribeMapper;
import com.github.eiriksgata.rulateday.platform.pojo.CrazyDescribe;
import com.github.eiriksgata.rulateday.platform.pojo.CrazyOverDescribe;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author: create by Keith
 * version: v1.0
 * description: com.github.eiriksgata.rulateday.service.impl
 * date: 2020/11/4
 **/
@Service
public class CrazyLibraryImpl implements CrazyLibraryService {

    @Autowired
    CrazyDescribeMapper crazyMapper;


    @Autowired
    CrazyOverDescribeMapper overMapper;


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
