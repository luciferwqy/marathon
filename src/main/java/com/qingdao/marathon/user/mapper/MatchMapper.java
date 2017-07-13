package com.qingdao.marathon.user.mapper;

import java.util.List;
import java.util.Map;

import com.qingdao.marathon.user.model.MatchInfo;

public interface MatchMapper {

    MatchInfo selectByPrimaryKey(Integer matchid);

    List<MatchInfo> queryList(Map<String,Object> parms);
}