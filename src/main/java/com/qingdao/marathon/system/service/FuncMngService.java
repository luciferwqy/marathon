package com.qingdao.marathon.system.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.qingdao.marathon.base.Pagination;
import com.qingdao.marathon.system.model.AuthInfo;
import com.qingdao.marathon.system.model.FuncEntity;

/**
 * 
 * @author wqy
 * @datetime 2016年7月27日
 * @func 功能模块管理
 */
public interface FuncMngService {

	/**
	 * 根据操作员信息进行功能控制
	 * 
	 * @param id
	 * @return
	 */
	List<AuthInfo> queryFuncByOptId(String id);
	
	/**
	 * 查询主功能模块
	 * @param pagination
	 * @return
	 */
	public Page<FuncEntity> queryFunc(Pagination pagination,Map<String,Object> parms,boolean flag);
	
	/**
	 * 根据条件查询功能实体
	 * @param parms
	 * @return
	 */
	public FuncEntity queryByParams(Map<String,Object> parms);
	
	/**
	 * 删除主功能及其子节点
	 * @param FuncId
	 */
	public void delMainFunc(String FuncId);
	
	/**
	 * 根据funcEntity 更新
	 * @param funcEntity
	 */
	public void updateFunc(FuncEntity funcEntity);
	
	/**
	 * 新增功能
	 * @param funcEntity
	 */
	public void addFunc(FuncEntity funcEntity);
	
	/**
	 * 查询所有func用于显示权限树形结构
	 * @return
	 */
	List<AuthInfo> queryAllFunc();
	
	/**
	 * 封装成树形所需要的数据
	 * @param authInfos
	 * @return
	 */
	public List<AuthInfo> treeAuthInfo(List<AuthInfo> authInfos);

	/**
	 * 根据roleid查权限
	 * @param roleid
	 * @return
	 */
	List<AuthInfo> queryFuncByRoleId(String roleid);
	
	/**
	 * 根据funcid查询
	 * @param funcid
	 * @return
	 */
	FuncEntity queryById(String funcid);

}
