package com.dome.web.sysplatformservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dome.bean.TSPlatformService;
import com.dome.bean.vo.PlatformServiceVo;
import com.dome.bean.vo.TSPlatformServiceVo;
import com.dome.web.sysplatformservice.dao.TSPlatformServiceMapper;
import com.dome.web.sysplatformservice.service.TSPlatformServiceService;
import com.dome.web.sysplatformservice.vo.CmsDoctorServiceVo;

@Service("platformServiceService")
public class TSPlatformServiceServiceImpl implements TSPlatformServiceService{

	@Autowired
	private TSPlatformServiceMapper platformServiceMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return platformServiceMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(TSPlatformService record) {
		// TODO Auto-generated method stub
		return platformServiceMapper.insert(record);
	}

	@Override
	public int insertSelective(TSPlatformService record) {
		// TODO Auto-generated method stub
		return platformServiceMapper.insertSelective(record);
	}

	@Override
	public TSPlatformService selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return platformServiceMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(TSPlatformService record) {
		// TODO Auto-generated method stub
		return platformServiceMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(TSPlatformService record) {
		// TODO Auto-generated method stub
		return platformServiceMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<TSPlatformServiceVo> findDoctorServicesPage(Map<String, Object> parameter) {
		List<TSPlatformServiceVo> platformServiceVos = platformServiceMapper.findDoctorServicesPage(parameter);
		for (TSPlatformServiceVo tsPlatformServiceVo : platformServiceVos) {
			String servicePublicName = tsPlatformServiceVo.getServicePublicName();
			if(StringUtils.hasText(servicePublicName)) {
				String[] publicNames = servicePublicName.split(",");
				ArrayList list = new ArrayList();
		        for (int i = 0; i < publicNames.length; i++) {
		            if (!list.contains(publicNames[i]))
		                list.add(publicNames[i]);
		        }
		        String publicName = "";
		        for (int i = 0; i < list.size(); i++) {
		        	if(i==0) {
		        		publicName = list.get(i).toString();
		        	}else {
		        		publicName += "," + list.get(i).toString();
		        	}
		        }
		        tsPlatformServiceVo.setServicePublicName(publicName);
			}
	   }
	   return platformServiceVos;
	}

	@Override
	public List<CmsDoctorServiceVo> findDoctorPlatformService(Map<String, Object> par) {
		// TODO Auto-generated method stub
		return platformServiceMapper.findDoctorPlatformService(par);
	}

	@Override
	public List<PlatformServiceVo> findPlatformServiceInfo(Map<String, Object> par) {
		// TODO Auto-generated method stub
		return platformServiceMapper.findPlatformServiceInfo(par);
	}

}
