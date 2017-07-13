package com.qingdao.marathon.system.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qingdao.marathon.base.Pagination;
import com.qingdao.marathon.system.mapper.OperatorMapper;
import com.qingdao.marathon.system.model.Operator;
import com.qingdao.marathon.system.service.OperatorMngService;

@Service("operatorMngService")
public class OperatorMngServiceImpl implements OperatorMngService{

	@Resource
	OperatorMapper<Operator> operatorMapper;
	
	@Override
	public Operator queryByLoginInfo(String userName, String password) {
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("userName", userName);
		params.put("password", password);
		
		return operatorMapper.selectByLoginInfo(params);
	}

	@Override
	public Page<Operator> queryByList(Pagination pagination, Map<String, Object> parms, boolean flag) {
		PageHelper.startPage(pagination.getCurrentPage(), pagination.getNumPerPage(), flag);
		return operatorMapper.queryByList(parms);
	}

	@Override
	public void delOpt(String optId) {
		
		operatorMapper.delete(optId);
		
		operatorMapper.delOptBindRole(optId);
	}

	@Override
	public void addOpt(Operator operator) {
		
		operatorMapper.add(operator);
	}

	@Override
	public void updateOpt(Operator operator) {
		operatorMapper.update(operator);
	}

	@Override
	public Operator selectBadge(String badge) {
		return operatorMapper.selectBadge(badge);
	}

	@Override
	public void addOptBindRole(Operator operator) {
		operatorMapper.add(operator);
		operatorMapper.addRoleOpt(operator);
	}

	@Override
	public void updateOptBindRole(Operator operator) {
		operatorMapper.updateRoleOpt(operator);
		operatorMapper.update(operator);
	}
}
