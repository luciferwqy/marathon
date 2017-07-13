package com.qingdao.marathon.user.mapper;

import java.util.List;

import com.qingdao.marathon.user.model.MatchGroup;

public interface MatchGroupMapper {

    List<MatchGroup> queryByMatchId(String matchId);
}