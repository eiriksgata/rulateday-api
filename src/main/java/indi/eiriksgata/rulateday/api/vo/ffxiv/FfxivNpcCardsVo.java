package indi.eiriksgata.rulateday.api.vo.ffxiv;

import lombok.Data;

import java.util.List;

@Data
public class FfxivNpcCardsVo {

    private Integer id;
    private List<Integer> cards;


}
