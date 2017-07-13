package com.qingdao.marathon.system.mapper;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.qingdao.marathon.base.BaseMapper;
import com.qingdao.marathon.system.model.AchievementEntity;
import com.qingdao.marathon.system.model.AuthInfo;
import com.qingdao.marathon.system.model.FuncEntity;
import com.qingdao.marathon.system.model.MatchGroupEntity;

/**
 * 
 * @author wqy
 * @datetime 2016年7月27日
 * @func
 */
public interface MatchGroupMngMapper<T> extends BaseMapper<T> {

	/**
	 * 根据登陆信息查詢功能列表
	 * 
	 * @param params
	 */
	List<AuthInfo> queryFuncByOptId(String id);
	
	List<AuthInfo> queryAllFunc();
	
	public Page<T> queryMatchGroup(Map<String,Object> parms);
	
	public MatchGroupEntity queryByParams(Map<String,Object> parms);
	
	public List<MatchGroupEntity> queryGroupByParams(Map<String,Object> parms);
	
	public List<AchievementEntity> queryAchByParams(Map<String,Object> parms);
	
	/**
	 * 根据Id删除group
	 * @param groupId
	 */
	public void delGroup(String groupId);
	
	public void updateById(String groupId);
	
	/**
	 * 根据父Id删除子节点
	 * @param funcId
	 */
	public void delFuncByParentId(String funcId);

	/**
	 * 根据roleid查权限
	 * @param roleid
	 * @return
	 */
	List<AuthInfo> queryFuncByRoleId(String roleid);
	
	/**
	 * 根据groupId查询
	 * @param groupId
	 * @return
	 */
	MatchGroupEntity queryById(String groupId);
	
}
