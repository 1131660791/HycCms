--  --------------------------------------------- 已经执行 start  ------------------------------------------------------------

-- 增加 用户 服务配置 状态 字段
alter table hyc_cost_configuration add column state INTEGER(12) DEFAULT 0 COMMENT '状态:0:启用,1:禁用';

-- 新增 用户服务配置 权重 字段
alter table hyc_cost_configuration add column sort INTEGER(12) DEFAULT 0 COMMENT '权重';

-- 增加 用户 服务配置 关联平台Id 字段
alter table hyc_cost_configuration add column fk_platform_id varchar(32)  COMMENT '关联平台Id';

-- 增加 服务发布时间
alter table hyc_cost_configuration add column public_time datetime COMMENT '发布时间';

-- 增加 服务过期时间
alter table hyc_cost_configuration add column expire_time datetime COMMENT '过期时间';

-- 增加 关联 栏目 id
alter table hyc_cost_configuration add column fk_column_id VARCHAR(32) COMMENT '关联栏目id';

-- 增加 服务套餐 关联 id
alter table hyc_oconfig_detail add column fk_servicecombo_id VARCHAR(32) DEFAULT NULL COMMENT '关联服务套餐id';

-- 增加系统 平台 服务表
CREATE TABLE `t_s_platform_service` (
  `id` varchar(32) NOT NULL COMMENT '平台服务id',
  `service_name` varchar(50) NOT NULL COMMENT '服务名称',
  `fk_platform_id` varchar(32) NOT NULL COMMENT '关联平台id',
  `create_user` varchar(32) NOT NULL COMMENT '创建人',
  `update_user` varchar(32) DEFAULT NULL COMMENT '修改人',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '创建用户姓名',
  `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改人姓名',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `state` int(12) NOT NULL DEFAULT '0' COMMENT '状态：0:草稿,1:已发布',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 增加平台 服务 套餐表
CREATE TABLE `t_s_platformservice_combo` (
  `id` varchar(32) NOT NULL COMMENT '平台服务套餐id',
  `combo_name` varchar(50) NOT NULL COMMENT '套餐名称',
  `package_money` int(12) DEFAULT NULL COMMENT '套餐费用',
  `package_time` int(12) DEFAULT NULL COMMENT '套餐时间',
  `package_time_unit` varchar(32) DEFAULT NULL COMMENT '套餐分钟',
  `state` int(12) DEFAULT NULL COMMENT '状态 0:启用,1:停用',
  `fk_platformservice_id` varchar(32) DEFAULT NULL COMMENT '关联平台服务id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '创建人名称',
  `update_user` varchar(32) DEFAULT NULL COMMENT '修改人',
  `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改用户姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 增加系统平台表 
CREATE TABLE `t_s_platform` (
  `id` varchar(32) NOT NULL COMMENT '平台Id',
  `platform_name` varchar(50) NOT NULL COMMENT '平台名称',
  `platform_url` varchar(300) DEFAULT NULL COMMENT '平台地址',
  `state` int(12) NOT NULL COMMENT '状态,0:启用,1:禁用',
  `create_user` varchar(32) NOT NULL COMMENT '创建人',
  `update_user` varchar(32) DEFAULT NULL COMMENT '修改人',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '创建用户姓名',
  `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改人姓名',
  `creation_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 新增角色平台表
CREATE TABLE `t_s_role_platform` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `fk_role_id` varchar(32) NOT NULL COMMENT '角色id',
  `fk_platform_id` varchar(32) NOT NULL COMMENT '平台id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 增加栏目表
CREATE TABLE `hyc_c_column` (
  `id` varchar(32) NOT NULL COMMENT '主键Id',
  `column_id` varchar(32) NOT NULL COMMENT '栏目id,默认栏目的id,非默认栏目为主键id',
  `p_id` varchar(32) DEFAULT NULL COMMENT '父级id',
  `column_name` varchar(50) NOT NULL COMMENT '栏目名称',
  `column_property` int(12) NOT NULL DEFAULT '0' COMMENT '栏目属性:0:非默认栏目,1:为默认栏目',
  `fk_column_type_id` varchar(32) NOT NULL COMMENT '栏目类型Id',
  `state` int(12) NOT NULL DEFAULT '0' COMMENT '状态:0启用:1:禁用',
  `column_code` varchar(32) NOT NULL COMMENT '栏目Code',
  `column_link` varchar(200) DEFAULT NULL COMMENT '栏目链接',
  `column_url` varchar(200) DEFAULT NULL COMMENT '栏目图片路径',
  `fk_platform_id` varchar(32) NOT NULL COMMENT '平台id',
  `hospital_id` varchar(32) NOT NULL COMMENT '医院id',
  `departname` varchar(50) DEFAULT NULL COMMENT '所属机构名称',
  `sort` int(12) DEFAULT NULL COMMENT '排序',
  `create_user` varchar(32) NOT NULL COMMENT '编辑人',
  `update_user` varchar(32) DEFAULT NULL COMMENT '修改人',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '创建用户姓名',
  `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改人姓名',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


-- 增加 栏目类型表
CREATE TABLE `hyc_c_column_type` (
  `id` varchar(32) NOT NULL COMMENT '栏目类型id',
  `type_name` varchar(50) NOT NULL COMMENT '栏目名称',
  `state` int(12) NOT NULL DEFAULT '0' COMMENT '状态:0:启用,1:禁用',
  `creation_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 增加 用户单位关联表
CREATE TABLE `hyc_c_user_depart` (
  `id` varchar(32) NOT NULL COMMENT '角色单位id',
  `fk_user_id` varchar(32) NOT NULL COMMENT '关联用户id',
  `fk_depart_id` varchar(32) NOT NULL COMMENT '单位id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 新增 banner表
CREATE TABLE `hyc_c_banner` (
  `id` varchar(32) NOT NULL COMMENT 'banner主键Id',
  `banner_theme` varchar(50) NOT NULL COMMENT '主题',
  `fk_column_id` varchar(32) NOT NULL COMMENT '关联栏目id',
  `hospital_id` varchar(32) DEFAULT NULL COMMENT '医院id',
  `departname` varchar(50) DEFAULT NULL COMMENT '所属机构名称',
  `fk_platform_id` varchar(32) DEFAULT NULL COMMENT '平台id',
  `state` int(12) NOT NULL COMMENT 'banner状态:0 发布,1:草稿',
  `public_time` datetime NOT NULL COMMENT '发布时间',
  `public_user` varchar(32) NOT NULL COMMENT '发布人',
  `expire_time` datetime NOT NULL COMMENT '过期时间',
  `create_user` varchar(32) NOT NULL COMMENT '添加人',
  `update_user` varchar(32) DEFAULT NULL COMMENT '修改人',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '创建用户姓名',
  `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改人姓名',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 新增banner 内容表
CREATE TABLE `hyc_c_banner_content` (
  `id` varchar(32) NOT NULL COMMENT 'banner内容Id',
  `fk_banner_Id` varchar(32) NOT NULL COMMENT '关联bannerId',
  `banner_keyword` varchar(100) NOT NULL COMMENT '关键字',
  `banner_name` varchar(50) NOT NULL COMMENT 'banner名称',
  `banner_url` varchar(300) DEFAULT NULL COMMENT 'banner图片地址',
  `sort` int(12) DEFAULT NULL COMMENT '排序',
  `banner_link_flag` int(12) NOT NULL COMMENT 'banner链接类型: 0:内联,1外联,2:无连接',
  `banner_link` varchar(300) DEFAULT NULL COMMENT 'banner链接',
  `banner_target` varchar(20) DEFAULT NULL COMMENT 'banner: html页面打开方式',
  `quote_flag` int(12) NOT NULL DEFAULT '0' COMMENT '是否被新闻关联:0:否,1是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 新增 新闻 表
CREATE TABLE `hyc_c_news` (
  `id` varchar(32) NOT NULL COMMENT '新闻表Id',
  `fk_column_Id` varchar(32) NOT NULL COMMENT '关联栏目id',
  `state` int(12) DEFAULT NULL COMMENT '新闻状态,0:发布,1:草稿,2:删除',
  `news_name` varchar(50) NOT NULL COMMENT '标题',
  `news_writer` varchar(50) NOT NULL COMMENT '作者',
  `news_keyword` varchar(100) DEFAULT NULL COMMENT '关键词',
  `news_source` varchar(300) DEFAULT NULL COMMENT '来源',
  `cover_link` varchar(300) DEFAULT NULL COMMENT '封面图',
  `news_digest` varchar(300) DEFAULT NULL COMMENT '摘要',
  `news_content` text NOT NULL COMMENT '新闻内容',
  `sort` int(12) DEFAULT NULL COMMENT '权重',
  `news_arrposid` int(12) NOT NULL DEFAULT '0' COMMENT '推荐位置  0:普通新闻，1：首页新闻,2:栏目新闻',
  `fk_platform_id` varchar(32) DEFAULT NULL COMMENT '关联平台id',
  `platform_name` varchar(50) DEFAULT NULL COMMENT '平台名称',
  `hospital_id` varchar(32) DEFAULT NULL COMMENT '医院Id',
  `departname` varchar(50) DEFAULT NULL COMMENT '所属机构名称',
  `public_time` datetime NOT NULL COMMENT '发布时间',
  `expire_time` datetime NOT NULL COMMENT '过期时间',
  `create_user` varchar(32) NOT NULL COMMENT '添加人',
  `update_user` varchar(32) DEFAULT NULL COMMENT '修改人',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '创建用户姓名',
  `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改人姓名',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 新增 banner 新闻 视频 学习资料 关联表
CREATE TABLE `hyc_c_banner_news` (
  `id` varchar(32) NOT NULL COMMENT 'banner新闻关联Id',
  `fk_banner_Id` varchar(32) NOT NULL COMMENT '关联bannerId',
  `fk_news_Id` varchar(32) DEFAULT NULL COMMENT '关联新闻Id',
  `fk_video_Id` varchar(32) DEFAULT NULL COMMENT '视频id',
  `fk_materials_id` varchar(32) DEFAULT NULL COMMENT '学习资料id',
  `type` int(12) NOT NULL COMMENT '关联类型:1 新闻,2:视频,3:学习资料',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 新增平台 默认 栏目 表
CREATE TABLE `hyc_c_default_column` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `p_id` varchar(32) DEFAULT NULL COMMENT '父id',
  `column_name` varchar(50) NOT NULL COMMENT '栏目名称',
  `fk_column_type` varchar(32) NOT NULL COMMENT '栏目类型',
  `state` int(12) NOT NULL COMMENT '0:启用,1:禁用',
  `column_code` varchar(32) NOT NULL COMMENT '栏目Code',
  `column_link` varchar(200) DEFAULT NULL COMMENT '栏目地址链接',
  `column_url` varchar(200) DEFAULT NULL COMMENT '栏目图片路径',
  `sort` int(12) DEFAULT NULL COMMENT '排序',
  `fk_platform_id` varchar(32) NOT NULL COMMENT '平台Id',
  `create_user` varchar(32) DEFAULT NULL COMMENT '编辑人',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '编辑人姓名',
  `update_user` varchar(32) DEFAULT NULL COMMENT '修改人',
  `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改人姓名',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


-- 新增平台 医院 视屏
CREATE TABLE `hyc_c_video` (
  `id` varchar(32) NOT NULL COMMENT '主键Id',
  `fk_column_id` varchar(32) NOT NULL COMMENT '关联栏目id',
  `video_titile` varchar(50) NOT NULL COMMENT '视频标题',
  `video_name` varchar(200) DEFAULT NULL COMMENT '视频名称',
  `video_link` varchar(200) DEFAULT NULL COMMENT '视屏封面图地址',
  `video_describe` varchar(100) DEFAULT NULL COMMENT '视屏描述',
  `charge_type` int(12) NOT NULL COMMENT '收费方式 0:免费 1:付费',
  `video_price` double DEFAULT NULL COMMENT '资料价格',
  `video_url` varchar(200) NOT NULL COMMENT '视屏链接地址',
  `state` int(12) NOT NULL COMMENT '1: 保存草稿 0:发布',
  `sort` int(12) DEFAULT NULL COMMENT '权重',
  `video_arrposid` int(12) NOT NULL COMMENT '视频推荐位置 0 首页,1栏目',
  `fk_platform_id` varchar(32) DEFAULT NULL COMMENT '平台id',
  `platform_name` varchar(50) DEFAULT NULL COMMENT '平台名称',
  `hospital_id` varchar(32) DEFAULT NULL COMMENT '医院id',
  `departname` varchar(50) DEFAULT NULL COMMENT '医院名称',
  `public_time` datetime NOT NULL COMMENT '发布时间',
  `expire_time` datetime NOT NULL COMMENT '过期时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '创建人名称',
  `update_user` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改人名称s',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 新增平台 医院 学习资料
CREATE TABLE `hyc_c_materials` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `fk_column_id` varchar(32) NOT NULL COMMENT '关联栏目id',
  `materials_name` varchar(50) DEFAULT NULL COMMENT '文件名称',
  `materials_link` varchar(300) DEFAULT NULL COMMENT '文件封面图',
  `materials_describe` varchar(100) DEFAULT NULL COMMENT '文件描述',
  `charge_type` int(12) NOT NULL COMMENT '收费方式',
  `materials_price` double DEFAULT NULL COMMENT '资料价格',
  `materials_att_name` varchar(50) DEFAULT NULL COMMENT '学习资料附件名称',
  `materials_url` varchar(300) DEFAULT NULL COMMENT '资料附件路径',
  `sort` int(12) DEFAULT NULL COMMENT '权重',
  `state` int(12) NOT NULL COMMENT '状态: 0:发布 1:草稿',
  `materials_arrposid` int(12) NOT NULL COMMENT '资料推荐位置',
  `fk_platform_id` varchar(32) NOT NULL COMMENT '关联平台Id',
  `platform_name` varchar(50) DEFAULT NULL COMMENT '平台名称',
  `hospital_id` varchar(32) DEFAULT NULL COMMENT '医院Id',
  `departname` varchar(50) DEFAULT NULL COMMENT '所属机构名称',
  `public_time` datetime NOT NULL COMMENT '发布时间',
  `expire_time` datetime NOT NULL COMMENT '过期时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改日期',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '创建人姓名',
  `update_user` varchar(32) DEFAULT NULL COMMENT '修改人',
  `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改人姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


--  --------------------------------------------- 已经执行 end  ------------------------------------------------------------




--  --------------------------------------------- 以下未执行 start 2019-1-13 ---------------------------------------------------------

-- 视频表增加 科室id,科室名称
alter table hyc_c_video add column section_id varchar(32)  COMMENT '科室id';
alter table hyc_c_video add column section_name varchar(50)  COMMENT '科室名称';
alter table hyc_c_video add column speechmaker varchar(50)  COMMENT '主讲人';
alter table hyc_c_video add column learn_number INTEGER(12) DEFAULT 0 COMMENT '观看次数';


-- 学习资料表增加  科室id,科室名称
alter table hyc_c_materials add column section_id varchar(32)  COMMENT '科室id';
alter table hyc_c_materials add column section_name varchar(50)  COMMENT '科室名称';
alter table hyc_c_materials add column speechmaker varchar(50)  COMMENT '主讲人';
alter table hyc_c_materials add column learn_number INTEGER(12) DEFAULT 0 COMMENT '观看次数';


-- --------------------------------------------- 以上未执行 end 2019-1-13 ------------------------------------------------------------
