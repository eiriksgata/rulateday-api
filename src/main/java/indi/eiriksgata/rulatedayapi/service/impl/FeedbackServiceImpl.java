package indi.eiriksgata.rulatedayapi.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import indi.eiriksgata.rulateday.mapper.DiceExceptionMapper;
import indi.eiriksgata.rulateday.pojo.DiceException;
import indi.eiriksgata.rulatedayapi.exception.CommonBaseException;
import indi.eiriksgata.rulatedayapi.exception.CommonBaseExceptionEnum;
import indi.eiriksgata.rulatedayapi.service.FeedbackService;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * author: create by Keith
 * version: v1.0
 * description: indi.eiriksgata.rulatedayapi.service.impl
 * date: 2021/5/20
 **/
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
    public PageInfo diceExceptionQuery(int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        return new PageInfo<>(diceExceptionMapper.selectAll());
    }

    @Override
    @Transactional
    public void deleteFeedback(Long id) {
        diceExceptionMapper.deleteById(id);
    }


}
