package com.github.eiriksgata.rulateday.platform.service;

import com.github.eiriksgata.rulateday.platform.exception.CommonBaseException;
import com.github.eiriksgata.rulateday.platform.pojo.ffxiv.FfxivSchemeNodeDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FfxivSchemeNodeService {
    @Transactional(rollbackFor = CommonBaseException.class)
    void ffxivNineCardSchemeInitNode(Integer schemeId);

    @Transactional(rollbackFor = CommonBaseException.class)
    void addNode(FfxivSchemeNodeDTO ffxivSchemeNodeDTO);

    @Transactional(rollbackFor = CommonBaseException.class)
    void deleteNode(Integer nodeId);

    @Transactional(rollbackFor = CommonBaseException.class)
    void deleteNodeBySchemeId(Integer schemeId);

    List<FfxivSchemeNodeDTO> findSchemeNodeBySchemeId(Integer schemeId);
}
