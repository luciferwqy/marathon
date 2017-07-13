package com.qingdao.marathon.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qingdao.marathon.base.Pagination;
import com.qingdao.marathon.system.mapper.FuncMapper;
import com.qingdao.marathon.system.model.AuthInfo;
import com.qingdao.marathon.system.model.FuncEntity;
import com.qingdao.marathon.system.service.FuncMngService;

@Service("funcMngServce")
@SuppressWarnings({"rawtypes","unchecked"})
public class FuncMngServceImpl implements FuncMngService {

	@Resource
	FuncMapper funcMapper;
	
	
	
	@Override
	public List<AuthInfo> queryFuncByOptId(String id) {
		return funcMapper.queryFuncByOptId(id);
	}

	@Override
	public Page<FuncEntity> queryFunc(Pagination pagination,Map<String,Object> parms,boolean flag) {
		PageHelper.startPage(pagination.getCurrentPage(), pagination.getNumPerPage(), flag);
		return funcMapper.queryFunc(parms);
	}

	@Override
	public FuncEntity queryByParams(Map<String, Object> parms) {
		return funcMapper.queryByParams(parms);
	}

	@Override
	public void delMainFunc(String FuncId) {
		
		funcMapper.delFunc(FuncId);
		funcMapper.delFuncByParentId(FuncId);
	}

	@Override
	public void updateFunc(FuncEntity funcEntity) {
		
		funcMapper.update(funcEntity);
	}

	@Override
	public void addFunc(FuncEntity funcEntity) {
		
		funcMapper.add(funcEntity);
	}

	@Override
	public List<AuthInfo> queryAllFunc() {
		return funcMapper.queryAllFunc();
	}
	
	@Override
	public List<AuthInfo> treeAuthInfo(List<AuthInfo> authInfos) {
		if (authInfos == null || authInfos.size() == 0) {
			return null;
		}
		Map<String, AuthInfo> authCaches = new HashMap<String, AuthInfo>();
		for (AuthInfo authInfo : authInfos) {
			if (authInfo.getParentId() == null || "".equals(authInfo.getParentId())) {
				authCaches.put(authInfo.getFuncId(), authInfo);
			}
		}

		for (AuthInfo authInfo : authInfos) {
			if (authCaches.containsKey(authInfo.getParentId())) {
				List<AuthInfo> temChild = authCaches.get(authInfo.getParentId()).getChildren();
				if (temChild == null) {
					temChild = new ArrayList<AuthInfo>();
					temChild.add(authInfo);
					authCaches.get(authInfo.getParentId()).setChildren(temChild);
				} else {
					temChild.add(authInfo);
				}
			}
		}

		List<AuthInfo> menuAuth = new ArrayList<AuthInfo>();

		for (Map.Entry<String, AuthInfo> entry : authCaches.entrySet()) {
			menuAuth.add(entry.getValue());
		}

		return menuAuth;
	}

	@Override
	public List<AuthInfo> queryFuncByRoleId(String roleid) {
		return funcMapper.queryFuncByRoleId(roleid);
	}

	@Override
	public FuncEntity queryById(String funcid) {
		// TODO Auto-generated method stub
		return funcMapper.queryById(funcid);
	}

}
