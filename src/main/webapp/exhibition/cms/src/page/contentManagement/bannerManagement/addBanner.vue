<template>
	<div class="addBannerBox">
		<crumbs></crumbs>
		<div class="bannerInfo">
			<div class="bannerContent fl">
				<!-- <div class="title" style="margin-left:10px;"><i></i>内容</div> -->
				<div class="bannerContentLeft infoBox">
					<div class="min-title">主题</div>
					<input type="text" placeholder="请输入主题 最多10个字符" id="theme" v-model="bannerTheme" maxlength="10">
					<div class="title bannerNum">
						<!-- <i style="width:6px;height:6px;margin-right:10px;margin-top:19px;"></i> -->
						banner 添加<span>(<b>{{bannerContents.length}}</b>/<em>6</em>)</span>
					</div>
					<div class="editorBannerBox">
						<ul>
							<li v-for="(item,index) in bannerContents" :key="index" class="clearfix" :class="{ on:index===currentIndex?true:false}"
							 @click="isEditorStatus(index===currentIndex?true:false,index)">
								<div class="fl leftBoxContent">
									<div class="imgBox">
										<el-upload class="avatar-uploader" action="/HycCms/fileOperate/fileUpload.do" :disabled="index===currentIndex?false:true"
										 :show-file-list="false" :http-request="httpRequest" :before-upload="beforeAvatarUpload">
											<img v-if="item.bannerUrl" :src="item.bannerUrl" class="avatar">
											<i v-else class="el-icon-plus avatar-uploader-icon"></i>
										</el-upload>
									</div>
									<div class="editorInfo bannerInfoBox" v-if="item.editor">
										<input type="text" placeholder="banner名称 最多15个字符" id="bannerName" v-model="item.bannerName" maxlength="15">
										<input type="text" placeholder="请输入关键字，以英文逗号分隔!" id="keywords" v-model="item.bannerKeyword">
										<div class="urlType">
											<label for="inLink"><input type="radio" name="urlType" value="0" id="inLink" v-model="item.bannerLinkFlag"
												 @click="changeInfoStatus(index,'0','editor')">内链</label>
											<label for="outerLink"><input type="radio" name="urlType" value="1" id="outerLink" v-model="item.bannerLinkFlag"
												 @click="changeInfoStatus(index,'1','')">外链</label>
											<label for="outerLinkSpace"><input type="radio" name="urlType" value="2" id="outerLinkSpace" v-model="item.bannerLinkFlag"
												 @click="changeInfoStatus(index,'2','')">不添加</label>
										</div>
										<input type="text" id="editorBannerUrl" placeholder="请选择要关联的文章" :value="item.newscolumnName+' - '+item.newsName"
										 v-if="item.bannerLinkFlag=='0'" readonly @click="changeInfoStatus(index,'0','editor')">
										<input type="text" id="editorBannerUrl" placeholder="请输入链接地址" v-model="item.bannerLink" v-else-if="item.bannerLinkFlag=='1'">
										<select name="" id="openMethod" v-model="item.bannerTarget" v-show="item.bannerLinkFlag=='2'?false:true">
											<option value="">请选择链接打开方式</option>
											<option value="_blank">新窗口</option>
											<option value="_self">原窗口</option>
										</select>
										<div class="saveBannerInfo" @click="saveEditorBannerInfo(index)">完成</div>
									</div>
									<div class="bannerInfoBox bannerInfoBoxShow" v-else>
										<p class="bannerName">banner名称：{{item.bannerName}}</p>
										<p class="keywords">关键词：{{item.bannerKeyword}}</p>
										<p class="urlType">关联方式：{{bannerLinkFlagText(item.bannerLinkFlag)}}</p>
										<p class="bannerUrl bannerInfo" v-if="item.bannerLinkFlag==2?false:true">
											{{item.bannerLinkFlag==0?'关联文章':'关联地址'}}：
											{{item.bannerLinkFlag=='0'?item.newscolumnName+' - '+item.newsName:item.bannerLink}}
										</p>
										<p class="openMethod">打开方式：{{item.bannerTarget=='_blank'?'新窗口':'原窗口'}}</p>
									</div>
								</div>
								<div class="editorBox fr">
									<p :class="{on:index==0?true:false}" @click="resetSortBanner(index,item.sort,'stick')">置顶</p>
									<p :class="{on:index==0?true:false}" @click="resetSortBanner(index,item.sort,'up')">上移</p>
									<p :class="{on:index==bannerContents.length-1?true:false}" @click="resetSortBanner(index,item.sort,'down')">下移</p>
									<p @click="editorBtn(index)">编辑</p>
									<p @click="delBanner(index)">删除</p>
								</div>
							</li>
						</ul>
						<div class="addBannerInfoBox" v-if="editorStatus && bannerContents.length<6">
							<div class="imgBox">
								<el-upload class="avatar-uploader" action="/HycCms/fileOperate/fileUpload.do" :show-file-list="false"
								 :http-request="httpRequest" :before-upload="beforeAvatarUpload">
									<img v-if="currentFillInfo.bannerUrl" :src="currentFillInfo.bannerUrl" class="avatar">
									<i v-else class="el-icon-plus avatar-uploader-icon"><br><span>请上传jpeg或png格式文件 单张不超过2M<br>图片规格：通栏banner尺寸为1425*400
											分中心首页banner尺寸为680*400</span></i>
								</el-upload>
								<!-- <div>asdfasdfasdfafd</div> -->
							</div>
							<div class="editorInfo bannerInfoBox clearfix">
								<input type="text" placeholder="banner名称 最多15个字符" id="bannerName" v-model="currentFillInfo.bannerName"
								 maxlength="15">
								<input type="text" placeholder="请输入关键字，以英文逗号分隔!" id="keywords" v-model="currentFillInfo.bannerKeyword">
								<div class="urlType">
									<label for="inLink"><input type="radio" name="urlType" value="0" id="inLink" v-model="currentFillInfo.bannerLinkFlag"
										 @click="changeInfoStatus('','0','add')">内链</label>
									<label for="outerLink"><input type="radio" name="urlType" value="1" id="outerLink" v-model="currentFillInfo.bannerLinkFlag"
										 @click="changeInfoStatus('','1','')">外链</label>
									<label for="outerLinkSpace"><input type="radio" name="urlType" value="2" id="outerLinkSpace" v-model="currentFillInfo.bannerLinkFlag"
										 @click="changeInfoStatus('','2','')">不添加</label>
								</div>
								<input type="text" id="editorBannerUrl" placeholder="请选择要关联的文章" :value="currentFillInfo.newscolumnName+' - '+currentFillInfo.newsName"
								 v-if="currentFillInfo.bannerLinkFlag=='0'" readonly @click="changeInfoStatus('','0','add')">
								<input type="text" id="editorBannerUrl" placeholder="请输入链接地址" v-model="currentFillInfo.bannerLink" v-else-if="currentFillInfo.bannerLinkFlag=='1'">
								<select name="" id="openMethod" v-model="currentFillInfo.bannerTarget" v-show="currentFillInfo.bannerLinkFlag=='2'?false:true">
									<option value="">请选择打开方式</option>
									<option value="_blank">新窗口</option>
									<option value="_self">原窗口</option>
								</select>
								<div class="saveBannerInfo" @click="saveBannerInfo()">完成</div>
							</div>
						</div>
						<!-- 关联弹出层 S -->
						<div v-show="isRelevanceInfo" class="relevanceNews">
							<!-- <div class="closeBtn" @click="closeBtn"></div> -->
							<div class="relevanceNewsBox">
								<div class="articleTitle clearfix">
									<div class="type_search fl">
										<select name="" id="" v-model="columnType" @change="getColumnInfoList()">
											<option value="">请选择类型</option>
											<option :value="item.id" v-for="item in columnTypeList" :key="item.id">{{item.typeName}}</option>
										</select>
										<select name="" id="" v-model="newsColumnId" @change="getArticleList()">
											<option value="">请选择栏目</option>
											<option :value="item.id" v-for="item in columnList" :key="item.id">{{item.columnName}}</option>
										</select>
										<input type="text" placeholder="请输入标题或栏目名称！" v-model="searchKeyWords">
										<i @click="getArticleList()"></i>
									</div>
									<div class="sureBtn fr" @click="sureArticle()">确认</div>
									<div class="sureBtn fr" style="background:#999;" @click="closeBtn()">取消</div>
								</div>
								<div class="articleListBox">
									<ul>
										<li class="articleInfo clearfix" v-if="columnType == '192880e64e1ef94d014e1efefc340003'" v-for="(item,index) in articleList" :key="item.id" :class="{on:index == currentArticleNum}"
										 @click="active(index,item.id,item.newsName)">
											<p class="fl">{{item.newsName}}</p>
											<span class="fr">{{item.updateTime?item.updateTime.substr(0,10):''}}</span>
										</li>
										<li class="articleInfo clearfix" v-if="columnType == '662880e64e1ef94d014e1efefc360006'" v-for="(item,index) in articleList" :key="item.id" :class="{on:index == currentArticleNum}"
										 @click="active(index,item.id,item.videoTitile)">
											<p class="fl">{{item.videoTitile}}</p>
											<span class="fr">{{item.updateTime?item.updateTime.substr(0,10):''}}</span>
										</li>
										<li class="articleInfo clearfix" v-if="columnType == '882880e64e1ef94d014e1efefc3e0008'" v-for="(item,index) in articleList" :key="item.id" :class="{on:index == currentArticleNum}"
										 @click="active(index,item.id,item.materialsName)">
											<p class="fl">{{item.materialsName}}</p>
											<span class="fr">{{item.updateTime?item.updateTime.substr(0,10):''}}</span>
										</li>
									</ul>
								</div>
								<el-pagination v-show="totalPage>1" @current-change="handleCurrentChange" :current-page.sync="currentPage"
								 :page-size="showCount" :page-count="totalPage" layout="total, prev, pager, next, jumper" :total="totalResult">
								</el-pagination>
							</div>
						</div>
						<!-- 关联弹出层 E -->
					</div>
				</div>
			</div>
			<div class="bannerSet fr">
				<div class="title">
					<!-- <i style="background-color:#29a2fb;"></i> -->
					发布设置
				</div>
				<div class="bannerContentRight infoBox cb">
					<p>发布时间</p>
					<el-date-picker v-model="publicTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="2018-1-1"
					 :picker-options="startDateOpt" default-time="00:00:00">
					</el-date-picker>
					<p>截止时间</p>
					<el-date-picker v-model="expireTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="2030-12-31"
					 :picker-options="endDateOpt" default-time="23:59:59">
					</el-date-picker>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="foo_btn">
				<div class="save" @click="cancel">取消</div>
				<div class="save" @click="bannerSave('draft')">保存草稿</div>
				<div class="preview" @click="preview()" v-show="bannerContents.length>0">预览</div>
				<div class="bannerSave" @click="bannerSave()">发布</div>
			</div>
		</div>
		<div class="bannerRunBox" v-show="isShow">
			<div class="banner_content">
				<div class="colseBtn" @click="isShowBtn"><i></i><i></i></div>
				<el-carousel indicator-position="outside">
					<el-carousel-item v-for="item in bannerContents" :key="item.id">
						<div class="imgUrl">
							<img :src="item.bannerUrl" alt="">
						</div>
					</el-carousel-item>
				</el-carousel>
			</div>
		</div>
	</div>
