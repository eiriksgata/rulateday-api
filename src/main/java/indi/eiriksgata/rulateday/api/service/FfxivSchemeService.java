package indi.eiriksgata.rulateday.api.service;

import indi.eiriksgata.rulateday.api.exception.CommonBaseException;
import indi.eiriksgata.rulateday.pojo.ffxiv.FfxivCardDTO;
import indi.eiriksgata.rulateday.pojo.ffxiv.FfxivSchemeDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FfxivSchemeService {
    @Transactional(rollbackFor = CommonBaseException.class)
    FfxivSchemeDTO saveFfxivScheme(FfxivSchemeDTO ffxivSchemeDTO);

    @Transactional(rollbackFor = CommonBaseException.class)
    void saveSchemeCards(Integer schemeId, List<Integer> cards);

    @Transactional(rollbackFor = CommonBaseException.class)
    void add(FfxivSchemeDTO ffxivSchemeDTO);

    @Transactional(rollbackFor = CommonBaseException.class)
    void update(FfxivSchemeDTO ffxivSchemeDTO);

    @Transactional(rollbackFor = CommonBaseException.class)
    void deleteScheme(Integer id);

    List<FfxivSchemeDTO> findAllScheme();

    List<FfxivCardDTO> findSchemeCards(Integer schemeId);

    List<FfxivSchemeDTO> findSchemeByNpcId(Integer id);

    FfxivSchemeDTO findById(Integer id);
}
