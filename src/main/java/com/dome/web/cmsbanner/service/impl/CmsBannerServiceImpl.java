package com.dome.web.cmsbanner.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dome.bean.CmsBanner;
import com.dome.bean.CmsBannerContent;
import com.dome.bean.CmsBannerNews;
import com.dome.bean.vo.CmsBannerVo;
import com.dome.util.uuid.UUIDUtil;
import com.dome.web.cmsbanner.dao.CmsBannerContentMapper;
import com.dome.web.cmsbanner.dao.CmsBannerMapper;
import com.dome.web.cmsbanner.service.CmsBannerService;
import com.dome.web.cmsbanner.vo.ColumnBannerVo;
import com.dome.web.cmsbannernews.dao.CmsBannerNewsMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("bannerService")
public class CmsBannerServiceImpl implements CmsBannerService {

	@Autowired
	private CmsBannerMapper bannerMapper;

	@Autowired
	private CmsBannerContentMapper bannerContentMapper;

	@Autowired
	private CmsBannerNewsMapper bannerNewsMapper;

	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return bannerMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(CmsBanner record) {
		// TODO Auto-generated method stub
		return bannerMapper.insert(record);
	}

	@Override
	public int insertSelective(CmsBanner record) {
		// TODO Auto-generated method stub
		return bannerMapper.insertSelective(record);
	}

	@Override
	public CmsBanner selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return bannerMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(CmsBanner record) {
		// TODO Auto-generated method stub
		return bannerMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(CmsBanner record) {
		// TODO Auto-generated method stub
		return bannerMapper.updateByPrimaryKey(record);
	}

	@Override
	public int addBanner(Map<String, Object> parameter) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date());
		String bannerJson = parameter.get("bannerJson").toString();
		String userId = parameter.get("userId").toString();
		String userName = parameter.get("userName").toString();

