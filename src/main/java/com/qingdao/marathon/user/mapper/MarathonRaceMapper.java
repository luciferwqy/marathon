package com.qingdao.marathon.user.mapper;

import com.qingdao.marathon.user.model.MarathonRace;

public interface MarathonRaceMapper {
    int deleteByPrimaryKey(String account);

    int insert(MarathonRace record);

    int insertSelective(MarathonRace record);

    MarathonRace selectByPrimaryKey(String account);

    int updateByPrimaryKeySelective(MarathonRace record);

    int updateByPrimaryKey(MarathonRace record);
    
    void addRace(MarathonRace record);
}