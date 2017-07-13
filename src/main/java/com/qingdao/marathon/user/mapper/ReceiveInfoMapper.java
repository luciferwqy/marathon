package com.qingdao.marathon.user.mapper;

import java.util.List;

import com.qingdao.marathon.user.model.ReceiveInfo;

public interface ReceiveInfoMapper {
	
	public void addReceiveInfo(ReceiveInfo info);
	
	public void delete(String receiveId);
	
	public List<ReceiveInfo> queryByList(String account); 
}