package com.github.eiriksgata.rulateday.dice.service.impl;

import com.github.eiriksgata.rulateday.RulatedayCore;
import com.github.eiriksgata.rulateday.event.EventAdapter;
import com.github.eiriksgata.rulateday.event.EventUtils;
import com.github.eiriksgata.rulateday.mapper.Dnd5ePhbDataMapper;
import com.github.eiriksgata.rulateday.pojo.QueryDataBase;
import com.github.eiriksgata.rulateday.service.Dnd5eLibService;
import com.github.eiriksgata.rulateday.utlis.FileUtil;
import com.github.eiriksgata.rulateday.utlis.MyBatisUtil;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.GroupTempMessageEvent;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * author: create by Keith
 * version: v1.0
 * description: com.github.eiriksgata.rulateday.service.impl
 * date: 2020/11/13
 **/
public class Dnd5eLibServiceImpl implements Dnd5eLibService {

    private final String imagesUrl = ResourceBundle.getBundle("resources").getString("resources.mm.images.url");
    private final String localPath = RulatedayCore.INSTANCE.getDataFolderPath() + ResourceBundle.getBundle("resources").getString("resources.mm.images.path");


    @Override
    public List<QueryDataBase> findName(String name) {
        List<QueryDataBase> result = new ArrayList<>();
        Dnd5ePhbDataMapper mapper = MyBatisUtil.getSqlSession().getMapper(Dnd5ePhbDataMapper.class);
        result.addAll(mapper.selectSkillPhb(name));
        result.addAll(mapper.selectArmorWeapon(name));
        result.addAll(mapper.selectClasses(name));
        result.addAll(mapper.selectFeat(name));
        result.addAll(mapper.selectRaces(name));
        result.addAll(mapper.selectRule(name));
        result.addAll(mapper.selectTools(name));
        result.addAll(mapper.selectSpellList(name));
        result.addAll(mapper.selectMagicItemsDmg(name));
        result.addAll(mapper.selectRuleDmg(name));
        result.addAll(mapper.selectMM(name));
        result.addAll(mapper.selectBackgroundPhb(name));
        result.addAll(mapper.selectEgtw(name));
        result.addAll(mapper.selectBaseModule(name));

        return result;
    }

    @Override
    public QueryDataBase findById(long id) {
        Dnd5ePhbDataMapper mapper = MyBatisUtil.getSqlSession().getMapper(Dnd5ePhbDataMapper.class);
        return mapper.selectSkillPhbById(id);
    }


    @Override
    public QueryDataBase getRandomMMData() {
        Dnd5ePhbDataMapper mapper = MyBatisUtil.getSqlSession().getMapper(Dnd5ePhbDataMapper.class);
        MyBatisUtil.getSqlSession().clearCache();
        return mapper.selectRandomMM();
    }


    @Override
    public void sendMMImage(Object event, QueryDataBase result) {
        String mmNameFileName = result.getName().substring(5) + ".png";
        String mmName = result.getName().substring(5) + ".png";
        mmNameFileName = URLEncoder.encode(mmNameFileName, StandardCharsets.UTF_8);
        String url = imagesUrl + mmNameFileName;
        File imageFile = new File(localPath + mmName);
        if (!imageFile.exists()) {
            try {
                FileUtil.downLoadFromUrl(url, localPath + mmName);
            } catch (Exception e) {
                RulatedayCore.INSTANCE.getLogger().info("下载" + result.getName().substring(5) + "图片失败，服务器可能没有该资源");
            }
        }


        // TODO: 需要对不同的事件进行处理，如果是属于主动推送额外的数据的话
        if (imageFile.exists()) {
            EventUtils.eventCallback(event, new EventAdapter() {

                @Override
                public void group(GroupMessageEvent groupMessageEvent) {
                    groupMessageEvent.getGroup().sendMessage(
                            groupMessageEvent.getGroup().uploadImage(
                                    ExternalResource.create(imageFile)
                            )
                    );
                }

                @Override
                public void friend(FriendMessageEvent friendMessageEvent) {
                    friendMessageEvent.getSender().sendMessage(friendMessageEvent.getSender()
                            .uploadImage(ExternalResource.create(imageFile)));
                }


                @Override
                public void groupTemp(GroupTempMessageEvent event) {
                    event.getSender().sendMessage(
                            event.getSender().uploadImage(
                                    ExternalResource.create(imageFile)
                            )
                    );
                }
            });
        }
    }




}
