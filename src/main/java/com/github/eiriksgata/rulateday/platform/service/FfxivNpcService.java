package com.github.eiriksgata.rulateday.platform.service;

import com.github.eiriksgata.rulateday.platform.exception.CommonBaseException;
import com.github.eiriksgata.rulateday.platform.pojo.ffxiv.FfxivCardDTO;
import com.github.eiriksgata.rulateday.platform.pojo.ffxiv.FfxivNpcDTO;
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