</template>

<script>
	import crumbs from "../../../components/crumbs";
	import {
		api_findBannerInfo,
		api_findArticleColumnType,
		api_findArticleColumnList,
		api_getArticleList,
		api_delBanner,
		api_commitBannerInfo,
		api_uploadFile,
		api_findVideoList,
		api_findLearnList
	} from "../../../axios/apis.js";
	export default {
		name: "addBanner",
		components: { crumbs },
		data() {
			return {
				fkPlatformId: "", // 平台id
				hospitalId: "", // 机构id
				departname: "", // 机构名称
				fkColumnId: "", // 栏目id
				bannerTheme: "", // 主题
				publicTime: "", // 发布时间
				expireTime: "2030-12-31 23:59:59", // 截止时间
				state: "", // 0发布 1草稿
				bannerContents: [], // 已添加的banner
				id: "", // 当前栏目 id
				// banner回显 及添加时的数据
				currentFillInfo: {
					urlAddress: false, // 是否显示连接地址栏
					editor: false, // 当前banner是否被编辑
					bannerUrl: "", // banner地址
					bannerName: "", // banner名称
					bannerKeyword: "", // banner关键词
					bannerLinkFlag: "2", // banner连接方式
					bannerLinkFlagText: "不添加", //
					bannerLink: "", // 外链地址
					sort: "", // 权重
					relationObjId: "", // 内联文章id
					newsName: "", // 内联文章名称
					newsColumnId: "", // 新闻栏目id
					newscolumnName: "", // 新闻栏目名称
					bannerTarget: "", // banner连接打开方式
					id: "" // banner id
				},

				// 本地添加的数据处理后 提交到后端的数组
				bannerList: [],

				// 编辑banner
				currentIndex: null, // 当前编辑的banner状态修改  控制on的显示
				editorStatus: true, // 有banner处于编辑状态时 新增banner模块隐藏 true：可编辑

				// 关联文章
				isRelevanceInfo: false, // 是否显示文章关联弹出 选中内联时 展开文章关联弹框
				columnTypeList: [], // 关联文章时 请求到的可关联栏目类型列表
				columnList: [], // 关联文章时 请求到的可关联栏目列表
				articleList: [], // 关联文章时 请求到的可关联文章列表

				columnType: "", // 关联文章时 当前选中目类型 id
				newsColumnId: "", // 关联文章时 当前选中栏目名称 id
				newscolumnName: "", // 关联文章时 当前选中新闻名称
				searchKeyWords: "", // 关联文章时 搜索文章标题的关键词
				// 关联选中的文章信息
				currentArticleNum: null, // 当前可关联文章列表中 选中的文章
				currentArticleId: "", // 选中的文章id
				currentArticleName: "", // 选中的文章名称
				currentArticleStatus: "", // 关联文章时 处于什么状态  编辑状态/新增状态
				// 关联弹层 分页相关
				currentPage: 1, // 关联文章时 当前页码
				totalResult: null, // 关联文章时 查询到的文章条数
				totalPage: null, // 关联文章时 总页数
				showCount: 10, // 关联文章时 每页显示条数

				isShow: false, // 预览弹出  默认false
				bannerContentsLength: "",
				startDateOpt: {},
				endDateOpt: {}
			};
		},
		mounted: function () {
			// 获取路由传递的参数
			this.fkPlatformId = this.$route.query.fkPlatformId;
			this.hospitalId = this.$route.query.hospitalId;
			this.departname = this.$route.query.departname;
			this.fkColumnId = this.$route.query.fkColumnId;

			this.init();
		},
		computed: {
			bannerLinkFlagText(bannerLinkFlag) {
				return function (bannerLinkFlag) {
					if (bannerLinkFlag == "0") {
						return "内联";
					} else if (bannerLinkFlag == "1") {
						return "外联";
					} else {
						return "不添加";
					}
				};
			}
		},
		methods: {
			init: function () {
				// 查询banner 初始化页面
				this.findBanner();
				// 查询关联栏目类型
				this.getColumnInfo();
			},
			// 取消
			cancel() {
				this.$router.go(-1);
			},
			// 获取当前时间
			getCurrentTime() {
				this.ct_month = new Date();
				// this.ct_month.setTime(
				// 	this.ct_month.getTime() - 3600 * 1000 * 24 * 30
				// ); //获取上个月的日期（这一行去掉就是获取今天计算机上的日期了）
				var now = this.ct_month;
				var year = now.getFullYear(); //年
				var month = now.getMonth() + 1; //月
				var day = now.getDate(); //日
				var hh = now.getHours(); //时
				var mm = now.getMinutes(); //分
				var ss = now.getSeconds(); //秒
				var clock = year + "-";
				if (month < 10) {
					clock += "0";
				}
				clock += month + "-";
				if (day < 10) {
					clock += "0";
				}
				clock += day + " ";
				if (hh < 10) {
					clock += "0";
				}
				clock += hh + ":";
				if (mm < 10) {
					clock += "0";
				}
				clock += mm + ":";
				if (ss < 10) {
					clock += "0";
				}
				clock += ss;
				this.publicTime = clock;
			},
			// 查询banner
			findBanner: function () {
				let _this = this;
				api_findBannerInfo({
					platformId: this.fkPlatformId,
					hospitalId: this.hospitalId,
					columnId: this.fkColumnId
				})
					.then(res => {
						if (res.flag == "success" && res.obj) {
							this.publicTime = res.obj.publicTime;
							this.expireTime = res.obj.expireTime;
							this.bannerTheme = res.obj.bannerTheme;
							this.state = res.obj.state;
							this.bannerContents = res.obj.bannerContents;
							this.bannerContentsLength = res.obj.bannerContents.length;
							this.id = res.obj.id;
							this.bannerContents.forEach(item => {
								item.urlAddress = false;
								item.editor = false;
							});
						} else {
							this.getCurrentTime();
						}
					})
					.then(() => {
						// 开始时间
						this.startDateOpt.disabledDate = function (time) {
							// 设置可选择的日期为 当前时间到结束时间之间的时间
							return (
								time.getTime() < Date.now() - 8.64e7 ||
								time.getTime() >
								new Date(_this.expireTime).getTime()
							);
						};
						// 结束时间
						this.endDateOpt.disabledDate = function (time) {
							// 设置可选择的日期为 当前时间到结束时间之间的时间
							return (
								time.getTime() <
								new Date(_this.publicTime).getTime()
							);
						};
					})
					.catch(error => console.log(error));
			},
			// 上传文件时
			changeFile(event, file, fileList) {
				console.log(event, file, fileList);
			},
			// 上传文件判断 大小/格式
			beforeAvatarUpload(file) {
				const isJPG =
					file.type === "image/jpeg" || file.type === "image/png";
				const isLt2M = file.size / 1024 / 1024 < 2;
				if (!isJPG) {
					this.$message.error("banner图只能上传 JPG或png 格式!");
				}
				if (!isLt2M) {
					this.$message.error("banner图单张大小不能超过 2MB!");
				}
				return isJPG && isLt2M;
			},
			// 自定义文件上传方法
			httpRequest(options) {
				let param = new FormData(); // 创建form对象
				param.append("img", options.file); // file对象是 beforeUpload参数
				// 添加请求头
				api_uploadFile(param)
					.then(res => {
						if (
							this.currentIndex != undefined &&
							this.currentIndex != null &&
							this.currentIndex != ""
						) {
							this.bannerContents[this.currentIndex].bannerUrl = res.obj[0].ossUrl;
						} else {
							this.currentFillInfo.bannerUrl = res.obj[0].ossUrl;
						}
					})
					.catch(err => console.log(err));
			},
			// 修改当前关联信息页面显示状态
			changeInfoStatus: function (index, status, type) {
				this.currentArticleStatus = type;
				if (status == "0") {
					this.isRelevanceInfo = true;
					this.currentFillInfo.bannerLinkFlagText = "内链";
				} else if (status == "1") {
					this.currentFillInfo.bannerLinkFlagText = "外链";
				} else {
					this.currentFillInfo.bannerLinkFlagText = "不添加";
				}
			},
			// 查询可关联文章栏目类型
			getColumnInfo: function () {
				api_findArticleColumnType()
				.then(res => {
					if (res.flag == "success") {
						this.columnTypeList = res.obj;
						// this.columnType = this.columnTypeList[0].id;
						// this.getColumnInfoList();
					}
				})
				.catch(error => console.log(error));
			},
			// 查询能关联的文章栏目
			getColumnInfoList: function () {
				this.columnList = [];
				this.articleList = [];
				this.currentArticleId = "";
				this.currentArticleName = "";
				this.currentArticleNum = null;
				this.newsColumnId = '';
				this.newscolumnName = '';

				api_findArticleColumnList({
					platformId: this.fkPlatformId,
					hospitalId: this.hospitalId,
					columnTypeId: this.columnType
				})
				.then(res => {
					if (res.flag == "success" && res.obj.length > 0) {
						this.columnList = res.obj;
					}
				})
			},
			// 查询可关联文章列表
			getArticleList: function () {
				this.articleList = [];
				this.currentArticleId = "";
				this.currentArticleName = "";
				this.currentArticleNum = null;

				this.columnList.forEach( item => {
					if (item.id == this.newsColumnId) {
						this.newscolumnName = item.columnName;
					}
				});

				if(this.newsColumnId){
					if(this.columnType == '192880e64e1ef94d014e1efefc340003'){
						api_getArticleList({
							columnId: this.newsColumnId,
							newsName: this.searchKeyWords
						})
						.then(res => {
							if (res.flag == "success") {
								this.articleList = res.obj.list;
								this.currentPage = res.obj.currentPage;
								this.totalResult = res.obj.totalResult;
								this.showCount = res.obj.showCount;
								this.totalPage = res.obj.totalPage;
							}
						})
					}else if(this.columnType == '662880e64e1ef94d014e1efefc360006'){
						api_findVideoList({
							columnId: this.newsColumnId,
							newsName: this.searchKeyWords
						})
						.then(res => {
							if (res.flag == "success") {
								this.articleList = res.obj;
								this.currentPage = res.obj.currentPage;
								this.totalResult = res.obj.totalResult;
								this.showCount = res.obj.showCount;
								this.totalPage = res.obj.totalPage;
							}
						})
					}else if(this.columnType == '882880e64e1ef94d014e1efefc3e0008'){
						api_findLearnList({
							columnId: this.newsColumnId,
							newsName: this.searchKeyWords
						})
						.then(res => {
							if (res.flag == "success") {
								this.articleList = res.obj;
								this.currentPage = res.obj.currentPage;
								this.totalResult = res.obj.totalResult;
								this.showCount = res.obj.showCount;
								this.totalPage = res.obj.totalPage;
							}
						})
					}
				}
			},
			// 关闭关联选择
			closeBtn: function () {
				this.isRelevanceInfo = false;

				this.columnType = '';
				this.columnList = [];
				this.articleList = [];
				this.currentArticleId = "";
				this.currentArticleName = "";
				this.currentArticleNum = null;
				this.newsColumnId = '';
				this.newscolumnName = '';
			},
			// 翻页
			handleCurrentChange(num) {
				this.currentPage = num;
				this.getColumnList();
			},
			// 确认文章选择
			sureArticle: function () {
				if (!this.currentArticleId) {
					this.$message({
						message: "请选择需要关联的文章",
						type: "warning",
						duration: 1500
					});
					return;
				}
				if (this.currentArticleStatus == "editor") {
					this.bannerContents[this.currentIndex].newsColumnId = this.newsColumnId;
					this.bannerContents[this.currentIndex].newscolumnName = this.newscolumnName;
					// this.bannerContents[this.currentIndex].newsId = this.currentArticleId;
					this.bannerContents[this.currentIndex].newsName = this.currentArticleName;
					this.bannerContents[this.currentIndex].relationObjId = this.currentArticleId;
					this.bannerContents[this.currentIndex].columnTypeId = this.columnType;
				} else {
					this.currentFillInfo.newsColumnId = this.newsColumnId;
					this.currentFillInfo.newscolumnName = this.newscolumnName;
					// this.currentFillInfo.newsId = this.currentArticleId;
					this.currentFillInfo.newsName = this.currentArticleName;
					this.currentFillInfo.relationObjId = this.currentArticleId;
					this.currentFillInfo.columnTypeId = this.columnType;
				}
				this.closeBtn();
			},
			// 点击选中当前文章
			active: function (index, id, name) {
				this.currentArticleNum = index;
				this.currentArticleId = id;
				this.currentArticleName = name;
			},
			// 编辑banner
			editorBtn: function (index) {
				// 判断是否有编辑状态的banner
				if (!this.isEditorBanner()) {
					return;
				}
				this.bannerContents[index].editor = true;
				this.currentIndex = index;
				this.editorStatus = false;
			},
			// 判断是否存在编辑中的banner
			isEditorBanner: function () {
				let status = this.bannerContents.every(item => {
					return item.editor == false;
				});
				if (!status) {
					this.$message({
						message: "有banner仍然在编辑状态,请先完成编辑！",
						type: "warning",
						duration: 1500
					});
				}
				return status;
			},
			// 判断当前状态是否存在编辑中的banner
			isEditorStatus: function (status, index) {
				if (!status) {
					this.isEditorBanner();
				}
			},
			// 完成banner信息  追加到已完成编辑列表中
			saveBannerInfo: function () {
				if (!this.currentFillInfo.bannerUrl) {
					this.$message({
						message: "请上传banner图片",
						type: "warning",
						duration: 1500
					});
					return;
				}
				if (!this.currentFillInfo.bannerName) {
					this.$message({
						message: "请填写banner名称",
						type: "warning",
						duration: 1500
					});
					return;
				}
				if (this.currentFillInfo.bannerLinkFlag == "0") {
					if (!this.currentFillInfo.relationObjId) {
						this.$message({
							message: "请选择关联文章",
							type: "warning",
							duration: 1500
						});
						return;
					}
					if (!this.currentFillInfo.bannerTarget) {
						this.$message({
							message: "请选择链接打开方式",
							type: "warning",
							duration: 1500
						});
						return;
					}
				} else if (this.currentFillInfo.bannerLinkFlag == "1") {
					if (!this.currentFillInfo.bannerLink) {
						this.$message({
							message: "请填写链接地址",
							type: "warning",
							duration: 1500
						});
						return;
					}
					if (!this.currentFillInfo.bannerTarget) {
						this.$message({
							message: "请选择链接打开方式",
							type: "warning",
							duration: 1500
						});
						return;
					}
				}
				this.currentFillInfo.sort = this.bannerContents.length;
				var currentFillInfo = JSON.parse(
					JSON.stringify(this.currentFillInfo)
				);
				this.bannerContents.push(currentFillInfo);
				this.currentIndex = "";
				for (const item in this.currentFillInfo) {
					if (this.currentFillInfo.hasOwnProperty(item)) {
						this.currentFillInfo[item] = "";
					}
				}
			},
			// 数组方法
			swapArray: function (arr, index1, index2) {
				arr[index1] = arr.splice(index2, 1, arr[index1])[0];
				return arr;
			},
			// 重置顺序
			resetSortBanner: function (index, sort, event) {
				// 判断是否有编辑状态的banner
				if (!this.isEditorBanner()) {
					return;
				}
				if (event == "stick") {
					var moveNum = index - 0;
					for (let i = 0; i < moveNum; i++) {
						this.bannerContents = this.swapArray(
							this.bannerContents,
							index,
							index - 1
						);
						index--;
					}
				}
				if (event == "up") {
					this.bannerContents = this.swapArray(
						this.bannerContents,
						index,
						index - 1
					);
				}
				if (event == "down") {
					this.bannerContents = this.swapArray(
						this.bannerContents,
						index,
						index + 1
					);
				}
			},
			// 保存编辑后的banner信息
			saveEditorBannerInfo: function (index) {
				if (!this.bannerContents[index].bannerUrl) {
					this.$message({
						message: "请上传banner图片",
						type: "warning",
						duration: 1500
					});
					return;
				}
				if (!this.bannerContents[index].bannerName) {
					this.$message({
						message: "请填写banner名称",
						type: "warning",
						duration: 1500
					});
					return;
				}
				if (this.bannerContents[index].bannerLinkFlag == "0") {
					if (!this.bannerContents[index].relationObjId) {
						this.$message({
							message: "请选择关联文章",
							type: "warning",
							duration: 1500
						});
						return;
					}
					if (!this.bannerContents[index].bannerTarget) {
						this.$message({
							message: "请选择链接打开方式",
							type: "warning",
							duration: 1500
						});
						return;
					}
				} else if (this.bannerContents[index].bannerLinkFlag == "1") {
					if (!this.bannerContents[index].bannerLink) {
						this.$message({
							message: "请填写链接地址",
							type: "warning",
							duration: 1500
						});
						return;
					}
					if (!this.bannerContents[index].bannerTarget) {
						this.$message({
							message: "请选择链接打开方式",
							type: "warning",
							duration: 1500
						});
						return;
					}
				}
				this.bannerContents[index].editor = false;
				this.editorStatus = true;
				this.currentIndex = null;
			},
			// 删除已添加的banner
			delBanner(index) {
				// 判断是否有编辑状态的banner
				if (!this.isEditorBanner()) {
					return;
				}
				if (this.bannerContents[index].id) {
					api_delBanner({
						bannerContentId: this.bannerContents[index].id,
						bannerFlag: this.bannerContents[index].quoteFlag
					})
						.then(res => {
							this.$message({
								message: res.msg,
								type: res.flag == "error" ? "error" : "success",
								duration: 1500
							});
							if (res.flag == "success") {
								this.bannerContents.splice(index, 1);
							}
						})
						.catch(error => console.log(error));
				} else {
					this.bannerContents.splice(index, 1);
				}
			},
			// 判断是否为存草稿 并处理数据
			bannerSave: function (type) {
				// // 判断是否有未添加完成的banner
				// for (const item in this.currentFillInfo) {
				// 	if (this.currentFillInfo.hasOwnProperty(item)) {
				// 		const element = this.currentFillInfo[item];
				// 		if (element != false || element != "") {
				// 			this.$message({
				// 				message: "存在未添加完成的banner",
				// 				type: "warning",
				// 				duration: 1500
				// 			});
				// 			return;
				// 		}
				// 	}
				// }

				// 判断是否有编辑状态的banner
				if (!this.isEditorBanner()) {
					return;
				}
				if (this.bannerContents.length > 0) {
					this.bannerList = [];
					for (let i = 0; i < this.bannerContents.length; i++) {
						let objKeyArr = {};
						let element = this.bannerContents[i];
						objKeyArr.bannerKeyword = element.bannerKeyword;
						objKeyArr.bannerName = element.bannerName;
						objKeyArr.bannerUrl = element.bannerUrl;
						objKeyArr.sort = i;
						objKeyArr.bannerLinkFlag = element.bannerLinkFlag;
						objKeyArr.bannerLink = element.bannerLink;
						objKeyArr.bannerTarget = element.bannerTarget;
						objKeyArr.bannerContentId = element.id;
						objKeyArr.relationObjId = element.relationObjId;
						objKeyArr.columnTypeId = element.columnTypeId;
						this.bannerList.push(objKeyArr);
					}
				}
				if (!this.bannerTheme) {
					this.$message({
						message: "请输入主题",
						type: "warning",
						duration: 1500
					});
					return;
				}
				if (this.bannerContents.length < 1) {
					this.$message({
						message: "请上传banner信息",
						type: "warning",
						duration: 1500
					});
					return;
				}
				if (!this.publicTime) {
					this.$message({
						message: "请选择发布时间",
						type: "warning",
						duration: 1500
					});
					return;
				}
				if (type == "draft") {
					this.state = 1;
					if (this.id) {
						this.commitBannerInfo("/HycCms/banner/updateBanner.do");
					} else {
						this.commitBannerInfo("/HycCms/banner/addBanner.do");
					}
				} else {
					this.state = 0;
					if (this.id) {
						this.commitBannerInfo("/HycCms/banner/updateBanner.do");
					} else {
						this.commitBannerInfo("/HycCms/banner/addBanner.do");
					}
				}
			},
			// 新增/修改banner信息
			commitBannerInfo: function (url) {
				api_commitBannerInfo(url, {
					bannerJson: JSON.stringify({
						bannerTheme: this.bannerTheme,
						fkColumnId: this.fkColumnId,
						hospitalId: this.hospitalId,
						departname: this.departname,
						fkPlatformId: this.fkPlatformId,
						publicTime: this.publicTime,
						expireTime: this.expireTime,
						bannerContents: this.bannerList,
						state: this.state,
						id: this.id
					})
				})
					.then(res => {
						this.$message({
							message: res.msg,
							type: res.flag == "error" ? "error" : "success",
							duration: 1500
						});
						if (res.flag == "success") {
							// this.findBanner()
							setTimeout(() => {
								this.$router.go(-1);
							}, 1500);
						}
					})
					.catch(error => {
						console.log(error);
					});
			},
			// 关闭预览
			isShowBtn: function () {
				this.isShow = false;
			},
			// 展开预览
			preview: function () {
				this.isShow = true;
			}
		}
	};
