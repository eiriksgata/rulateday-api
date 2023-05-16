package indi.eiriksgata.rulateday.api.controller;

import com.alibaba.fastjson.JSONObject;
import indi.eiriksgata.rulateday.api.config.NotRequireAuthentication;
import indi.eiriksgata.rulateday.api.config.WebPathConfig;
import indi.eiriksgata.rulateday.api.service.FfxivCardEditService;
import indi.eiriksgata.rulateday.api.utils.ImageToBase64;
import indi.eiriksgata.rulateday.api.vo.ResponseBean;
import indi.eiriksgata.rulateday.api.vo.ffxiv.FfxivCardVo;
import indi.eiriksgata.rulateday.mapper.FfxivCardEditMapper;
import indi.eiriksgata.rulateday.pojo.ffxiv.FfxivCardDTO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;


@RestController
@Api
@Slf4j
@RequestMapping("/api/v1")
public class FfxivCardEditController {

    @Autowired
    FfxivCardEditService ffxivCardEditService;

    @Autowired
    FfxivCardEditMapper ffxivCardEditMapper;

    @PutMapping("/ffxiv/card/save")
    public ResponseBean<?> saveCard(@RequestBody FfxivCardVo ffxivCardVo) {
        ffxivCardEditService.save(ffxivCardVo.getCard());
        //保存卡面图片和图标数据
        if (ffxivCardVo.getPictureBase64() != null && !ffxivCardVo.getPictureBase64().equals("")) {
            ImageToBase64.base64ToImage(ffxivCardVo.getPictureBase64(),
                    WebPathConfig.DEFAULT_FILE_PATH + "/ffxiv/surface/" + ffxivCardVo.getCard().getCardCode() + ".png");
        }
        if (ffxivCardVo.getIconBase64() != null && !ffxivCardVo.getIconBase64().equals("")) {
            ImageToBase64.base64ToImage(ffxivCardVo.getIconBase64(),
                    WebPathConfig.DEFAULT_FILE_PATH + "/ffxiv/surface_icon/" + ffxivCardVo.getCard().getCardCode() + ".png");
        }
        return ResponseBean.success();
    }

    @GetMapping("/ffxiv/card")
    @NotRequireAuthentication
    public ResponseBean<?> getAllCard() {
        return ResponseBean.success(
                ffxivCardEditService.selectAllCard()
        );
    }

    @DeleteMapping("/ffxiv/card")
    public ResponseBean<?> deleteCard(@RequestBody FfxivCardDTO ffxivCardDTO) {
        FfxivCardDTO result = ffxivCardEditMapper.selectById(ffxivCardDTO.getId());
        ffxivCardEditService.delete(ffxivCardDTO.getId());
        new File("resources/ffxiv/surface/" + result.getCardCode() + ".png").delete();
        new File("resources/ffxiv/surface_icon/" + result.getCardCode() + ".png").delete();
        return ResponseBean.success();
    }


    @PostMapping("/ffxiv/import/cards")
    public ResponseBean<?> ffxivImportLocalDataFiles() {
        File file = new File("D:\\workspace\\rulateday-api\\src\\main\\resources\\import\\ffxiv-9card-record.json");
        String surfacePicturePath = "E:\\github\\src\\ffxiv-9card\\public\\ffxiv\\surface\\";
        String surfaceIconPicturePath = "E:\\github\\src\\ffxiv-9card\\public\\ffxiv\\icon\\";

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] textBytes = fileInputStream.readAllBytes();
            JSONObject jsonObject = JSONObject.parseObject(new String(textBytes));
            jsonObject.forEach((key, value) -> {

                JSONObject cardJSONObject = (JSONObject) value;
                FfxivCardVo ffxivCardVo = new FfxivCardVo();

                ffxivCardVo.setPictureBase64(
                        ImageToBase64.ImageToBase64(surfacePicturePath + cardJSONObject.getString("surface"))
                );
                ffxivCardVo.setIconBase64(
                        ImageToBase64.ImageToBase64(surfaceIconPicturePath + cardJSONObject.getString("icon"))
                );

                FfxivCardDTO ffxivCardDTO = new FfxivCardDTO();
                ffxivCardDTO.setCardNo(key);
                ffxivCardDTO.setName(cardJSONObject.getString("name"));


                ffxivCardDTO.setRarity(Integer.parseInt(cardJSONObject.getString("rarity")));
                ffxivCardDTO.setPatch(cardJSONObject.getString("patch"));

                ffxivCardDTO.setTop(numberHandler(cardJSONObject.getJSONArray("values").getString(0)));
                ffxivCardDTO.setRight(numberHandler(cardJSONObject.getJSONArray("values").getString(1)));
                ffxivCardDTO.setBottom(numberHandler(cardJSONObject.getJSONArray("values").getString(2)));
                ffxivCardDTO.setLeft(numberHandler(cardJSONObject.getJSONArray("values").getString(3)));


                switch (cardJSONObject.getString("org")) {
                    case "兽人":
                        ffxivCardDTO.setType(1);
                        break;
                    case "蛮神":
                        ffxivCardDTO.setType(2);
                        break;
                    case "拂晓":
                        ffxivCardDTO.setType(3);
                        break;
                    case "帝国":
                        ffxivCardDTO.setType(4);
                        break;
                    default:
                        ffxivCardDTO.setType(0);
                }

                ffxivCardVo.setCard(ffxivCardDTO);

                saveCard(ffxivCardVo);
            });
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBean.error(e);
        }

        return ResponseBean.success();
    }

    public int numberHandler(String text) {
        if (text.equals("A")) {
            return 10;
        }
        return Integer.parseInt(text);
    }


}
