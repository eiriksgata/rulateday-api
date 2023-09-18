package com.github.eiriksgata.rulateday.platform.controller;

import com.github.eiriksgata.rulateday.platform.service.FfxivSchemeNodeService;
import com.github.eiriksgata.rulateday.platform.service.FfxivSchemeService;
import com.github.eiriksgata.rulateday.platform.vo.ResponseBean;
import com.github.eiriksgata.rulateday.platform.vo.ffxiv.FfxivNpcCardsVo;
import com.github.eiriksgata.rulateday.platform.pojo.ffxiv.FfxivNpcDTO;
import com.github.eiriksgata.rulateday.platform.pojo.ffxiv.FfxivSchemeDTO;
import com.github.eiriksgata.rulateday.platform.pojo.ffxiv.FfxivSchemeNodeDTO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api
@RequestMapping("/api/v1")
public class FfxivSchemeController {

    @Autowired
    FfxivSchemeService ffxivSchemeService;

    @Autowired
    FfxivSchemeNodeService ffxivSchemeNodeService;


    @GetMapping("/ffxiv/scheme/query/all")
    public ResponseBean<?> ffxivSchemeQueryAll() {
        return ResponseBean.success(ffxivSchemeService.findAllScheme());
    }

    @PutMapping("/ffxiv/scheme/query/id")
    public ResponseBean<?> findSchemeById(@RequestBody FfxivSchemeDTO ffxivSchemeDTO) {
        return ResponseBean.success(
                ffxivSchemeService.findById(ffxivSchemeDTO.getId())
        );
    }

    @PutMapping("/ffxiv/scheme/query/npc/id")
    public ResponseBean<?> findSchemeByNpcId(@RequestBody FfxivNpcDTO ffxivNpcDTO) {
        return ResponseBean.success(
                ffxivSchemeService.findSchemeByNpcId(ffxivNpcDTO.getId())
        );
    }


    @PutMapping("/ffxiv/scheme/cards/query/id")
    public ResponseBean<?> ffxivSchemeCardsBySchemeId(@RequestBody FfxivSchemeDTO ffxivSchemeDTO) {
        return ResponseBean.success(
                ffxivSchemeService.findSchemeCards(ffxivSchemeDTO.getId())
        );
    }

    @PutMapping("/ffxiv/scheme/save")
    public ResponseBean<?> ffxivSchemeSave(@RequestBody FfxivSchemeDTO ffxivSchemeDTO) {
        ffxivSchemeService.saveFfxivScheme(ffxivSchemeDTO);
        return ResponseBean.success();
    }

    @PutMapping("/ffxiv/scheme/cards/save")
    public ResponseBean<?> ffxivSchemeCardsSave(@RequestBody FfxivNpcCardsVo ffxivNpcCardsVo) {
        ffxivSchemeService.saveSchemeCards(ffxivNpcCardsVo.getId(), ffxivNpcCardsVo.getCards());
        return ResponseBean.success();
    }


    @PutMapping("/ffxiv/scheme/node")
    public ResponseBean<?> ffxivSchemeNodeAdd(@RequestBody FfxivSchemeNodeDTO ffxivSchemeNodeDTO) {
        ffxivSchemeNodeService.addNode(ffxivSchemeNodeDTO);
        return ResponseBean.success();
    }

    @PutMapping("/ffxiv/scheme/node/query/id")
    public ResponseBean<?> findFfxivSchemeNode(@RequestBody FfxivSchemeDTO ffxivSchemeDTO) {
        return ResponseBean.success(
                ffxivSchemeNodeService.findSchemeNodeBySchemeId(ffxivSchemeDTO.getId())
        );
    }

    @DeleteMapping("/ffxiv/scheme/node")
    public ResponseBean<?> ffxivSchemeNodeDelete(@RequestBody FfxivSchemeNodeDTO ffxivSchemeNodeDTO) {
        ffxivSchemeNodeService.deleteNode(ffxivSchemeNodeDTO.getId());
        return ResponseBean.success();
    }


    @DeleteMapping("/ffxiv/scheme/delete/id")
    public ResponseBean<?> ffxivSchemeDeleteById(@RequestBody FfxivSchemeDTO ffxivSchemeDTO) {
        ffxivSchemeService.deleteScheme(ffxivSchemeDTO.getId());
        return ResponseBean.success();
    }


}
