package indi.eiriksgata.rulateday.api.service;

import indi.eiriksgata.rulateday.api.exception.CommonBaseException;
import indi.eiriksgata.rulateday.api.pojo.ffxiv.FfxivCardDTO;
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
