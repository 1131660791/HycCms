package com.dome.web.sysfunction.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dome.bean.TSFunction;
import com.dome.bean.vo.TSFunctionVo;
import com.dome.web.sysfunction.dao.TSFunctionMapper;
import com.dome.web.sysfunction.service.TSFunctionService;

@Service("tSFunctionService")
public class TSFunctionServiceImpl implements TSFunctionService{

	@Autowired
	private TSFunctionMapper tSFunctionMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return tSFunctionMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(TSFunction record) {
		// TODO Auto-generated method stub
		return tSFunctionMapper.insert(record);
	}

	@Override
	public int insertSelective(TSFunction record) {
		// TODO Auto-generated method stub
		return tSFunctionMapper.insertSelective(record);
	}

	@Override
	public TSFunction selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return tSFunctionMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(TSFunction record) {
		// TODO Auto-generated method stub
		return tSFunctionMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(TSFunction record) {
		// TODO Auto-generated method stub
		return tSFunctionMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<TSFunctionVo> findRoleFunction(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return tSFunctionMapper.findRoleFunction(parameter);
	}
	
	
	@Override
	public List<TSFunctionVo> loadFunctionTree(List<TSFunctionVo> functions) {
		 // 最后的结果
	    List<TSFunctionVo> functionVos = new ArrayList<TSFunctionVo>();
	    // 先找到所有的一级菜单
	    for (int i = 0; i < functions.size(); i++) {
	        // 一级菜单没有parentId
	        if (!StringUtils.hasText(functions.get(i).getParentfunctionid())) {
	        	functionVos.add(functions.get(i)); 
	        }
	    }
	    
	    // 为一级菜单设置子菜单，getChild 是递归调用的
	    for (TSFunctionVo functionVo : functionVos) {
	    	functionVo.setFunctions(getChild(functionVo.getId(), functions));
	    }
		return functionVos;
	}
	
	/**
	 * 递归查找子菜单
	 * @param id
	 *            当前菜单id
	 * @param functions
	 *            要查找的列表
	 * @return
	 */
	private List<TSFunctionVo> getChild(String id, List<TSFunctionVo> functions) {
	    // 子菜单
	    List<TSFunctionVo> childList = new ArrayList<>();
	    for (TSFunctionVo function : functions) {
	        // 遍历所有节点，将父菜单id与传过来的id比较
	        if (StringUtils.hasText(function.getParentfunctionid())) {
	            if (function.getParentfunctionid().equals(id)) {
	                childList.add(function);
	            }
	        }
	    }
	    
	    // 把子菜单的子菜单再循环一遍
	    for (TSFunctionVo functionVo : childList) {// 没有url子菜单还有子菜单
	        if (!StringUtils.hasText(functionVo.getFunctionurl())) {
	            // 递归
	        	functionVo.setFunctions(getChild(functionVo.getId(), functions));
	        }
	    } // 递归退出条件
	    if (childList.size() == 0) {
	        return null;
	    }
	    return childList;
	}
	
}
