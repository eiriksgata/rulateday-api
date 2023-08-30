package indi.eiriksgata.rulateday.api.service.impl;

import indi.eiriksgata.rulateday.api.exception.CommonBaseException;
import indi.eiriksgata.rulateday.api.service.FfxivCardEditService;
import indi.eiriksgata.rulateday.api.mapper.FfxivCardEditMapper;
import indi.eiriksgata.rulateday.api.pojo.ffxiv.FfxivCardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class FfxivCardEditServiceImpl implements FfxivCardEditService {

    @Autowired
    FfxivCardEditMapper ffxivCardEditMapper;

    @Transactional(rollbackFor = CommonBaseException.class)
    @Override
    public FfxivCardDTO save(FfxivCardDTO ffxivCardDTO) {
        if (ffxivCardDTO.getId() == null || ffxivCardDTO.getId() == -1) {
            return add(ffxivCardDTO);
        }
        FfxivCardDTO result = ffxivCardEditMapper.selectById(ffxivCardDTO.getId());
        if (result == null) {
            return add(ffxivCardDTO);
        }
        update(ffxivCardDTO);
        return ffxivCardDTO;
    }


    @Override
    public List<FfxivCardDTO> selectAllCard() {
        return ffxivCardEditMapper.selectAll();
    }


    @Transactional(rollbackFor = CommonBaseException.class)
    @Override
    public FfxivCardDTO add(FfxivCardDTO ffxivCardDTO) {
        ffxivCardDTO.setId(null);
        ffxivCardDTO.setCreatedAt(new Date());
        ffxivCardDTO.setUpdatedAt(new Date());
        ffxivCardDTO.setCardCode(UUID.randomUUID().toString().replaceAll("-", ""));
        ffxivCardEditMapper.insert(ffxivCardDTO);
        return ffxivCardDTO;
    }

    @Transactional(rollbackFor = CommonBaseException.class)
    @Override
    public void delete(int id) {
        ffxivCardEditMapper.deleteById(id);
    }

    @Transactional(rollbackFor = CommonBaseException.class)
    @Override
    public void update(FfxivCardDTO ffxivCardDTO) {
        FfxivCardDTO result = ffxivCardEditMapper.selectById(ffxivCardDTO.getId());
        if (result == null) {
            return;
        }
        ffxivCardDTO.setId(result.getId());
        ffxivCardDTO.setCreatedAt(result.getCreatedAt());
        ffxivCardDTO.setUpdatedAt(new Date());
        ffxivCardEditMapper.updateById(ffxivCardDTO);
    }


}
