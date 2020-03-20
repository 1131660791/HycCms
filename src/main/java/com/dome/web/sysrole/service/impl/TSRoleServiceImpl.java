package com.dome.web.sysrole.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dome.bean.TSRole;
import com.dome.bean.vo.TSRoleVo;
import com.dome.web.sysrole.dao.TSRoleMapper;
import com.dome.web.sysrole.service.TSRoleService;

@Service("tSRoleService")
public class TSRoleServiceImpl implements TSRoleService{

	@Autowired
	private TSRoleMapper tsRoleMapper;

	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return tsRoleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(TSRole record) {
		// TODO Auto-generated method stub
		return tsRoleMapper.insert(record);
	}

	@Override
	public int insertSelective(TSRole record) {
		// TODO Auto-generated method stub
		return tsRoleMapper.insertSelective(record);
	}

	@Override
	public TSRole selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return tsRoleMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(TSRole record) {
		// TODO Auto-generated method stub
		return tsRoleMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(TSRole record) {
		// TODO Auto-generated method stub
		return tsRoleMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<TSRole> findUserRoleInfo(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return tsRoleMapper.findUserRoleInfo(parameter);
	}

	@Override
	public List<TSRoleVo> findRolesPage(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		List<TSRoleVo> roleVos = tsRoleMapper.findRolesPage(parameter);
		TreeSet<String> tr = new TreeSet<String>();
		for (TSRoleVo tsRoleVo : roleVos) {
			String platformNames = tsRoleVo.getPlatformName();
			if(StringUtils.hasText(platformNames)) {
				String[] platformNameArray = platformNames.split(",");
				String str = mapDistinct(platformNameArray);
				tsRoleVo.setPlatformName(str);
			}
		}
		return roleVos;
	}
	
	/**
	 * 通过map去重
	 * @param arrStr
	 */
    public static String mapDistinct(String [] arrStr) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (String str : arrStr) {
            map.put(str, str);
        }
        //返回一个包含所有对象的指定类型的数组
        String[] newArrStr =  map.keySet().toArray(new String[1]);
        String str = Arrays.toString(newArrStr);
        str = str.replace("[", "");
        str = str.replace("]", "");
        return str;
    }
	
	
	
	
}
