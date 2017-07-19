package com.qingdao.marathon.system.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.qingdao.marathon.base.Pagination;
import com.qingdao.marathon.system.model.AchievementEntity;
import com.qingdao.marathon.system.model.DrawEntity;
import com.qingdao.marathon.system.model.MatchEntity;
import com.qingdao.marathon.system.model.MatchGroupEntity;
import com.qingdao.marathon.system.model.ParticipantEntity;
import com.qingdao.marathon.user.model.Registration;

/**
 * 
 * @author wqy
 * @datetime 2016年7月27日
 * @func 功能模块管理
 */
public interface ReviewMngService {

	
	/**
	 * 查询主功能模块
	 * @param pagination
	 * @return
	 */
	public Page<DrawEntity> queryDraw(Pagination pagination,Map<String,Object> parms,boolean flag);
	
	/**
	 * 查询主功能模块
	 * @param pagination
	 * @return
	 */
	public Page<ParticipantEntity> queryParticipant(Pagination pagination,Map<String,Object> parms,boolean flag);
	
	/**
	 * 查询主功能模块
	 * @param Map<String,Object>
	 * @return
	 */
	public List<ParticipantEntity> queryParticipantForExport(Map<String,Object> parms);
	
//	/**
//	 * 根据条件查询功能实体
//	 * @param parms
//	 * @return
//	 */
//	public List<MatchEntity> queryByParams(Map<String,Object> parms);
//	
//	/**
//	 * 根据条件查询功能实体
//	 * @param parms
//	 * @return
//	 */
//	public List<MatchGroupEntity> queryGroupByParams(Map<String,Object> parms);
//	
//	/**
//	 * 删除主功能及其子节点
//	 * @param FuncId
//	 */
//	public void delMainFunc(String groupId);
	
	/**
	 * 根据registration 更新
	 * @param registration
	 */
	public void updateRegistrationMatchGroup(List<Registration> registration,String groupId);
//	
//	/**
//	 * 新增功能
//	 * @param matchEntity
//	 */
//	public void addMatchGroup(MatchGroupEntity matchGroupEntity);
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
	 * 根据groupId查询
	 * @param groupId
	 * @return
	 */
	List<Registration> queryById(String groupId);
	
	/**
	 * 根据registration 更新
	 * @param registration
	 */
	public void updateRegistration(List<AchievementEntity> achievementEntity);
}
