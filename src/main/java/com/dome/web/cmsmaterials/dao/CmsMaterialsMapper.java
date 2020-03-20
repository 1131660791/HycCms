package com.dome.web.cmsmaterials.dao;

import java.util.List;
import java.util.Map;

import com.dome.bean.CmsMaterials;
import com.dome.bean.vo.CmsMaterialsVo;

/**
 * 学习资料 数据访问层
 * @author admin
 */
public interface CmsMaterialsMapper {
	
    int deleteByPrimaryKey(String id);

    int insert(CmsMaterials record);

    int insertSelective(CmsMaterials record);

    CmsMaterials selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CmsMaterials record);

    int updateByPrimaryKey(CmsMaterials record);
    
    /**
     * 查询学习资料列表 分页
     * @param par
     * @return
     */
	List<CmsMaterialsVo> findMaterialsListPage(Map<String, Object> par);
	
	/**
	 * 查询学习资料 栏目类型的 学习资料
	 * @param par
	 * @return
	 */
	List<CmsMaterials> findColumnMaterialsPage(Map<String, Object> par);
	
	/**
	 * 远程查看学习资料 分页
	 * @param par
	 * @return
	 */
	List<CmsMaterials> findRemoteMaterialsPage(Map<String, Object> par);
	
	/**
	 * 查询学习资料 详情
	 * @param parameter
	 * @return
	 */
	CmsMaterials findMaterialsRemote(Map<String, Object> parameter);

}