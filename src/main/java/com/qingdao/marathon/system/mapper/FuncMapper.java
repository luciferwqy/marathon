package com.qingdao.marathon.system.mapper;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.qingdao.marathon.base.BaseMapper;
import com.qingdao.marathon.system.model.AuthInfo;
import com.qingdao.marathon.system.model.FuncEntity;

/**
 * 
 * @author wqy
 * @datetime 2016年7月27日
 * @func
 */
public interface FuncMapper<T> extends BaseMapper<T> {

	/**
	 * 根据登陆信息查詢功能列表
	 * 
	 * @param params
	 */
	List<AuthInfo> queryFuncByOptId(String id);
	
	List<AuthInfo> queryAllFunc();
	
	public Page<T> queryFunc(Map<String,Object> parms);
	
	public FuncEntity queryByParams(Map<String,Object> parms);
	
	/**
	 * 根据Id删除func
	 * @param funcId
	 */
	public void delFunc(String funcId);
	
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
	 * 根据funcid查询
	 * @param funcid
	 * @return
	 */
	FuncEntity queryById(String funcid);
	
}
