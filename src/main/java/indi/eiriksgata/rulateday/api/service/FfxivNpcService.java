package indi.eiriksgata.rulateday.api.service;

import indi.eiriksgata.rulateday.api.exception.CommonBaseException;
import indi.eiriksgata.rulateday.pojo.ffxiv.FfxivCardDTO;
import indi.eiriksgata.rulateday.pojo.ffxiv.FfxivNpcDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FfxivNpcService {
    @Transactional(rollbackFor = CommonBaseException.class)
    FfxivNpcDTO save(FfxivNpcDTO ffxivNpcDTO);

    FfxivNpcDTO selectById(Integer id);

    @Transactional(rollbackFor = CommonBaseException.class)
    void delete(Integer id);

    List<FfxivNpcDTO> selectAll();

    @Transactional(rollbackFor = CommonBaseException.class)
    void saveCardRel(Integer id, List<Integer> cardsId);

    List<FfxivCardDTO> findNpcCards(Integer id);

    @Transactional(rollbackFor = CommonBaseException.class)
    void deleteAllCardsByNpcId(Integer id);
}
