package com.qingdao.marathon.user.mapper;

import com.qingdao.marathon.user.model.PersonalInfo;

public interface PersonalInfoMapper {
    int deleteByPrimaryKey(String account);

    int insert(PersonalInfo record);

    int insertSelective(PersonalInfo record);

    PersonalInfo queryByPersonal(PersonalInfo record);
    
    int updateByPrimaryKeySelective(PersonalInfo record);

}