package com.github.eiriksgata.rulateday.platform.vo.ffxiv;

import com.github.eiriksgata.rulateday.platform.pojo.ffxiv.FfxivCardDTO;
import lombok.Data;

@Data
public class FfxivCardVo {

    private FfxivCardDTO card;
    private String pictureBase64;
    private String iconBase64;


}
