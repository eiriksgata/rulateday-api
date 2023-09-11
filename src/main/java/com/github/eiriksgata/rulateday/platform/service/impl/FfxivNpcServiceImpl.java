package com.github.eiriksgata.rulateday.platform.service.impl;

import com.github.eiriksgata.rulateday.platform.exception.CommonBaseException;
import com.github.eiriksgata.rulateday.platform.service.FfxivNpcService;
import com.github.eiriksgata.rulateday.platform.mapper.FfxivNpcMapper;
import com.github.eiriksgata.rulateday.platform.mapper.FfxivNpcRelCardMapper;
import com.github.eiriksgata.rulateday.platform.pojo.ffxiv.FfxivCardDTO;
import com.github.eiriksgata.rulateday.platform.pojo.ffxiv.FfxivNpcDTO;
import com.github.eiriksgata.rulateday.platform.pojo.ffxiv.FfxivNpcRelCardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class FfxivNpcServiceImpl implements FfxivNpcService {

    @Autowired
    FfxivNpcMapper ffxivNpcMapper;

    @Autowired
    FfxivNpcRelCardMapper ffxivNpcRelCardMapper;


    @Transactional(rollbackFor = CommonBaseException.class)
    @Override
    public FfxivNpcDTO save(FfxivNpcDTO ffxivNpcDTO) {
        if (ffxivNpcDTO.getId() == null || ffxivNpcDTO.getId() == -1) {
            add(ffxivNpcDTO);
        }
        FfxivNpcDTO result = ffxivNpcMapper.selectById(ffxivNpcDTO.getId());
        if (result == null) {
            add(ffxivNpcDTO);
            return ffxivNpcDTO;
        } else {
            result.setName(ffxivNpcDTO.getName());
            result.setDescribe(ffxivNpcDTO.getDescribe());
            result.setPosition(ffxivNpcDTO.getPosition());
            update(result);
            return result;
        }
    }

    @Override
    public FfxivNpcDTO selectById(Integer id) {
        return ffxivNpcMapper.selectById(id);
    }

    @Transactional(rollbackFor = CommonBaseException.class)
    public FfxivNpcDTO add(FfxivNpcDTO ffxivNpcDTO) {
        ffxivNpcDTO.setCreatedAt(new Date());
        ffxivNpcDTO.setUpdatedAt(new Date());
        ffxivNpcDTO.setId(null);
        ffxivNpcDTO.setCode(UUID.randomUUID().toString().replaceAll("-", ""));
        ffxivNpcMapper.insert(ffxivNpcDTO);
        return ffxivNpcDTO;
    }

    @Transactional(rollbackFor = CommonBaseException.class)
    @Override
    public void delete(Integer id) {
        //需要先删除该角色的卡牌关系
        ffxivNpcRelCardMapper.deleteByCharacterId(id);

        //再删除角色数据
        ffxivNpcMapper.deleteById(id);
    }

    @Transactional(rollbackFor = CommonBaseException.class)
    public FfxivNpcDTO update(FfxivNpcDTO ffxivNpcDTO) {
        ffxivNpcMapper.updateById(ffxivNpcDTO);
        return ffxivNpcDTO;
    }

    @Override
    public List<FfxivNpcDTO> selectAll() {
        return ffxivNpcMapper.selectAll();
    }

    @Transactional(rollbackFor = CommonBaseException.class)
    @Override
    public void saveCardRel(Integer id, List<Integer> cardsId) {
        deleteAllCardsByNpcId(id);
        for (Integer cardId : cardsId) {
            FfxivNpcRelCardDTO relCardDTO = new FfxivNpcRelCardDTO();
            relCardDTO.setCreatedAt(new Date());
            relCardDTO.setUpdatedAt(new Date());
            relCardDTO.setCharacterId(id);
            relCardDTO.setCardId(cardId);
            ffxivNpcRelCardMapper.insert(relCardDTO);
        }
    }

    @Override
    public List<FfxivCardDTO> findNpcCards(Integer id) {
        return ffxivNpcMapper.selectCardsById(id);
    }

    @Transactional(rollbackFor = CommonBaseException.class)
    @Override
    public void deleteAllCardsByNpcId(Integer id) {
        ffxivNpcRelCardMapper.deleteByCharacterId(id);
    }


}