		JSONObject bannerObj = JSONObject.fromObject(bannerJson);
		String bannerTheme = bannerObj.getString("bannerTheme");
		String fkColumnId = bannerObj.getString("fkColumnId");
		String hospitalId = bannerObj.getString("hospitalId");
		String departname = bannerObj.getString("departname");
		String fkPlatformId = bannerObj.getString("fkPlatformId");
		String publicTime = bannerObj.getString("publicTime");
		String expireTime = bannerObj.getString("expireTime");
		int state = bannerObj.getInt("state");
		CmsBanner banner = new CmsBanner();
		banner.setId(UUIDUtil.getUUID());
		banner.setBannerTheme(bannerTheme);
		banner.setFkColumnId(fkColumnId);
		banner.setHospitalId(hospitalId);
		banner.setDepartname(departname);
		banner.setFkPlatformId(fkPlatformId);
		banner.setState(state); // 0: 发布 ,1：草稿
		banner.setPublicTime(publicTime);
		banner.setPublicUser(userId);
		banner.setExpireTime(expireTime);
		banner.setCreateUser(userId);
		banner.setCreateUserName(userName);
		banner.setUpdateUser(userId);
		banner.setUpdateUserName(userName);
		banner.setCreateTime(time);
		banner.setUpdateTime(time);
		int reslut = bannerMapper.insertSelective(banner);
		if (reslut > 0) {
			JSONArray bannerContents = bannerObj.getJSONArray("bannerContents");
			CmsBannerContent bannerContent = null;
			for (int i = 0; i < bannerContents.size(); i++) {
				String contentJson = bannerContents.get(i).toString();
				JSONObject contentObj = JSONObject.fromObject(contentJson);
				String bannerKeyword = contentObj.getString("bannerKeyword");
				String bannerName = contentObj.getString("bannerName");
				String bannerUrl = contentObj.getString("bannerUrl");
				int sort = contentObj.getInt("sort");
				int bannerLinkFlag = contentObj.getInt("bannerLinkFlag");
				String bannerLink = contentObj.getString("bannerLink");
				String bannerTarget = contentObj.getString("bannerTarget");
				// 生成Banner内容
				bannerContent = new CmsBannerContent();
				String bannerContentId = UUIDUtil.getUUID();
				bannerContent.setId(bannerContentId);
				bannerContent.setFkBannerId(banner.getId());
				bannerContent.setBannerKeyword(bannerKeyword);
				bannerContent.setBannerName(bannerName);
				bannerContent.setBannerUrl(bannerUrl);
				bannerContent.setSort(sort);
				bannerContent.setBannerLinkFlag(bannerLinkFlag);
				bannerContent.setBannerLink(bannerLink);
				bannerContent.setBannerTarget(bannerTarget);
				bannerContent.setQuoteFlag(0); // 默认未关联新闻
				bannerContent.setCreateTime(time);
				bannerContent.setUpdateTime(time);

				// banner链接类型 为内链: 0:内联,1外联
				if (bannerLinkFlag == 0) {
					// banner 内容关联对象
					Object relationObjIdObj = contentObj.get("relationObjId");
					Object columnTypeIdObj = contentObj.get("columnTypeId");

					if (columnTypeIdObj != null && !columnTypeIdObj.equals(null)) {
						
						Integer type = this.columnTypeConvert(columnTypeIdObj.toString());
						if (type != null && !type.equals(null)) {
							CmsBannerNews bannerNews = new CmsBannerNews();
							bannerNews.setId(UUIDUtil.getUUID());
							bannerNews.setFkBannerId(bannerContentId);
							bannerNews.setType(type); // 1:新闻 2:视频 3:学习资料
							if (type == 1) {
								bannerNews.setFkNewsId(relationObjIdObj.toString());
							} else if (type == 2) {
								bannerNews.setFkVideoId(relationObjIdObj.toString());
							} else if (type == 3) {
								bannerNews.setFkMaterialsId(relationObjIdObj.toString());
							}
							bannerContent.setQuoteFlag(1); // banner 内容图片已经被关联
							bannerNewsMapper.insertSelective(bannerNews);
						}
					}
				}
				bannerContentMapper.insertSelective(bannerContent);
			}
		}
		return 1;
	}

	@Override
	public CmsBannerVo findBanner(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return bannerMapper.findBanner(parameter);
	}

	@Override
	public int updateBanner(Map<String, Object> parameter) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date());
		String bannerJson = parameter.get("bannerJson").toString();
		String userId = parameter.get("userId").toString();
		String userName = parameter.get("userName").toString();

		JSONObject bannerObj = JSONObject.fromObject(bannerJson);
		String id = bannerObj.getString("id");
		String bannerTheme = bannerObj.getString("bannerTheme");
		String publicTime = bannerObj.getString("publicTime");
		String expireTime = bannerObj.getString("expireTime");
		int state = bannerObj.getInt("state");
		String fkColumnId = bannerObj.getString("fkColumnId");
		CmsBanner banner = new CmsBanner();
		banner.setId(id);
		banner.setBannerTheme(bannerTheme);
		banner.setPublicTime(publicTime);
		banner.setExpireTime(expireTime);
		banner.setUpdateUser(userId);
		banner.setUpdateUserName(userName);
		banner.setUpdateTime(time);
		banner.setState(state);
		banner.setFkColumnId(fkColumnId);
		int reslut = bannerMapper.updateByPrimaryKeySelective(banner);
		if (reslut > 0) {
			// 删除原有 Banner内容
			Map<String, Object> param = new HashMap();
			param.put("fkBannerId", id);
			JSONArray bannerContents = bannerObj.getJSONArray("bannerContents");
			CmsBannerContent bannerContent = null;
			for (int i = 0; i < bannerContents.size(); i++) {
				String contentJson = bannerContents.get(i).toString();
				JSONObject contentObj = JSONObject.fromObject(contentJson);
				Object bannerContentId = contentObj.get("bannerContentId");

				String bannerKeyword = contentObj.getString("bannerKeyword");
				String bannerName = contentObj.getString("bannerName");
				String bannerUrl = contentObj.getString("bannerUrl");
				int sort = contentObj.getInt("sort");
				int bannerLinkFlag = contentObj.getInt("bannerLinkFlag");
				String bannerLink = contentObj.getString("bannerLink");
				String bannerTarget = contentObj.getString("bannerTarget");

				if (bannerContentId == null || bannerContentId.equals(null) || bannerContentId == ""
						|| bannerContentId.equals("")) { // 修改原有banner
					// 生成Banner内容
					bannerContent = new CmsBannerContent();
					bannerContentId = UUIDUtil.getUUID();
					bannerContent.setId(bannerContentId.toString());
					bannerContent.setFkBannerId(banner.getId());
					bannerContent.setBannerKeyword(bannerKeyword);
					bannerContent.setBannerName(bannerName);
					bannerContent.setBannerUrl(bannerUrl);
					bannerContent.setSort(sort);
					bannerContent.setBannerLinkFlag(bannerLinkFlag);
					bannerContent.setBannerLink(bannerLink);
					bannerContent.setBannerTarget(bannerTarget);
					bannerContent.setQuoteFlag(0); // 默认未关联
					bannerContent.setCreateTime(time);
					bannerContent.setUpdateTime(time);

					// banner链接类型 为内链: 0:内联,1外联
					if (bannerLinkFlag == 0) {
						// banner 内容关联对象
						Object relationObjIdObj = contentObj.get("relationObjId");
						Object columnTypeIdObj = contentObj.get("columnTypeId");

						if (columnTypeIdObj != null && !columnTypeIdObj.equals(null)) {
							Integer type = this.columnTypeConvert(columnTypeIdObj.toString());
							if (type != null && !type.equals(null)) {
								CmsBannerNews bannerNews = new CmsBannerNews();
								bannerNews.setId(UUIDUtil.getUUID());
								bannerNews.setFkBannerId(bannerContentId.toString());
								bannerNews.setType(type); // 1:新闻 2:视频 3:学习资料
								if (type == 1) {
									bannerNews.setFkNewsId(relationObjIdObj.toString());
								} else if (type == 2) {
									bannerNews.setFkVideoId(relationObjIdObj.toString());
								} else if (type == 3) {
									bannerNews.setFkMaterialsId(relationObjIdObj.toString());
								}
								bannerContent.setQuoteFlag(1); // banner 内容图片已经被关联
								bannerNewsMapper.insertSelective(bannerNews);
							}
						}
					}
					bannerContentMapper.insertSelective(bannerContent);
				} else { // 已有banner
					// 修改Banner内容
					bannerContent = new CmsBannerContent();
					bannerContent.setId(bannerContentId.toString());
					bannerContent.setFkBannerId(id);
					bannerContent.setBannerKeyword(bannerKeyword);
					bannerContent.setBannerName(bannerName);
					bannerContent.setBannerUrl(bannerUrl);
					bannerContent.setSort(sort);
					bannerContent.setBannerLinkFlag(bannerLinkFlag);
					bannerContent.setBannerLink(bannerLink);
					bannerContent.setBannerTarget(bannerTarget);
					bannerContent.setQuoteFlag(0); // 默认未关联新闻
					bannerContent.setCreateTime(time);
					bannerContent.setUpdateTime(time);
					
					// banner链接类型 为内链: 0:内联,1外联
					if (bannerLinkFlag == 0) {
						Map<String, Object> parame = new HashMap();
						parame.put("bannerId", bannerContentId);
						// 删除原有banner 的关联
						bannerNewsMapper.deleteBannerNews(parame);
						
						// banner 内容关联对象
						Object relationObjIdObj = contentObj.get("relationObjId");
						Object columnTypeIdObj = contentObj.get("columnTypeId");
						
						if (columnTypeIdObj != null && !columnTypeIdObj.equals(null)) {
							Integer type = this.columnTypeConvert(columnTypeIdObj.toString());
							
							if (type != null && !type.equals(null)) {
								CmsBannerNews bannerNews = new CmsBannerNews();
								bannerNews.setId(UUIDUtil.getUUID());
								bannerNews.setFkBannerId(bannerContentId.toString());
								bannerNews.setType(type); // 1:新闻 2:视频 3:学习资料
								if (type == 1) {
									bannerNews.setFkNewsId(relationObjIdObj.toString());
								} else if (type == 2) {
									bannerNews.setFkVideoId(relationObjIdObj.toString());
								} else if (type == 3) {
									bannerNews.setFkMaterialsId(relationObjIdObj.toString());
								}
								bannerContent.setQuoteFlag(1); // banner 内容图片已经被关联
								bannerNewsMapper.insertSelective(bannerNews);
							}
						}
					}
					bannerContentMapper.updateByPrimaryKeySelective(bannerContent);
				}

			}

		}
		return 0;
	}

	@Override
	public List<ColumnBannerVo> findColumnBanners(Map<String, Object> parameter) {
		List<ColumnBannerVo> columnBanners = bannerMapper.findColumnBanners(parameter);
		// 最后的结果
		List<ColumnBannerVo> columnBannerVos = new ArrayList<ColumnBannerVo>();
		// 先找到所有的一级菜单
		for (int i = 0; i < columnBanners.size(); i++) {
			// 一级菜单没有parentId
			if (!StringUtils.hasText(columnBanners.get(i).getColumnPId())) {
				columnBannerVos.add(columnBanners.get(i));
			}
		}

		// 为一级菜单设置子菜单，getChild 是递归调用的
		for (ColumnBannerVo columnBannerVo : columnBannerVos) {
			columnBannerVo.setColumnBannerVos(getChild(columnBannerVo.getColumnRawId(), columnBanners));
		}
		return columnBannerVos;
	}

	/**
	 * 递归查找子菜单
	 * 
	 * @param id
	 *            当前菜单id
	 * @param functions
	 *            要查找的列表
	 * @return
	 */
	private List<ColumnBannerVo> getChild(String id, List<ColumnBannerVo> columnBannerVos) {
		// 子菜单
		List<ColumnBannerVo> childList = new ArrayList<>();
		for (ColumnBannerVo columnBanner : columnBannerVos) {
			// 遍历所有节点，将父菜单id与传过来的id比较
			if (StringUtils.hasText(columnBanner.getColumnPId())) {
				if (columnBanner.getColumnPId().equals(id)) {
					childList.add(columnBanner);
				}
			}
		}
		// 把子菜单的子菜单再循环一遍
		for (ColumnBannerVo columnBannerVo : childList) {// 没有url子菜单还有子菜单
			// 递归
			columnBannerVo.setColumnBannerVos(getChild(columnBannerVo.getColumnRawId(), columnBannerVos));
		} // 递归退出条件
		if (childList.size() == 0) {
			return null;
		}
		return childList;
	}

	@Override
	public int deleteBanner(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		String bannerContentId = parameter.get("bannerContentId").toString();
		int bannerFlag = Integer.parseInt(parameter.get("bannerFlag").toString());

		int reslut = bannerContentMapper.deleteByPrimaryKey(bannerContentId);

		// 删除banner 与新闻的关联 如果banner已经关联新闻
		if (bannerFlag == 1) {
			Map<String, Object> param = new HashMap();
			param.put("bannerId", bannerContentId);
			bannerNewsMapper.deleteBannerNews(param);
		}
		return reslut;
	}

	@Override
	public List<Map<String, Object>> findBannersRemote(Map<String, Object> par) {
		// TODO Auto-generated method stub
		return bannerMapper.findBannersRemote(par);
	}

	@Override
	public List<CmsBanner> findColumnBannerByColumId(Map<String, Object> par) {
		// TODO Auto-generated method stub
		return bannerMapper.findColumnBannerByColumId(par);
	}

	@Override
	public int deleteColumnBanner(Map<String, Object> parameter) {
		// 查询 banner 的banner 内容
		List<CmsBanner> banners = bannerMapper.findColumnBannerByColumId(parameter);
		if (banners != null && banners.size() > 0) {
			CmsBanner banner = banners.get(0);
			String bannerId = banner.getId();
			// 查询 banner 的banner 内容
			Map<String, Object> par = new HashMap();
			par.put("bannerId", bannerId);
			List<CmsBannerContent> contents = bannerContentMapper.findBannerBannerContent(par);
			for (int i = 0; i < contents.size(); i++) {
				CmsBannerContent content = contents.get(i);
				String contentId = content.getId();
				Integer bannerLinkFlag = content.getBannerLinkFlag();
				if (bannerLinkFlag != null && bannerLinkFlag == 1) { // 如果为内联
					// 删除banner内容 与 关联内容的关联
					Map<String, Object> para = new HashMap();
					para.put("bannerId", contentId);
					int reslut = bannerNewsMapper.deleteBannerNews(para);
				}
				// 删除banner 内容
				int reslut = bannerContentMapper.deleteByPrimaryKey(contentId);
			}
			// 删除banner
			bannerMapper.deleteByPrimaryKey(bannerId);
		}
		return 1;
	}
	
	/**
	 * 1:新闻 2:视频 3:学习资料
	 * @param columnId - 栏目id
	 * 232880e64e1ef94d014e1efefc380006	医院	0
	   852880e64e1ef94d014e1efefc3d0007	课程	0
	   892880e64e1ef94d014e1efefc370005	医生	0
	   902880e64e1ef94d014e1efefc2f0002	通用	0
	   902880e64e1ef94d014e1efefc2f0002	通用	0
	 * @return
	 */
	public Integer columnTypeConvert(String columnId) {
		if(columnId.equals("192880e64e1ef94d014e1efefc340003") || columnId == "192880e64e1ef94d014e1efefc340003") { // 新闻 
			return 1;
		}else if(columnId.equals("662880e64e1ef94d014e1efefc360006") || columnId == "662880e64e1ef94d014e1efefc360006") { // 视频
			return 2;
		}else if(columnId.equals("882880e64e1ef94d014e1efefc3e0008") || columnId == "882880e64e1ef94d014e1efefc3e0008") { // 学习资料
			return 3;
		}else {
			return null;
		}
	}
	
	
	
	
	
	
	
	
	
	
}
