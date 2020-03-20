package com.dome.web.cmsmaterials.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dome.bean.CmsMaterials;
import com.dome.bean.vo.CmsMaterialsVo;
import com.dome.bean.vo.TSUserVo;
import com.dome.util.ajax.AjaxUtil;
import com.dome.util.page.Page;
import com.dome.util.reverseback.ReverseBackData;
import com.dome.util.uuid.UUIDUtil;
import com.dome.web.cmsmaterials.service.CmsMaterialsService;

/**
 * 学习资料控制层
 * @author admin
 *
 */
@Controller
@RequestMapping(value="materials")
public class CmsMaterialsController {
	
	@Resource(name="materialsService")
	private CmsMaterialsService materialsService;
	
	
	/**
	 * 添加学习资料
	 * @param materials
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value="addMaterials")
	public void addMaterials(CmsMaterials materials,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Session session = SecurityUtils.getSubject().getSession();
			TSUserVo userVo = (TSUserVo)session.getAttribute("sysUser");
			String realname = userVo.getTsBaseUser().getRealname();
			String time = sdf.format(new Date());
			String id = UUIDUtil.getUUID();
			materials.setId(id);
			materials.setCreateTime(time);
			materials.setUpdateTime(time);
			materials.setCreateUser(userVo.getId());
			materials.setCreateUserName(realname);
			materials.setUpdateUser(userVo.getId());
			materials.setUpdateUserName(realname);
			
			int reslut =  materialsService.insertSelective(materials);
			if(reslut < 1){
				returnDate.setFlag("error");
				returnDate.setMsg("添加学习资料失败!");
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("添加学习资料异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}

	/**
	 * 查询学习资料
	 * @param materialsId - 学习资料Id
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value="findMaterials")
	public void findMaterials(@RequestParam String id,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			CmsMaterials materials =  materialsService.selectByPrimaryKey(id);
			returnDate.setObj(materials);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询学习资料异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}

	
	/**
	 * 修改 学习资料
	 * @param materials
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value="updateMaterials")
	public void updateMaterials(CmsMaterials materials,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Session session = SecurityUtils.getSubject().getSession();
			TSUserVo userVo = (TSUserVo)session.getAttribute("sysUser");
			String realname = userVo.getTsBaseUser().getRealname();
			String time = sdf.format(new Date());
			
			materials.setUpdateTime(time);
			materials.setUpdateUser(userVo.getId());
			materials.setUpdateUserName(realname);
			int reslut =  materialsService.updateByPrimaryKeySelective(materials);
			if(reslut < 1){
				returnDate.setFlag("error");
				returnDate.setMsg("修改学习资料失败!");
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("修改学习资料异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 删除学习资料
	 * @param  id - 学习资料id
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value="deleteMaterials")
	public void deleteMaterials(@RequestParam String id,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			int reslut = materialsService.deleteByPrimaryKey(id);
		}catch (Exception e) {
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("删除学习资料异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 批量发布 学习资料
	 * @param idJsons - id 集
	 * [{
		    "id":"学习资料id"
		 },{
		    "id":"学习资料id"
	   }]
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value="publishAllMaterials")
	public void publishAllMaterials(String idsJson,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			int reslut = materialsService.publishAllMaterials(idsJson);
		}catch (Exception e) {
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("批量发布学习资料异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 查询学习资料列表
	 * @param currentPage - 显示页
	 * @param showCount  - 每页显示条数
	 * @param platformId - 平台id
	 * @param hospitalId - 医院id
	 * @param columnId - 栏目id
	 * @param materialsName - 资料名称
	 * @param state - 状态 0:已发布 1:草稿 2:待发布
	 * @param startDate - 开始时间  yyyy-MM-dd HH:mm:ss 
	 * @param endDate   - 结束时间  yyyy-MM-dd HH:mm:ss
	 * @param weightOrder - 按权重排序 1：升序,2:降序
	 * @param dataOrder - 时间排序  1:升序,2：降序
	 * @param sortOrder - 按前台显示顺序 - 推荐 权重 时间的优先级排序 排序  0:不按此排序,1：按此排序
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value="findMaterialsList")
	public void findMaterialsList(Integer currentPage, Integer showCount, @RequestParam String platformId,
			@RequestParam String hospitalId, @RequestParam String columnId, String materialsName, Integer state,
			String startDate, String endDate, Integer weightOrder, Integer dataOrder, int sortOrder,
			HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			if(currentPage == null){
				currentPage = 1;
			}
			if(showCount == null){
				showCount = 20;
			}
			Page page = new Page();
		    page.setCurrentPage(currentPage);
		    page.setShowCount(showCount);
		    
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = sdf.format(new Date());
			Map<String,Object> par = new HashMap();
			par.put("platformId", platformId);
			par.put("hospitalId", hospitalId);
			par.put("columnId", columnId);
			par.put("materialsName", materialsName);
			par.put("state", state);
			par.put("startDate", startDate);
			par.put("endDate", endDate);
			par.put("weightOrder", weightOrder);
			par.put("dataOrder", dataOrder);
			par.put("sortOrder", sortOrder);
			par.put("time", time);
			par.put("page", page);
			List<CmsMaterialsVo> materialss = materialsService.findMaterialsListPage(par);
			returnDate.setObj(materialss);
		}catch (Exception e) {
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询学习资料列表异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 查询学习资料 栏目类型的 学习资料
	 * @param currentPage - 开始页
	 * @param showCount	  - 显示页
	 * @param columnId    - 栏目id
	 * @param materialsName - 学习资料名称
	 */
	@ResponseBody
	@RequestMapping(value="findColumnMaterials")
	public void findColumnMaterials(Integer currentPage, Integer showCount, @RequestParam String columnId,
			String materialsName,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			if(currentPage == null){
				currentPage = 1;
			}
			if(showCount == null){
				showCount = 20;
			}
			Page page = new Page();
		    page.setCurrentPage(currentPage);
		    page.setShowCount(showCount);
		    
			Map<String,Object> par = new HashMap();
			par.put("page", page);
			par.put("columnId", columnId);
			par.put("materialsName", materialsName);
			List<CmsMaterials> materials = materialsService.findColumnMaterialsPage(par);
			returnDate.setObj(materials);
		}catch (Exception e) {
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询学习资料类型栏目异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	
	
}
