package com.github.eiriksgata.rulateday.platform.service;

import com.github.eiriksgata.rulateday.platform.vo.EventContentQueryVo;
import com.github.eiriksgata.rulateday.platform.vo.TrpgEventDataVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EventService {

    List<TrpgEventDataVo> queryFirstNode();

    @Transactional(rollbackFor = RuntimeException.class)
    void addNode(String title, String content, Integer previousEventId, Boolean firstNode);

    List<TrpgEventDataVo> selectNextNodes(Integer eventId);

    EventContentQueryVo selectContent(EventContentQueryVo eventContentQueryVo);

    @Transactional(rollbackFor = RuntimeException.class)
    void deleteNode(Integer eventId);

    @Transactional(rollbackFor = RuntimeException.class)
    void addEventRel(Integer currentEventId, Integer nextEventId);

    @Transactional(rollbackFor = RuntimeException.class)
    void deleteEventRel(Integer eventId, Integer nextEventId);
}
