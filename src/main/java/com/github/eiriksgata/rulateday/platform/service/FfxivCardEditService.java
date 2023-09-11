package com.github.eiriksgata.rulateday.platform.service;

import com.github.eiriksgata.rulateday.platform.exception.CommonBaseException;
import com.github.eiriksgata.rulateday.platform.pojo.ffxiv.FfxivCardDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FfxivCardEditService {
    @Transactional(rollbackFor = CommonBaseException.class)
    FfxivCardDTO save(FfxivCardDTO ffxivCardDTO);

    List<FfxivCardDTO> selectAllCard();

    @Transactional(rollbackFor = CommonBaseException.class)
    FfxivCardDTO add(FfxivCardDTO ffxivCardDTO);

    @Transactional(rollbackFor = CommonBaseException.class)
    void delete(int id);

    @Transactional(rollbackFor = CommonBaseException.class)
    void update(FfxivCardDTO ffxivCardDTO);
}
