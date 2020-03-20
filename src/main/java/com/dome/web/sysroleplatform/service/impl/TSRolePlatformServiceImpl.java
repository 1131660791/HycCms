package com.dome.web.sysroleplatform.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dome.bean.TSRolePlatform;
import com.dome.util.uuid.UUIDUtil;
import com.dome.web.sysroleplatform.dao.TSRolePlatformMapper;
import com.dome.web.sysroleplatform.service.TSRolePlatformService;
import com.dome.web.sysroleplatform.vo.CmsRolePlatformVo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("tSRolePlatform")
public class TSRolePlatformServiceImpl implements TSRolePlatformService{

	@Autowired
	private TSRolePlatformMapper rolePlatformMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return rolePlatformMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(TSRolePlatform record) {
		// TODO Auto-generated method stub
		return rolePlatformMapper.insert(record);
	}

	@Override
	public int insertSelective(TSRolePlatform record) {
		// TODO Auto-generated method stub
		return rolePlatformMapper.insertSelective(record);
	}

	@Override
	public TSRolePlatform selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return rolePlatformMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(TSRolePlatform record) {
		// TODO Auto-generated method stub
		return rolePlatformMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(TSRolePlatform record) {
		// TODO Auto-generated method stub
		return rolePlatformMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<CmsRolePlatformVo> findRolePlatform(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return rolePlatformMapper.findRolePlatform(parameter);
	}
	
	/**
	 * {
			"roleId": "角色Id",
			"platformIds": [{
				"platformId": "平台id"
			}, {
				"platformId": "平台id"
			}]
		}
	 */
	@Override
	public int saveRolePlatform(Map<String, Object> parameter) {
		
		String rolePlatformJson = parameter.get("rolePlatformJson").toString();
		JSONObject rolePlatform = JSONObject.fromObject(rolePlatformJson);
		
		String roleId = rolePlatform.get("roleId").toString();
		String platformIds = rolePlatform.get("platformIds").toString();
		
		Map<String,Object> par = new HashMap();
		par.put("roleId", roleId);
		// 删除 该角色 原有 角色配置
		int reslut = rolePlatformMapper.deleteRolePlatform(par);
		
		JSONArray platformIdArray = JSONArray.fromObject(platformIds);
		
		TSRolePlatform rolePlatfrom = null;
		for(int i=0;i<platformIdArray.size();i++) {
			String platformIdStr = platformIdArray.get(i).toString();
			JSONObject platformIdObj = JSONObject.fromObject(platformIdStr);
			String platformId = platformIdObj.get("platformId").toString();
			
			rolePlatfrom =new TSRolePlatform();
			
			rolePlatfrom.setId(UUIDUtil.getUUID());
			rolePlatfrom.setFkRoleId(roleId);
			rolePlatfrom.setFkPlatformId(platformId);
			
			rolePlatformMapper.insertSelective(rolePlatfrom);
		}
		return 1;
	}

	@Override
	public List<TSRolePlatform> findRoleManagePlatform(Map<String, Object> par) {
		// TODO Auto-generated method stub
		return rolePlatformMapper.findRoleManagePlatform(par);
	}
	
}
