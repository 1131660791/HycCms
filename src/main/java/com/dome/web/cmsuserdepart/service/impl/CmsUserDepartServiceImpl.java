package com.dome.web.cmsuserdepart.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dome.bean.CmsUserDepart;
import com.dome.bean.TSDepart;
import com.dome.bean.vo.CmsUserDepartVo;
import com.dome.util.uuid.UUIDUtil;
import com.dome.web.cmsuserdepart.dao.CmsUserDepartMapper;
import com.dome.web.cmsuserdepart.service.CmsUserDepartService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 查询用户单位
 * @author admin
 *
 */
@Service("userDepartService")
public class CmsUserDepartServiceImpl implements CmsUserDepartService {
	
	@Autowired
	private CmsUserDepartMapper userDepartMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return userDepartMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(CmsUserDepart record) {
		// TODO Auto-generated method stub
		return userDepartMapper.insert(record);
	}

	@Override
	public int insertSelective(CmsUserDepart record) {
		// TODO Auto-generated method stub
		return userDepartMapper.insertSelective(record);
	}

	@Override
	public CmsUserDepart selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return userDepartMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(CmsUserDepart record) {
		// TODO Auto-generated method stub
		return userDepartMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(CmsUserDepart record) {
		// TODO Auto-generated method stub
		return userDepartMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<CmsUserDepartVo> findUserManageDeparts(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return userDepartMapper.findUserManageDeparts(parameter);
	}

	@Override
	public int deleteUserDepart(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return userDepartMapper.deleteUserDepart(parameter);
	}

	@Override
	public int saveUserManageDepart(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		String userDepartJson = parameter.get("userDepartJson").toString();
		JSONArray userDepartArray = JSONArray.fromObject(userDepartJson);
		String userStr = userDepartArray.get(0).toString();
		JSONObject userObj =JSONObject.fromObject(userStr);
		String userIdStr = userObj.get("userId").toString();
		
		Map<String,Object> par = new HashMap<>();
		par.put("userId", userIdStr);
		userDepartMapper.deleteUserAllDepart(par);
		
		CmsUserDepart userDepart = null;
		for(int i=0;i<userDepartArray.size();i++) {
			userDepart = new CmsUserDepart();
			String userDepartStr = userDepartArray.get(i).toString();
			JSONObject userDepartObj =JSONObject.fromObject(userDepartStr);
			String userId = userDepartObj.get("userId").toString();
			String departId = userDepartObj.get("departId").toString();
			
			userDepart.setId(UUIDUtil.getUUID());
			userDepart.setFkUserId(userId);
			userDepart.setFkDepartId(departId);
			userDepartMapper.insertSelective(userDepart);
		}
		return 1;
	}


}
