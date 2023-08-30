package indi.eiriksgata.rulateday.api.service.impl;

import indi.eiriksgata.rulateday.api.exception.CommonBaseException;
import indi.eiriksgata.rulateday.api.service.FfxivSchemeNodeService;
import indi.eiriksgata.rulateday.api.service.FfxivSchemeService;
import indi.eiriksgata.rulateday.api.mapper.FfxivSchemeMapper;
import indi.eiriksgata.rulateday.api.mapper.FfxivSchemeRelCardMapper;
import indi.eiriksgata.rulateday.api.pojo.ffxiv.FfxivCardDTO;
import indi.eiriksgata.rulateday.api.pojo.ffxiv.FfxivSchemeDTO;
import indi.eiriksgata.rulateday.api.pojo.ffxiv.FfxivSchemeRelCardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class FfxivSchemeServiceImpl implements FfxivSchemeService {

    @Autowired
    FfxivSchemeMapper ffxivSchemeMapper;

    @Autowired
    FfxivSchemeRelCardMapper ffxivSchemeRelCardMapper;

    @Autowired
    FfxivSchemeNodeService ffxivSchemeNodeService;

    @Transactional(rollbackFor = CommonBaseException.class)
    @Override
    public FfxivSchemeDTO saveFfxivScheme(FfxivSchemeDTO ffxivSchemeDTO) {
        if (ffxivSchemeDTO.getId() == null || ffxivSchemeDTO.getId() == -1) {
            ffxivSchemeDTO.setCode(UUID.randomUUID().toString().replaceAll("-", ""));
            ffxivSchemeDTO.setId(null);
            ffxivSchemeDTO.setCreatedAt(new Date());
            ffxivSchemeDTO.setUpdatedAt(new Date());
            add(ffxivSchemeDTO);
            return ffxivSchemeDTO;
        }
        FfxivSchemeDTO result = ffxivSchemeMapper.selectById(ffxivSchemeDTO.getId());
        if (result == null) {
            ffxivSchemeDTO.setCode(UUID.randomUUID().toString().replaceAll("-", ""));
            ffxivSchemeDTO.setId(null);
            ffxivSchemeDTO.setCreatedAt(new Date());
            ffxivSchemeDTO.setUpdatedAt(new Date());
            add(ffxivSchemeDTO);
        } else {
            ffxivSchemeDTO.setCreatedAt(result.getCreatedAt());
            ffxivSchemeDTO.setUpdatedAt(result.getUpdatedAt());
            ffxivSchemeDTO.setCode(result.getCode());
            update(ffxivSchemeDTO);
        }
        return ffxivSchemeDTO;
    }

    @Transactional(rollbackFor = CommonBaseException.class)
    @Override
    public void saveSchemeCards(Integer schemeId, List<Integer> cards) {
        ffxivSchemeRelCardMapper.deleteBySchemeId(schemeId);
        for (Integer cardId : cards) {
            FfxivSchemeRelCardDTO relCardDTO = new FfxivSchemeRelCardDTO();
            relCardDTO.setCardId(cardId);
            relCardDTO.setSchemeId(schemeId);
            relCardDTO.setCreatedAt(new Date());
            relCardDTO.setUpdatedAt(new Date());
            ffxivSchemeRelCardMapper.insert(relCardDTO);
        }
    }


    @Transactional(rollbackFor = CommonBaseException.class)
    @Override
    public void add(FfxivSchemeDTO ffxivSchemeDTO) {
        ffxivSchemeMapper.insert(ffxivSchemeDTO);

        //每次新增都需要 增加 先手 和 后手节点
        ffxivSchemeNodeService.ffxivNineCardSchemeInitNode(ffxivSchemeDTO.getId());


    }


    @Transactional(rollbackFor = CommonBaseException.class)
    @Override
    public void update(FfxivSchemeDTO ffxivSchemeDTO) {
        ffxivSchemeMapper.updateById(ffxivSchemeDTO);
    }

    @Transactional(rollbackFor = CommonBaseException.class)
    @Override
    public void deleteScheme(Integer id) {
        //删除案例需要删除案例对应的节点数据
        ffxivSchemeNodeService.deleteNodeBySchemeId(id);

        ffxivSchemeMapper.deleteById(id);
    }

    @Override
    public List<FfxivSchemeDTO> findAllScheme() {
        return ffxivSchemeMapper.selectAll();
    }

    @Override
    public List<FfxivCardDTO> findSchemeCards(Integer schemeId) {
        return ffxivSchemeMapper.selectCardsById(schemeId);
    }

    @Override
    public List<FfxivSchemeDTO> findSchemeByNpcId(Integer id) {
        return ffxivSchemeMapper.selectByCharacterId(id);
    }


    @Override
    public FfxivSchemeDTO findById(Integer id) {
        return ffxivSchemeMapper.selectById(id);
    }


}
