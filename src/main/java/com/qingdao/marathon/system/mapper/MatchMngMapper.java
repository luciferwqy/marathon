package com.qingdao.marathon.system.mapper;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.qingdao.marathon.base.BaseMapper;
import com.qingdao.marathon.system.model.AuthInfo;
import com.qingdao.marathon.system.model.MatchEntity;

/**
 * 
 * @author wqy
 * @datetime 2016年7月27日
 * @func
 */
public interface MatchMngMapper<T> extends BaseMapper<T> {

	/**
	 * 根据登陆信息查詢功能列表
	 * 
	 * @param params
	 */
	List<AuthInfo> queryFuncByOptId(String id);
	
	List<AuthInfo> queryAllFunc();
	
	public Page<T> queryMatch(Map<String,Object> parms);
	
	public List<MatchEntity> queryByParams(Map<String,Object> parms);
	
	/**
	 * 根据Id删除Match
	 * @param matchId
	 */
	public void delMatch(String matchId);
	
	/**
	 * 根据父Id删除子节点
	 * @param matchId
	 */
	public void delMatchByParentId(String matchId);

	/**
	 * 根据roleid查权限
	 * @param roleid
	 * @return
	 */
	List<AuthInfo> queryFuncByRoleId(String roleid);
	
	/**
	 * 根据matchId查询
	 * @param matchId
	 * @return
	 */
	MatchEntity queryById(String matchId);
	
}
