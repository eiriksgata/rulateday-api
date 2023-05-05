package indi.eiriksgata.rulateday.api.controller;

import indi.eiriksgata.rulateday.api.config.WebPathConfig;
import indi.eiriksgata.rulateday.api.service.FfxivNpcService;
import indi.eiriksgata.rulateday.api.utils.ImageToBase64;
import indi.eiriksgata.rulateday.api.vo.ResponseBean;
import indi.eiriksgata.rulateday.api.vo.ffxiv.FfxivNpcCardsVo;
import indi.eiriksgata.rulateday.pojo.ffxiv.FfxivCardDTO;
import indi.eiriksgata.rulateday.pojo.ffxiv.FfxivNpcDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FfxivNpcController {

    @Autowired
    FfxivNpcService ffxivNpcService;

    @PutMapping("/ffxiv/npc")
    public ResponseBean<?> ffxivNpcDataAdd(@RequestBody FfxivNpcDTO ffxivNpcDTO) {
        FfxivNpcDTO result = ffxivNpcService.save(ffxivNpcDTO);
        if (ffxivNpcDTO.getPictureBase64() != null && !ffxivNpcDTO.getPictureBase64().equals("")) {
            ImageToBase64.base64ToImage(ffxivNpcDTO.getPictureBase64(),
                    WebPathConfig.DEFAULT_FILE_PATH + "/ffxiv/npc/" + result.getCode() + ".png");
        }
        return ResponseBean.success();
    }


    @PutMapping("/ffxiv/npc/id")
    public ResponseBean<?> ffxivNpcInfoQuery(@RequestBody FfxivNpcDTO ffxivNpcDTO) {
        return ResponseBean.success(
                ffxivNpcService.selectById(ffxivNpcDTO.getId())
        );
    }

    @GetMapping("/ffxiv/npc")
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
