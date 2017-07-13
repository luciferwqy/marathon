package com.qingdao.marathon.system.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qingdao.marathon.base.Pagination;
import com.qingdao.marathon.system.mapper.MatchGroupMngMapper;
import com.qingdao.marathon.system.mapper.MatchMngMapper;
import com.qingdao.marathon.system.model.AchievementEntity;
import com.qingdao.marathon.system.model.MatchEntity;
import com.qingdao.marathon.system.model.MatchGroupEntity;
import com.qingdao.marathon.system.service.MatchGroupMngService;

@Service("matchGroupMngService")
@SuppressWarnings({"rawtypes","unchecked"})
public class MatchGroupMngServceImpl implements MatchGroupMngService {

	@Resource
	MatchGroupMngMapper matchGroupMngMapper;
	
	@Resource
	MatchMngMapper matchMngMapper;
	
	
	
//	@Override
//	public List<AuthInfo> queryFuncByOptId(String id) {
//		return funcMapper.queryFuncByOptId(id);
//	}

	@Override
	public Page<MatchGroupEntity> queryMatchGroup(Pagination pagination,Map<String,Object> parms,boolean flag) {
		PageHelper.startPage(pagination.getCurrentPage(), pagination.getNumPerPage(), flag);
		return matchGroupMngMapper.queryMatchGroup(parms);
	}

	@Override
	public List<MatchEntity> queryByParams(Map<String, Object> parms) {
		return matchMngMapper.queryByParams(parms);
	}

	@Override
	public List<AchievementEntity> queryAchByParams(Map<String, Object> parms) {
		return matchGroupMngMapper.queryAchByParams(parms);
	}

	@Override
	public List<MatchGroupEntity> queryGroupByParams(Map<String, Object> parms) {
		return matchGroupMngMapper.queryGroupByParams(parms);
	}

	@Override
	public void delMainFunc(String groupId) {
		matchGroupMngMapper.delGroup(groupId);
	}

	@Override
	public void updateMatchGroup(MatchGroupEntity matchGroupEntity) {
		matchGroupMngMapper.update(matchGroupEntity);
	}

	@Override
	public void addMatchGroup(MatchGroupEntity matchGroupEntity) {
		
		matchGroupMngMapper.add(matchGroupEntity);
	}
//
//	@Override
//	public List<AuthInfo> queryAllFunc() {
//		return funcMapper.queryAllFunc();
//	}
	
//	@Override
//	public List<AuthInfo> treeAuthInfo(List<AuthInfo> authInfos) {
//		if (authInfos == null || authInfos.size() == 0) {
//			return null;
//		}
//		Map<String, AuthInfo> authCaches = new HashMap<String, AuthInfo>();
//		for (AuthInfo authInfo : authInfos) {
//			if (authInfo.getParentId() == null || "".equals(authInfo.getParentId())) {
//				authCaches.put(authInfo.getFuncId(), authInfo);
//			}
//		}
//
//		for (AuthInfo authInfo : authInfos) {
//			if (authCaches.containsKey(authInfo.getParentId())) {
//				List<AuthInfo> temChild = authCaches.get(authInfo.getParentId()).getChildren();
//				if (temChild == null) {
//					temChild = new ArrayList<AuthInfo>();
//					temChild.add(authInfo);
//					authCaches.get(authInfo.getParentId()).setChildren(temChild);
//				} else {
//					temChild.add(authInfo);
//				}
//			}
//		}
//
//		List<AuthInfo> menuAuth = new ArrayList<AuthInfo>();
//
//		for (Map.Entry<String, AuthInfo> entry : authCaches.entrySet()) {
//			menuAuth.add(entry.getValue());
//		}
//
//		return menuAuth;
//	
	
	@Override
	public MatchGroupEntity queryById(String groupId) {
		return matchGroupMngMapper.queryById(groupId);
	}
}
