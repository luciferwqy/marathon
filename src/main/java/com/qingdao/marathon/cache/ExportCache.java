package com.qingdao.marathon.cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.qingdao.marathon.thread.model.ProgressbarModel;

public class ExportCache {

	private Map<Integer,ProgressbarModel> progressbarMap = new HashMap<Integer,ProgressbarModel>();
	
	private static Set<Integer> idSet = new HashSet<Integer>();//获取不重复的ID
	
	private static Integer limit = 10;//固定10个
	
	private static ExportCache instance;
	
	static {
		for(int i=0;i<limit ;i++){
			idSet.add(i);
		}
	}
	private ExportCache(){}
	
	public static ExportCache getInstance(){
		if(instance == null){
			instance = new ExportCache();
		}
		return instance;
	}

	public Map<Integer,ProgressbarModel> getProgressbarSet() {
		return progressbarMap;
	}

	/**
	 * 放入map，并删除set里面的ID
	 * @param p
	 * @return
	 */
	public synchronized boolean put(ProgressbarModel p) {
		if (progressbarMap.size() > limit){
			ProgressbarModel model ; 
			for(Map.Entry<Integer,ProgressbarModel> entry : progressbarMap.entrySet()){
				model = entry.getValue();
				if(model.getCurrentNum() == model.getTotal()){
					progressbarMap.remove(model.getId());
					idSet.add(model.getId());
				}
			}
			if(progressbarMap.size() > limit){
				return false;
			}else{
				progressbarMap.put(p.getId(), p);
				return true;
			}
		}else{
			progressbarMap.put(p.getId(), p);
			return true;
		}
	}
	
	public synchronized int getId(){
		Iterator<Integer> it = idSet.iterator();
		int temp = -1;
		while(it.hasNext()){
			temp = it.next();
			idSet.remove(temp);
			return temp;
		}
		return temp;
	}
	
}
