package com.github.eiriksgata.rulateday.platform.service.impl;

import com.github.eiriksgata.rulateday.platform.exception.CommonBaseException;
import com.github.eiriksgata.rulateday.platform.exception.CommonBaseExceptionEnum;
import com.github.eiriksgata.rulateday.platform.pojo.TrpgEventSchemaEntity;
import com.github.eiriksgata.rulateday.platform.pojo.TrpgEventSchemaRelEntity;
import com.github.eiriksgata.rulateday.platform.service.EventService;
import com.github.eiriksgata.rulateday.platform.vo.EventContentQueryVo;
import com.github.eiriksgata.rulateday.platform.vo.TrpgEventDataVo;
import com.github.eiriksgata.rulateday.platform.mapper.TrpgEventSchemaMapper;
import com.github.eiriksgata.rulateday.platform.mapper.TrpgEventSchemaRelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    TrpgEventSchemaMapper eventSchemaMapper;

    @Autowired
    TrpgEventSchemaRelMapper relMapper;

    @Override
    public List<TrpgEventDataVo> queryFirstNode() {
        List<TrpgEventDataVo> list = new ArrayList<>();
        List<TrpgEventSchemaEntity> result = eventSchemaMapper.queryAllFirstNode();
        for (TrpgEventSchemaEntity entity : result) {
            TrpgEventDataVo trpgEventDataVo = new TrpgEventDataVo();
            trpgEventDataVo.setEventId(entity.getId());
            trpgEventDataVo.setContent(entity.getContent());
            trpgEventDataVo.setFirstNode(true);
            list.add(trpgEventDataVo);
        }
        return list;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void addNode(String title, String content, Integer previousEventId, Boolean firstNode) {
        TrpgEventSchemaEntity temp = new TrpgEventSchemaEntity();
        temp.setTitle(title);
        temp.setCreatedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        temp.setFirstNode(firstNode);
        temp.setContent(content);
        eventSchemaMapper.insertResultId(temp);
        TrpgEventSchemaRelEntity entity = new TrpgEventSchemaRelEntity();
        entity.setEventId(previousEventId);
        entity.setNextEventId(temp.getId());
        entity.setCreatedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        relMapper.insertResultId(entity);
    }

    @Override
    public List<TrpgEventDataVo> selectNextNodes(Integer eventId) {
        List<TrpgEventSchemaRelEntity> result = relMapper.selectByEventId(eventId);
        List<TrpgEventDataVo> list = new ArrayList<>();
        for (TrpgEventSchemaRelEntity temp : result) {
            TrpgEventDataVo dataVo = new TrpgEventDataVo();
            dataVo.setEventId(temp.getNextEventId());
            dataVo.setTitle(eventSchemaMapper.selectById(temp.getNextEventId()).getTitle());
            list.add(dataVo);
        }
        return list;
    }

    @Override
    public EventContentQueryVo selectContent(EventContentQueryVo eventContentQueryVo) {
        eventContentQueryVo.setContent(
                eventSchemaMapper.selectById(eventContentQueryVo.getEventId())
                        .getContent()
        );
        return eventContentQueryVo;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void deleteNode(Integer eventId) {
        //当删除一个节点时，需要确认当前节点是否有下属节点，如果有则不能删除
        List<?> result = selectNextNodes(eventId);
        if (result.size() == 0) {
            //同时还要删除对应的关系
            relMapper.deleteByNextEventId(eventId);
            eventSchemaMapper.deleteById(eventId);
            return;
        }
        throw new CommonBaseException(CommonBaseExceptionEnum.GAME_EVENT_NODE_DELETE_ERROR);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void addEventRel(Integer currentEventId, Integer nextEventId) {
        TrpgEventSchemaRelEntity entity = new TrpgEventSchemaRelEntity();
        entity.setEventId(currentEventId);
        entity.setNextEventId(nextEventId);
        relMapper.insertResultId(entity);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteEventRel(Integer eventId, Integer nextEventId) {
        relMapper.deleteByLink(eventId,nextEventId);
    }


}
