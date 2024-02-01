package com.github.eiriksgata.rulateday.platform.mapper;

import com.alibaba.fastjson.JSONObject;
import com.github.eiriksgata.rulateday.platform.pojo.DiceConfigEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface DiceConfigMapper {

    @Select("select id,private_chat,beta_version from dice_config where id = 1")
    JSONObject selectOne();

    @Update("update dice_config set private_chat=#{privateChat} where id= 1")
    void updateByPrivateChat(@Param("privateChat") Integer privateChat);

    @Update("update dice_config set beta_version =#{betaVersion} where id = 1")
    void updateByBetaVersion(@Param("betaVersion") Integer betaVersion);


}