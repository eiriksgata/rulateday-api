package indi.eiriksgata.rulateday.api.service.impl;

import indi.eiriksgata.rulateday.api.exception.CommonBaseException;
import indi.eiriksgata.rulateday.api.exception.CommonBaseExceptionEnum;
import indi.eiriksgata.rulateday.api.service.FfxivSchemeNodeService;
import indi.eiriksgata.rulateday.mapper.FfxivSchemeNodeMapper;
import indi.eiriksgata.rulateday.pojo.ffxiv.FfxivSchemeNodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
public class FfxivSchemeNodeServiceImpl implements FfxivSchemeNodeService {

    @Autowired
    FfxivSchemeNodeMapper ffxivSchemeNodeMapper;

    @Transactional(rollbackFor = CommonBaseException.class)
    @Override
    public void ffxivNineCardSchemeInitNode(Integer schemeId) {
        //初始化 先手 后手节点
        FfxivSchemeNodeDTO offensive = new FfxivSchemeNodeDTO();
        offensive.setName("先手");
        offensive.setFirst(1);
        offensive.setSchemeId(schemeId);

        FfxivSchemeNodeDTO defensive = new FfxivSchemeNodeDTO();
        defensive.setName("后手");
        defensive.setFirst(0);
        defensive.setSchemeId(schemeId);


        ffxivSchemeNodeMapper.insert(offensive);
        ffxivSchemeNodeMapper.insert(defensive);
    }

    @Transactional(rollbackFor = CommonBaseException.class)
    @Override
    public void addNode(FfxivSchemeNodeDTO ffxivSchemeNodeDTO) {

        //需要查询该节点的父节点是否存在
        FfxivSchemeNodeDTO result = ffxivSchemeNodeMapper.selectById(ffxivSchemeNodeDTO.getParentId());
        if (result == null) {
            throw new CommonBaseException(CommonBaseExceptionEnum.FFXIV_SCHEME_NODE_NOT_FOUNT_PARENT_ID);
        }

        ffxivSchemeNodeDTO.setFirst(-1);
        ffxivSchemeNodeDTO.setId(null);
        ffxivSchemeNodeDTO.setUpdatedAt(new Date());
        ffxivSchemeNodeDTO.setCreatedAt(new Date());
        ffxivSchemeNodeMapper.insert(ffxivSchemeNodeDTO);
    }

    @Transactional(rollbackFor = CommonBaseException.class)
    @Override
    public void deleteNode(Integer nodeId) {
        //删除节点还需要额外删除子节点
        List<FfxivSchemeNodeDTO> list = ffxivSchemeNodeMapper.selectNodeThreeByNodeId(nodeId);
        for (FfxivSchemeNodeDTO temp : list) {
            ffxivSchemeNodeMapper.deleteById(temp.getId());
        }
        ffxivSchemeNodeMapper.deleteById(nodeId);
    }

    @Transactional(rollbackFor = CommonBaseException.class)
    @Override
    public void deleteNodeBySchemeId(Integer schemeId) {
        ffxivSchemeNodeMapper.deleteBySchemeId(schemeId);
    }

    @Override
    public List<FfxivSchemeNodeDTO> findSchemeNodeBySchemeId(Integer schemeId) {
        return ffxivSchemeNodeMapper.selectBySchemeId(schemeId);
    }

}
