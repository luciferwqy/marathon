package com.qingdao.marathon.system.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.qingdao.marathon.base.Pagination;
import com.qingdao.marathon.system.model.AchievementEntity;
import com.qingdao.marathon.system.model.MatchEntity;
import com.qingdao.marathon.system.model.MatchGroupEntity;

/**
 * 
 * @author wqy
 * @datetime 2016年7月27日
 * @func 功能模块管理
 */
public interface MatchGroupMngService {

	/**
	 * 根据操作员信息进行功能控制
	 * 
	 * @param id
	 * @return
	 */
//	List<AuthInfo> queryFuncByOptId(String id);
	
	/**
	 * 查询主功能模块
	 * @param pagination
	 * @return
	 */
	public Page<MatchGroupEntity> queryMatchGroup(Pagination pagination,Map<String,Object> parms,boolean flag);
	
	/**
	 * 根据条件查询功能实体
	 * @param parms
	 * @return
	 */
	public List<MatchEntity> queryByParams(Map<String,Object> parms);
	/**
	 * 根据条件查询功能实体
	 * @param parms
	 * @return
	 */
	public List<AchievementEntity> queryAchByParams(Map<String,Object> parms);
	
	/**
	 * 根据条件查询功能实体
	 * @param parms
	 * @return
	 */
	public List<MatchGroupEntity> queryGroupByParams(Map<String,Object> parms);
	
	/**
	 * 删除主功能及其子节点
	 * @param FuncId
	 */
	public void delMainFunc(String groupId);
	
	/**
	 * 根据matchGroupEntity 更新
	 * @param matchGroupEntity
	 */
	public void updateMatchGroup(MatchGroupEntity matchGroupEntity);
	
	/**
	 * 新增功能
	 * @param matchEntity
	 */
	public void addMatchGroup(MatchGroupEntity matchGroupEntity);
//	
//	/**
//	 * 查询所有func用于显示权限树形结构
//	 * @return
//	 */
//	List<AuthInfo> queryAllFunc();
//	
//	/**
//	 * 封装成树形所需要的数据
//	 * @param authInfos
//	 * @return
//	 */
//	public List<AuthInfo> treeAuthInfo(List<AuthInfo> authInfos);
//
//	/**
//	 * 根据roleid查权限
//	 * @param roleid
//	 * @return
//	 */
//	List<AuthInfo> queryFuncByRoleId(String roleid);
	
	/**
	 * 根据funcid查询
	 * @param funcid
	 * @return
	 */
	MatchGroupEntity queryById(String groupId);

}
