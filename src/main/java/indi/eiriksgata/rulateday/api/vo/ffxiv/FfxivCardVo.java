package indi.eiriksgata.rulateday.api.vo.ffxiv;

import indi.eiriksgata.rulateday.api.pojo.ffxiv.FfxivCardDTO;
import lombok.Data;

@Data
public class FfxivCardVo {

    private FfxivCardDTO card;
    private String pictureBase64;
    private String iconBase64;


}
