package com.dome.web.cmsmaterials.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dome.bean.CmsMaterials;
import com.dome.bean.vo.CmsMaterialsVo;
import com.dome.web.cmsmaterials.dao.CmsMaterialsMapper;
import com.dome.web.cmsmaterials.service.CmsMaterialsService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("materialsService")
public class CmsMaterialsServiceImpl implements CmsMaterialsService {

	@Autowired
	private CmsMaterialsMapper materialsMapper;

	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return materialsMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(CmsMaterials record) {
		// TODO Auto-generated method stub
		return materialsMapper.insert(record);
	}

	@Override
	public int insertSelective(CmsMaterials record) {
		// TODO Auto-generated method stub
		return materialsMapper.insertSelective(record);
	}

	@Override
	public CmsMaterials selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return materialsMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(CmsMaterials record) {
		// TODO Auto-generated method stub
		return materialsMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(CmsMaterials record) {
		// TODO Auto-generated method stub
		return materialsMapper.updateByPrimaryKey(record);
	}

	@Override
	public int publishAllMaterials(String idsJson) {
		// TODO Auto-generated method stub
		JSONArray idsObjs = JSONArray.fromObject(idsJson);
		CmsMaterials materials = null;
		for (int i = 0; i < idsObjs.size(); i++) {
			String idStr = idsObjs.get(i).toString();
			JSONObject idObj = JSONObject.fromObject(idStr);
			String id = idObj.getString("id");

			materials = new CmsMaterials();
			materials.setId(id);
			materials.setState(0);
			materialsMapper.updateByPrimaryKeySelective(materials);
		}
		return 1;
	}

	@Override
	public List<CmsMaterialsVo> findMaterialsListPage(Map<String, Object> par) {
		// TODO Auto-generated method stub
		return materialsMapper.findMaterialsListPage(par);
	}

	@Override
	public List<CmsMaterials> findColumnMaterialsPage(Map<String, Object> par) {
		// TODO Auto-generated method stub
		return materialsMapper.findColumnMaterialsPage(par);
	}

	@Override
	public List<CmsMaterials> findRemoteMaterialsPage(Map<String, Object> par) {
		// TODO Auto-generated method stub
		return materialsMapper.findRemoteMaterialsPage(par);
	}

	@Override
	public CmsMaterials findMaterialsRemote(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return materialsMapper.findMaterialsRemote(parameter);
	}

}
