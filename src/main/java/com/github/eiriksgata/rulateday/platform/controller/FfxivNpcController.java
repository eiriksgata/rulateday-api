package com.github.eiriksgata.rulateday.platform.controller;

import com.github.eiriksgata.rulateday.platform.config.NotRequireAuthentication;
import com.github.eiriksgata.rulateday.platform.config.WebPathConfig;
import com.github.eiriksgata.rulateday.platform.service.FfxivNpcService;
import com.github.eiriksgata.rulateday.platform.utils.ImageToBase64;
import com.github.eiriksgata.rulateday.platform.vo.ResponseBean;
import com.github.eiriksgata.rulateday.platform.vo.ffxiv.FfxivNpcCardsVo;
import com.github.eiriksgata.rulateday.platform.pojo.ffxiv.FfxivCardDTO;
import com.github.eiriksgata.rulateday.platform.pojo.ffxiv.FfxivNpcDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class FfxivNpcController {

    @Autowired
    FfxivNpcService ffxivNpcService;

    @PutMapping("/ffxiv/npc")
    @NotRequireAuthentication
    public ResponseBean<?> ffxivNpcDataAdd(@RequestBody FfxivNpcDTO ffxivNpcDTO) {
        FfxivNpcDTO result = ffxivNpcService.save(ffxivNpcDTO);
        if (ffxivNpcDTO.getPictureBase64() != null && !ffxivNpcDTO.getPictureBase64().equals("")) {
            ImageToBase64.base64ToImage(ffxivNpcDTO.getPictureBase64(),
                    WebPathConfig.DEFAULT_FILE_PATH + "/ffxiv/npc/" + result.getCode() + ".png");
        }
        return ResponseBean.success();
    }


    @PutMapping("/ffxiv/npc/id")
    @NotRequireAuthentication
    public ResponseBean<?> ffxivNpcInfoQuery(@RequestBody FfxivNpcDTO ffxivNpcDTO) {
        return ResponseBean.success(
                ffxivNpcService.selectById(ffxivNpcDTO.getId())
        );
    }

    @GetMapping("/ffxiv/npc")
    @NotRequireAuthentication
    public ResponseBean<?> findAllFfxivNpcData() {
        return ResponseBean.success(
                ffxivNpcService.selectAll()
        );
    }


    @PutMapping("/ffxiv/npc/cards/save")
    public ResponseBean<?> ffxivNpcCardSave(@RequestBody FfxivNpcCardsVo ffxivNpcCardsVo) {
        ffxivNpcService.saveCardRel(ffxivNpcCardsVo.getId(), ffxivNpcCardsVo.getCards());
        return ResponseBean.success();
    }

    @PutMapping("/ffxiv/npc/cards/query/id")
    @NotRequireAuthentication
    public ResponseBean<List<FfxivCardDTO>> findCardsByNpcId(@RequestBody FfxivNpcDTO ffxivNpcDTO) {
        return ResponseBean.success(
                ffxivNpcService.findNpcCards(ffxivNpcDTO.getId())
        );
    }

    @DeleteMapping("/ffxiv/npc")
    public ResponseBean<?> deleteFfxivNpc(@RequestBody FfxivNpcDTO ffxivNpcDTO) {
        ffxivNpcService.delete(ffxivNpcDTO.getId());
        return ResponseBean.success();
    }

}
