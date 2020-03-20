package com.dome.web.cmscostconfiguration.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dome.bean.CmsCostConfiguration;
import com.dome.bean.CmsOconfigDetail;
import com.dome.bean.TSRolePlatform;
import com.dome.util.uuid.UUIDUtil;
import com.dome.web.cmscostconfiguration.dao.CmsCostConfigurationMapper;
import com.dome.web.cmscostconfiguration.service.CmsCostConfigurationService;
import com.dome.web.cmsoconfigdetail.dao.CmsOconfigDetailMapper;
import com.dome.web.sysroleplatform.dao.TSRolePlatformMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Service("costConfigurationService")
public class CmsCostConfigurationServiceImpl implements CmsCostConfigurationService{
	
	@Autowired
	private CmsCostConfigurationMapper costConfigurationMapper;
	
	@Autowired
	private CmsOconfigDetailMapper oconfigDetailMapper;
	
	@Autowired
	private TSRolePlatformMapper rolePlatformMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return costConfigurationMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(CmsCostConfiguration record) {
		// TODO Auto-generated method stub
		return costConfigurationMapper.insert(record);
	}

	@Override
	public int insertSelective(CmsCostConfiguration record) {
		// TODO Auto-generated method stub
		return costConfigurationMapper.insertSelective(record);
	}

	@Override
	public CmsCostConfiguration selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return costConfigurationMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(CmsCostConfiguration record) {
		// TODO Auto-generated method stub
		return costConfigurationMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(CmsCostConfiguration record) {
		// TODO Auto-generated method stub
		return costConfigurationMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateDoctorState(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return costConfigurationMapper.updateDoctorState(parameter);
	}

	/**
	   {
			"state": "0:发布:1:草稿",
			"doctorId": "医生Id",
			"doctorName": "医生名称",
			"hospitalId":"医院",
			"hospitalName":"医院名称",
			"platformService": [{
				"platformId": "平台名称",
				"columnId": "栏目id",
				"serviceId": "服务id",
				"comboId": [{
					"id": "套餐Id"
				}, {
					"id": "套餐Id"
				}],
				"publicTime": "发布时间",
				"expireTime": "修改时间",
				"sort": 权重
			}]
		}
	 */
	@Override
	public int saveDoctorPlatformService(Map<String, Object> par) {
		// TODO Auto-generated method stub
		String userId = par.get("userId").toString();
		String realname = par.get("realname").toString();
		String platformServiceJsonStr = par.get("platformServiceJson").toString();
		
		JSONObject doctorServiceObj =JSONObject.fromObject(platformServiceJsonStr);
		CmsCostConfiguration configuration = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		int state = doctorServiceObj.getInt("state"); // 发布状态
		String doctorId = doctorServiceObj.get("doctorId").toString();
		String doctorName = doctorServiceObj.get("doctorName").toString();
		String hospitalId = doctorServiceObj.get("hospitalId").toString();
		String hospitalName = doctorServiceObj.get("hospitalName").toString();
		
		// 查询医生原来是否有 服务 并且管理员 存在 该平台的管理权限 如果有 删除 并生成新的服务
		
		// 查询该角色能 操作的 平台
		List<TSRolePlatform> rolePlatforms =  rolePlatformMapper.findRoleManagePlatform(par);
		List platformIds = new ArrayList();
		for (int i=0;i<rolePlatforms.size();i++) {
			platformIds.add(rolePlatforms.get(i).getFkPlatformId());
		}
		
		
		Map<String,Object> parameter = new HashMap();
		parameter.put("doctorId", doctorId);
		parameter.put("hospitalId", hospitalId);
		parameter.put("platformIds", platformIds); // 管理员 能操作的平台ids
		
		List<CmsCostConfiguration> costConfigurations = costConfigurationMapper.findDoctorService(parameter);
		// 如果存在 删除 医生配置
		if(costConfigurations != null && costConfigurations.size() > 0) {
			List list = new ArrayList();
			for (CmsCostConfiguration cmsCostConfiguration : costConfigurations) {
				String configurationId = cmsCostConfiguration.getId(); // 配置Id
				list.add(configurationId);
			}
			Map<String,Object> param = new HashMap();
			// 删除 医生 服务 配置 与 套餐的关联
			param.put("configDetails", list);
			int reslut = oconfigDetailMapper.deleteDoctorConfigDetail(param);
			
			// 删除 医生  服务 配置
			int re = costConfigurationMapper.deleteDoctorConfig(parameter);
		}
		
		// 删除后从新 对医生进行配置
		String platformServiceStr = doctorServiceObj.get("platformService").toString();
		JSONArray platformServiceArray = JSONArray.fromObject(platformServiceStr);
		for(int j = 0;j<platformServiceArray.size();j++) {
			// 用户服务配置
			configuration = new CmsCostConfiguration();
			
			String serviceJson = platformServiceArray.get(j).toString();
			
			JSONObject serviceObj =JSONObject.fromObject(serviceJson);
			
			String platformId = serviceObj.get("platformId").toString();
			String columnId = serviceObj.get("columnId").toString();
			String serviceId = serviceObj.get("serviceId").toString();
			String publicTime = serviceObj.get("publicTime").toString();
			String expireTime = serviceObj.get("expireTime").toString();
			int sort = serviceObj.getInt("sort");
			
			String uuid = UUIDUtil.getUUID();
			String time = sdf.format(new Date());
			configuration.setId(uuid);
			configuration.setHospitalId(hospitalId);
			configuration.setHospitalName(hospitalName);
			configuration.setConfigCreateTime(time);
			configuration.setBusinessType(serviceId); // 服务id
			configuration.setConfigUserId(userId);
			configuration.setConfigOwnUserId(doctorId);
			configuration.setConfigOwnUserName(doctorName);
			
			String date = sdf.format(new Date());
			configuration.setCreateTime(date);
			configuration.setUpdateTime(date);
			
			configuration.setState(state); // 0 发布 1 草稿
			configuration.setFkPlatformId(platformId);
			configuration.setPublicTime(publicTime);
			configuration.setExpireTime(expireTime);
			configuration.setFkColumnId(columnId);
			configuration.setSort(sort);
			
			costConfigurationMapper.insertSelective(configuration);
			
			String comboJsonStr = serviceObj.get("comboId").toString();
			JSONArray comboJsonArray = JSONArray.fromObject(comboJsonStr);
			CmsOconfigDetail oconfigDetail = null;
			for(int k = 0;k < comboJsonArray.size(); k++) {
			   oconfigDetail = new CmsOconfigDetail();
			   String comboJson = comboJsonArray.get(k).toString();
			   JSONObject combo =JSONObject.fromObject(comboJson);
			   String comboId = combo.get("id").toString();
			   oconfigDetail.setId(UUIDUtil.getUUID());
			   oconfigDetail.setConfigId(uuid);
			   oconfigDetail.setFkServicecomboId(comboId);
			   oconfigDetail.setCreateTime(time);
			   String updateTime = sdf.format(new Date());
			   oconfigDetail.setUpdateTime(updateTime);
			   
			   oconfigDetailMapper.insertSelective(oconfigDetail);
			}
		}
		return 1;
	}

}
