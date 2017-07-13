package com.qingdao.marathon.system.service;

import java.util.Map;

import com.github.pagehelper.Page;
import com.qingdao.marathon.base.Pagination;
import com.qingdao.marathon.system.model.Operator;

/**
 * 
 * @author 賀斌
 * @datetime 2016年7月27日
 * @func
 */
public interface OperatorMngService {

	/**
	 * 
	 * @param userName
	 * @param pwd
	 * @return
	 */
	Operator queryByLoginInfo(String userName, String pwd);
	
	Page<Operator> queryByList(Pagination pagination,Map<String,Object>parms,boolean flag);
	
	/**
	 * 根据Id删除
	 * @param optId
	 */
	public void delOpt(String optId);
	
	/**
	 * 新增员工
	 * @param operator
	 */
	public void addOpt(Operator operator);
	
	/**
	 * 更新员工信息
	 * @param operator
	 */
	public void updateOpt(Operator operator);
	
	/**
	 * 根据badge查询
	 * @param badge
	 * @return
	 */
	Operator selectBadge(String badge);
	
	/**
	 * 新增员工和角色绑定关系
	 * @param operator
	 */
	public void addOptBindRole(Operator operator);
	
	/**
	 * 更新员工和角色绑定关系
	 * @param operator
	 */
	public void updateOptBindRole(Operator operator);
	
}
