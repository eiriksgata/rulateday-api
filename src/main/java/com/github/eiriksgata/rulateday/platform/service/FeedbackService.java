package com.github.eiriksgata.rulateday.platform.service;

import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

/**
 * author: create by Keith
 * version: v1.0
 * description: indi.eiriksgata.rulatedayapi.service
 * date: 2021/5/20
 **/
public interface FeedbackService {
    @Transactional
    void addDiceExceptionRecord(String title, String content, long qqId);

    PageInfo<?> diceExceptionQuery(int pageNumber, int pageSize);

    @Transactional
    void deleteFeedback(Long id);
}
