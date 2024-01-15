package com.github.eiriksgata.rulateday.platform.controller;

import com.github.eiriksgata.rulateday.platform.vo.EventContentQueryVo;
import com.github.eiriksgata.rulateday.platform.pojo.TrpgEventSchemaEntity;
import com.github.eiriksgata.rulateday.platform.pojo.TrpgEventSchemaRelEntity;
import com.github.eiriksgata.rulateday.platform.service.EventService;
import com.github.eiriksgata.rulateday.platform.vo.ResponseBean;
import com.github.eiriksgata.rulateday.platform.vo.TrpgEventDataVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@Api(tags = "9宫幻卡模拟器节点控制器")
@RequestMapping("/api/v1")
public class TrpgEventController {

    @Autowired
    EventService eventService;

    @GetMapping("/game/event/node/first")
    public ResponseBean<?> findEventNodeFirst() {
        return ResponseBean.success(
                eventService.queryFirstNode()
        );
    }

    @PostMapping("/game/event/node/next")
    public ResponseBean<?> findEventNextNode(@RequestBody TrpgEventSchemaEntity trpgEventSchemaEntity) {
        return ResponseBean.success(
                eventService.selectNextNodes(trpgEventSchemaEntity.getId())
        );
    }

    @PostMapping("/game/event/node/info")
    public ResponseBean<?> findEventNodeInfo(@RequestBody EventContentQueryVo eventContentQueryVo) {
        return ResponseBean.success(
                eventService.selectContent(eventContentQueryVo)
        );
    }

    @PostMapping("/game/event/node")
    public ResponseBean<?> insertNode(@RequestBody TrpgEventDataVo trpgEventDataVo) {
        eventService.addNode(
                trpgEventDataVo.getTitle(),
                trpgEventDataVo.getContent(),
                trpgEventDataVo.getPreviousEventId(),
                trpgEventDataVo.isFirstNode());
        return ResponseBean.success();
    }

    @DeleteMapping("/game/event/node")
    public ResponseBean<?> deleteNode(@RequestBody TrpgEventSchemaEntity trpgEventSchemaEntity) {
        eventService.deleteNode(trpgEventSchemaEntity.getId());
        return ResponseBean.success();
    }

    @PostMapping("/game/event/node/rel")
    public ResponseBean<?> insertNodeRel(@RequestBody TrpgEventSchemaRelEntity trpgEventSchemaRelEntity) {
        eventService.addEventRel(
                trpgEventSchemaRelEntity.getEventId(),
                trpgEventSchemaRelEntity.getNextEventId()
        );
        return ResponseBean.success();
    }

    @DeleteMapping("/game/event/node/rel")
    public ResponseBean<?> deleteNodeRel(@RequestBody TrpgEventSchemaRelEntity trpgEventSchemaRelEntity) {
        eventService.deleteEventRel(trpgEventSchemaRelEntity.getEventId(), trpgEventSchemaRelEntity.getNextEventId());
        return ResponseBean.success();
    }

}
