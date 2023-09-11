package com.github.eiriksgata.rulateday.platform.service.impl;

import com.github.eiriksgata.rulateday.platform.exception.CommonBaseException;
import com.github.eiriksgata.rulateday.platform.exception.CommonBaseExceptionEnum;
import com.github.eiriksgata.rulateday.platform.mapper.DiceExceptionMapper;
import com.github.eiriksgata.rulateday.platform.pojo.DiceException;
import com.github.eiriksgata.rulateday.platform.service.FeedbackService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    DiceExceptionMapper diceExceptionMapper;

    @Override
    @Transactional
    public void addDiceExceptionRecord(String title, String content, long qqId) {
        DiceException addData = new DiceException();
        addData.setQqId(qqId);
        addData.setTitle(title);
        addData.setContent(content);
        addData.setCreatedTimestamp(System.currentTimeMillis());
        addData.setId(null);
        try {
            diceExceptionMapper.insert(addData);
        } catch (PersistenceException exception) {
            throw new CommonBaseException(CommonBaseExceptionEnum.DICE_EXCEPTION_COMMIT_ERROR);
        }
    }

    @Override
    public PageInfo<?> diceExceptionQuery(int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        return new PageInfo<>(diceExceptionMapper.selectAll());
    }

    @Override
    @Transactional
    public void deleteFeedback(Long id) {
        diceExceptionMapper.deleteById(id);
    }


}
