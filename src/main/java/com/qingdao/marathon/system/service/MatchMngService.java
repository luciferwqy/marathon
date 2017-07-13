package com.qingdao.marathon.system.service;

import java.util.Map;

import com.github.pagehelper.Page;
import com.qingdao.marathon.base.Pagination;
import com.qingdao.marathon.system.model.MatchEntity;

/**
 * 
 * @author wqy
 * @datetime 2016年7月27日
 * @func 功能模块管理
 */
public interface MatchMngService {

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
	public Page<MatchEntity> queryMatch(Pagination pagination,Map<String,Object> parms,boolean flag);
	
	/**
	 * 根据条件查询功能实体
	 * @param parms
	 * @return
	 */
//	public FuncEntity queryByParams(Map<String,Object> parms);
	
	/**
	 * 删除主功能及其子节点
	 * @param matchId
	 */
	public void delMainFunc(String matchId);
	
	/**
	 * 根据matchEntity 更新
	 * @param matchEntity
	 */
	public void updateMatch(MatchEntity matchEntity);
	
	/**
	 * 新增功能
	 * @param matchEntity
	 */
	public void addMatch(MatchEntity matchEntity);
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
	 * 根据matchId查询
	 * @param matchId
	 * @return
	 */
	MatchEntity queryById(String matchId);

}