</script>

<style scoped>
	.addBannerBox {
		width: 100%;
		height: 100%;
		padding-right: 12px;
		padding-left: 2px;
		overflow: scroll;
	}

	.bannerInfo {
		width: 100%;
		height: calc(100% - 60px);
	}

	.title {
		text-align: left;
		font-size: 16px;
		color: #333333;
		height: 20px;
		line-height: 20px;
		margin-bottom: 15px;
	}

	.title i {
		display: block;
		float: left;
		width: 8px;
		height: 8px;
		margin-top: 6px;
		border-radius: 50%;
		background-color: #9992fd;
		margin-right: 22px;
	}

	.bannerContent {
		width: 60%;
	}

	.bannerSet {
		width: calc(40% - 15px);
	}

	.bannerContentLeft {
		padding-left: 10px;
		padding-right: 20px;
	}

	.bannerContentRigth {
		width: 40%;
	}

	.infoBox {
		box-shadow: 0 0 8px #ddd;
		padding-top: 10px;
		text-align: left;
	}

	#column {
		width: 100%;
		height: 40px;
		line-height: 40px;
		border-color: #ccc;
	}

	.min-title {
		height: 30px;
		line-height: 30px;
		font-size: 14px;
		color: #666666;
		text-align: left;
	}

	#keywords,
	#theme,
	#bannerName,
	#editorBannerUrl,
	#addKeywords,
	#addBannerName,
	#addOpenMethod {
		width: calc(100% - 60px);
		height: 40px;
		line-height: 40px;
		font-size: 14px;
		color: #333;
		text-align: left;
		border: 1px solid #ccc;
		padding: 0 5px;
		margin-top: 10px;
	}

	#keywords,
	#bannerName,
	#editorBannerUrl {
		width: 100%;
	}

	#editorBannerUrl {
		margin-top: 0;
	}

	.bannerNum b {
		font-weight: normal;
		color: #ee5535;
	}

	.bannerNum {
		height: 44px;
		line-height: 44px;
	}

	.editorBannerBox {
		width: 100%;
		padding-bottom: 15px;
		margin-bottom: 20px;
	}

	.editorBannerBox ul {
		width: 100%;
	}

	.editorBannerBox>ul>li {
		width: 100%;
		margin-bottom: 20px;
		padding-bottom: 20px;
		border-bottom: 1px dashed #666;
	}

	.editorBannerBox>ul>li.on .editorBox p {
		cursor: default;
		pointer-events: none;
		opacity: 0.5;
	}

	.editorBannerBox>ul>li .editorBox p.on {
		cursor: default;
		pointer-events: none;
		opacity: 0.5;
	}

	/* .editorBannerBox li:last-child{
        border-bottom: none;
        margin-bottom: 0;
    } */
	.leftBoxContent {
		width: calc(100% - 60px);
	}

	.imgBox {
		width: 100%;
		height: 200px;
		position: relative;
		background-color: #f0f0f0;
		border: 1px dashed #ccc;
		line-height: 200px;
		overflow: hidden;
	}

	.imgBox .delBtn {
		position: absolute;
		right: -8px;
		top: -8px;
		display: block;
		width: 24px;
		height: 24px;
		background-image: url(../../../assets/images/del_icon.png);
		background-size: 100%;
		cursor: pointer;
	}

	.imgBox img {
		/* width: 100%; */
		vertical-align: middle;
	}

	#openMethod {
		width: 100%;
		height: 40px;
		line-height: 40px;
		margin-top: 10px;
		border-color: #cccccc;
		color: #333;
	}

	.urlType {
		text-align: left;
		height: 40px;
		line-height: 40px;
	}

	.urlType label {
		line-height: 40px;
		cursor: pointer;
	}

	.urlType input {
		display: inline-block;
		margin-right: 5px;
	}

	.bannerInfoBoxShow {
		padding-top: 10px;
	}

	.bannerInfoBoxShow p,
	.uploading {
		font-size: 14px;
		color: #666;
		text-align: left;
		text-align: left;
		height: 30px;
		line-height: 30px;
		word-break: break-all;
	}

	.bannerInfoBoxShow .bannerInfo {
		height: auto;
		line-height: 20px;
	}

	.uploading {
		height: 40px;
		line-height: 40px;
		margin-top: 10px;
	}

	#imageFile {
		width: 200px;
		margin-top: 8px;
	}

	.editorBox {
		width: 60px;
		height: 200px;
		padding-top: 20px;
	}

	.editorBox p {
		height: 30px;
		line-height: 30px;
		font-size: 14px;
		color: #333;
		padding-left: 15px;
		text-align: left;
		cursor: pointer;
	}

	.editorBox p:hover {
		color: #4c48e8;
	}

	.editorBox p.on {
		color: #4c48e8;
	}

	.addbtn {
		display: block;
		width: 50px;
		height: 80px;
		margin: 0 auto;
		background-image: url(../../../assets/images/add_icon.png);
		background-size: 100% auto;
		background-position: center;
		background-repeat: no-repeat;
	}

	.imgBox p {
		line-height: 40px;
		font-size: 14px;
		color: #666;
	}

	#image-file {
		position: absolute;
		top: 0;
		right: 0;
		bottom: 0;
		left: 0;
		z-index: 1;
		opacity: 0;
		width: 100%;
		height: 100%;
	}

	.addBannerInfoBox {
		width: calc(100% - 60px);
	}

	.addBannerInfoBox.on {
		cursor: default;
		pointer-events: none;
		opacity: 0.8;
	}

	.bannerSet {
		height: 100%;
	}

	.bannerContentRight {
		height: 100%;
		text-align: left;
		padding-left: 15px;
		padding-bottom: 15px;
		padding-right: 10px;
	}

	.bannerContentRight p {
		height: 40px;
		line-height: 40px;
		font-size: 14px;
	}

	.bannerContentRight input {
		height: 40px;
		line-height: 40px;
		border: 1px solid #ccc;
		padding: 0 5px;
		width: 100%;
	}

	.saveBannerInfo {
		height: 22px;
		width: 40px;
		line-height: 22px;
		font-size: 14px;
		background: #4c48e8;
		border-radius: 4px;
		color: #fff;
		float: right;
		margin-top: 20px;
		cursor: pointer;
		text-align: center;
	}

	.foo_btn {
		margin: 30px 0;
		text-align: center;
	}

	.foo_btn div {
		width: 120px;
		height: 38px;
		border-radius: 4px;
		text-align: center;
		line-height: 38px;
		font-size: 14px;
		display: inline-block;
		background-color: #9992fd;
		color: #fff;
		cursor: pointer;
		margin: 0 12px;
	}

	.foo_btn div:hover {
		background-color: #29a2fb;
	}

	.avatar-uploader {
		width: 100%;
		height: 100%;
		position: relative;
	}

	.avatar-uploader .el-upload {
		border: 1px dashed #d9d9d9;
		border-radius: 6px;
		cursor: pointer;
		position: relative;
		overflow: hidden;
		width: 100%;
		height: 100%;
	}

	.avatar-uploader .el-upload:hover {
		border-color: #409eff;
	}

	.avatar-uploader-icon {
		font-size: 28px;
		color: #8c939d;
		width: 100%;
		height: 178px;
		line-height: 30px;
		text-align: center;
		position: absolute;
		top: 0;
		bottom: 0;
		left: 0;
		right: 0;
		margin: auto;
		padding-top: 40px;
	}

	.avatar-uploader-icon span {
		font-size: 14px;
		line-height: 20px;
	}

	.avatar {
		position: absolute;
		top: 0;
		bottom: 0;
		left: 0;
		right: 0;
		margin: auto;
		width: 100%;
		/* height: 100%; */
		display: block;
	}

	.relevanceNews {
		position: fixed;
		width: 100%;
		height: 100%;
		top: 0;
		bottom: 0;
		right: 0;
		left: 0;
		margin: auto;
		background: rgba(0, 0, 0, 0.5);
		z-index: 10;
	}

	.closeBtn {
		position: absolute;
		z-index: 1;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
	}

	.relevanceNewsBox {
		width: 650px;
		height: 360px;
		border-radius: 6px;
		background-color: #ffffff;
		position: absolute;
		top: 0;
		right: 0;
		bottom: 0;
		left: 0;
		z-index: 2;
		margin: auto;
		padding: 12px 10px;
		border: 1px solid #ccc;
		text-align: center;
	}

	.type_search {
		font-size: 0;
		border-radius: 6px;
		border-left: none;
		height: 40px;
		position: relative;
	}

	.type_search select,
	.type_search input {
		height: 40px;
		margin: 0;
		border: 1px solid #ccc;
		font-size: 14px;
		margin-top: -1px;
		float: left;
		width: 120px;
		margin-right: 15px;
		border-radius: 4px;
	}

	.type_search input {
		padding-left: 5px;
		width: 180px;
		padding-right: 24px;
	}

	.type_search i {
		display: block;
		position: absolute;
		right: 19px;
		top: 3px;
		bottom: 0;
		margin: auto;
		width: 19px;
		height: 24px;
		background: url(../../../assets/images/big_icon.png) no-repeat center/cover;
		cursor: pointer;
	}

	.sureBtn {
		width: 60px;
		height: 30px;
		line-height: 30px;
		font-size: 14px;
		color: #fff;
		background: #4c48e8;
		border-radius: 4px;
		margin: 4px;
		text-align: center;
		cursor: pointer;
	}

	.articleTitle {
		height: 40px;
	}

	.articleListBox {
		margin-top: 10px;
		text-align: left;
		max-height: 240px;
		margin-bottom: 12px;
	}

	.articleListBox ul {
		width: 100%;
	}

	.articleListBox li {
		padding: 0 5px;
		border-radius: 4px;
	}

	.articleListBox li.on {
		background: #29a2fb;
	}

	.articleListBox li.on * {
		color: #fff;
	}

	.articleListBox h1 {
		font-size: 16px;
		color: #212022;
		margin-bottom: 5px;
	}

	.articleInfo {
		font-size: 14px;
		color: #444;
		height: 30px;
		line-height: 30px;
		cursor: pointer;
	}

	.articleInfo p {
		max-width: 400px;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}

	.articleInfo span {
		color: #999;
	}

	.articleInfo:hover {
		background: #ddd;
	}

	/* banner轮播预览 */
	.bannerRunBox {
		position: fixed;
		top: 0;
		bottom: 0;
		right: 0;
		left: 0;
		margin: auto;
		width: 100%;
		height: 100%;
		background: rgba(0, 0, 0, 0.8);
	}

	.colseBtn {
		cursor: pointer;
		width: 30px;
		height: 30px;
		right: -30px;
		top: -30px;
		background: #ccc;
		border-radius: 50%;
		position: absolute;
	}

	.colseBtn i {
		display: block;
		width: 20px;
		height: 2px;
		background: #000;
		position: absolute;
		top: 0;
		bottom: 0;
		right: 0;
		left: 0;
		margin: auto;
	}

	.colseBtn i:nth-child(1) {
		transform: rotateZ(45deg);
	}

	.colseBtn i:nth-child(2) {
		transform: rotateZ(-45deg);
	}

	.banner_content {
		position: absolute;
		top: 0;
		bottom: 0;
		right: 0;
		left: 0;
		margin: auto;
		width: 1100px;
		height: 400px;
		/* background: #fff; */
	}

	.el-carousel__item {
		color: #475669;
		font-size: 18px;
		opacity: 0.75;
		line-height: 300px;
		margin: 0;
	}

	.el-carousel__item {
		background-color: #ccc;
	}

	.banner_content .imgUrl {
		width: 100%;
		height: 100%;
		line-height: 400px;
		position: relative;
	}

	.banner_content img {
		position: absolute;
		top: 0;
		bottom: 0;
		left: 0;
		right: 0;
		margin: auto;
		width: 100%;
		vertical-align: middle;
	}
</style>
