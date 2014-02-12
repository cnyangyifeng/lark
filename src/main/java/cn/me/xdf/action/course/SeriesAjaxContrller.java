package cn.me.xdf.action.course;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import jodd.util.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.me.xdf.common.hibernate4.Finder;
import cn.me.xdf.common.json.JsonUtils;
import cn.me.xdf.common.page.Pagination;
import cn.me.xdf.common.utils.ComUtils;
import cn.me.xdf.model.bam.BamCourse;
import cn.me.xdf.model.base.AttMain;
import cn.me.xdf.model.course.CourseInfo;
import cn.me.xdf.model.course.SeriesCourses;
import cn.me.xdf.model.course.SeriesInfo;
import cn.me.xdf.model.organization.SysOrgPerson;
import cn.me.xdf.model.score.ScoreStatistics;
import cn.me.xdf.service.AccountService;
import cn.me.xdf.service.bam.BamCourseService;
import cn.me.xdf.service.base.AttMainService;
import cn.me.xdf.service.course.CourseService;
import cn.me.xdf.service.course.SeriesCoursesService;
import cn.me.xdf.service.course.SeriesInfoService;
import cn.me.xdf.service.score.ScoreStatisticsService;
import cn.me.xdf.utils.DateUtil;
import cn.me.xdf.utils.ShiroUtils;

@Controller
@RequestMapping(value = "/ajax/series")
@Scope("request")
public class SeriesAjaxContrller {
	@Autowired
	private SeriesInfoService seriesInfoService;

	@Autowired
	private SeriesCoursesService seriesCoursesService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private AttMainService attMainService;

	@Autowired
	private CourseService courseService;

	// 课程进程service
	@Autowired
	private BamCourseService bamCourseService;

	@Autowired
	private ScoreStatisticsService scoreStatisticsService;

	/**
	 * 保存阶段信息 author hanhl
	 */
	@RequestMapping(value = "saveSeries")
	@ResponseBody
	public String saveSeries(HttpServletRequest request) {
		String seriesId = request.getParameter("seriesId");
		String fdName = request.getParameter("title");
		int fdNo = Integer.parseInt(request.getParameter("fdno"));
		SysOrgPerson creator = accountService.findById(ShiroUtils.getUser()
				.getId());
		SeriesInfo series = new SeriesInfo();
		series.setFdName(fdName);
		series.setFdSeriesNo(fdNo);
		series.setFdCreateTime(new Date());
		series.setCreator(creator);
		// 没有系列id说明是新增系列 否则就是新增阶段
		Map<String, String> map = new HashMap<String, String>();
		if (StringUtil.isNotEmpty(seriesId)) {
			SeriesInfo seriessup = seriesInfoService.get(seriesId);
			series.setHbmParent(seriessup);
			seriesInfoService.save(series);
			map.put("seriesId", seriesId);
			map.put("id", series.getFdId());
		} else {
			SeriesInfo seriessup = new SeriesInfo();// 先创建系列
			seriessup.setIsPublish(false);// 初始化为草稿状态
			seriessup.setVersion(0);
			seriessup.setFdCreateTime(new Date());
			seriessup.setCreator(creator);
			seriessup.setIsAvailable(true);// 有效的
			seriessup.setFdName("未命名");
			seriesInfoService.save(seriessup);
			series.setHbmParent(seriessup);
			seriesInfoService.save(series);// 再保存阶段
			map.put("id", series.getFdId());
			map.put("seriesId", seriessup.getFdId());
		}

		return JsonUtils.writeObjectToJson(map);
	}

	/**
	 * 根据阶段id更新阶段名称
	 * 
	 * @param request
	 */
	@RequestMapping(value = "updateSeriesFdNameById")
	@ResponseBody
	public void updateSeriesFdNameById(HttpServletRequest request) {
		// 获取阶段id
		String fdId = request.getParameter("fdid");
		// 获取章节名称
		String fdName = request.getParameter("title");
		SeriesInfo seriesInfo = seriesInfoService.get(fdId);
		seriesInfo.setFdName(fdName);
		seriesInfoService.save(seriesInfo);
	}

	/**
	 * 保存系列基本信息
	 * 
	 * @param
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "saveSeriesBaseInfo")
	@ResponseBody
	public String saveSeriesBaseInfo(HttpServletRequest request) {
		String seriesId = request.getParameter("seriesId");
		String seriesTitle = request.getParameter("seriesTitle");
		String seriesDesc = request.getParameter("seriesDesc");
		String seriesAuthor = request.getParameter("seriesAuthor");
		String seriesAuthorId = request.getParameter("seriesAuthorId");
		String authorDesc = request.getParameter("authorDesc");
		// String isavailable=request.getParameter("isavailable");
		SysOrgPerson creator = accountService.findById(ShiroUtils.getUser()
				.getId());
		SeriesInfo series=new SeriesInfo();
		if(StringUtil.isNotEmpty(seriesId)){
			series = seriesInfoService.get(seriesId);
			series.setFdName(seriesTitle);
			series.setFdDescription(seriesDesc);
			series.setFdAuthor(seriesAuthor);
			series.setFdAuthorDescription(authorDesc);
			series.setFdAuthorId(seriesAuthorId);
			series.setCreator(creator);
			seriesInfoService.save(series);
		}else{
			series.setFdName(seriesTitle);
			series.setFdDescription(seriesDesc);
			series.setCreator(creator);
			series.setFdCreateTime(new Date());
			series.setFdAuthor(seriesAuthor);
			series.setFdAuthorDescription(authorDesc);
			series.setFdAuthorId(seriesAuthorId);
			series.setIsPublish(false);
			series.setIsAvailable(true);
			seriesInfoService.save(series);
			seriesId=series.getFdId();
		}
		Map map = new HashMap();
		map.put("seriesId", seriesId);
		return JsonUtils.writeObjectToJson(map);
	}

	/**
	 * 加载系列封页
	 * */
	@RequestMapping(value = "showcover")
	@ResponseBody
	public String showcover(HttpServletRequest

	request) {
		String seriesId = request.getParameter("seriesId");
		AttMain attMain = attMainService.getByModelId(seriesId);
		Map<String, String> map = new HashMap<String, String>();
		if (attMain != null) {
			map.put("coverUrl", attMain.getFdId()+"?n="+new Random().nextInt(100));
		} else {
			map.put("coverUrl", "");
		}
		return JsonUtils.writeObjectToJson(map);
	}

	/**
	 * 保存系列封页信息
	 * 
	 * @param
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "saveSeriesPic")
	@ResponseBody
	public void saveSeriesPic(HttpServletRequest request) {
		String seriesId = request.getParameter("seriesId");
		String attMainId = request.getParameter("attId");
		if (StringUtil.isNotBlank(attMainId)) {
			// 先清理附件库(清理该课程下的原始附件)
			attMainService.deleteAttMainByModelId(seriesId);
			AttMain attMain = attMainService.get(attMainId);
			attMain.setFdModelId(seriesId);
			attMain.setFdModelName(SeriesInfo.class.getName());
			attMain.setFdKey("SeriesInfo");
			// 保存最新的附件
			attMainService.save(attMain);
		}
	}

	/**
	 * 查询课程列表 或者根据关键字搜索 author hanhl
	 */
	@RequestMapping(value = "getSeriesInfosOrByKey")
	public String getSeiesOrByKey(Model model, HttpServletRequest request) {
		String fdName = request.getParameter("fdName");
		String pageNo = request.getParameter("pageNo");
		String orderbyStr = request.getParameter("order");
		Pagination page = seriesInfoService.findSeriesInfosOrByName(fdName,
				pageNo, orderbyStr);
		model.addAttribute("page", page);
		return "/course/divserieslist";
	}

	/**
	 * 选择课程删除时:删除课程与系列的关系 author hanhl
	 */
	@RequestMapping(value = "deleteSeriesOfCourse")
	public void deleteSeriesOfCourse(HttpServletRequest request) {
		String courseId = request.getParameter("courseId");
		seriesCoursesService.deleteByCourseId(courseId);
	}

	/**
	 * 删除阶段信息
	 */
	@RequestMapping(value = "deletePhasesById")
	@ResponseBody
	public void deletePhasesById(HttpServletRequest request) {
		String phasesId = request.getParameter("phasesId");
		if (StringUtil.isNotEmpty(phasesId)) {
			// 此处删除涉及两张表:系列表 系列课程表
			// 删除系列课程表
			seriesCoursesService.deleteBySeriesId(phasesId);
			// 删除系列表
			seriesInfoService.delete(phasesId);
		}
	}

	/**
	 * 更新阶段的顺序
	 * 
	 * @param request
	 */
	@RequestMapping(value = "updateSeriesOrder")
	@ResponseBody
	public void updateSeriesOrder(HttpServletRequest request) {
		// 获取阶段
		String chapter = request.getParameter("chapter");
		// 如果章信息不为空，则将章信息转为list循环更新
		if (StringUtil.isNotEmpty(chapter)) {
			List<Map> chapters = JsonUtils
					.readObjectByJson(chapter, List.class);
			if (chapters != null && chapters.size() > 0) {
				for (Map<?, ?> chapterMap : chapters) {
					String chapterId = (String) chapterMap.get("id");
					if (StringUtil.isNotEmpty(chapterId)) {
						SeriesInfo seriesInfo = seriesInfoService
								.get(chapterId);
						seriesInfo.setFdSeriesNo((Integer) chapterMap
								.get("num"));
						seriesInfoService.save(seriesInfo);
					}
				}
			}
		}
	}

	/**
	 * 根据课程标题或者副标题进行课程查询
	 */
	@RequestMapping(value = "getCourseBykey")
	@ResponseBody
	public List<Map> getCourseBykey(HttpServletRequest request) {
		String key = request.getParameter("q");
		List<Map> courseInfos = new ArrayList<Map>();
		List<CourseInfo> courses = courseService
				.findCourseInfoByCouseNameTop10(key);
		if (courses != null && courses.size() > 0) {
			for (CourseInfo courseInfo : courses) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", courseInfo.getFdId());// 课程id
				map.put("name", courseInfo.getFdTitle());// 课程名称
				map.put("creator", courseInfo.getCreator().getFdName());//课程发布者
				map.put("createtime",  DateUtil.getInterval(courseInfo.getFdCreateTime().toString(),null));//发布时间
				courseInfos.add(map);
			}
		}
		return courseInfos;
	}

	/**
	 * 添加阶段课程
	 */
	@RequestMapping(value = "saveSeriesCourse")
	@ResponseBody
	public void saveSeriesCourse(HttpServletRequest request) {
		// 获取阶段Id
		String phasesId = request.getParameter("phasesId");
		// 获取阶段描述
		String seriesDesc = request.getParameter("sectionsIntro");
		// 阶段名称
		String phaseTitle = request.getParameter("phaseTitle");
		// 获取节内容列表
		String courseList = request.getParameter("mediaList");
		if (StringUtil.isNotEmpty(phasesId)) {
			SeriesInfo seriesSub = seriesInfoService.get(phasesId);
			if (seriesSub != null) {
				if (StringUtil.isNotEmpty(seriesDesc)) {
					seriesSub.setFdName(phaseTitle);// 修改后的阶段名称
					seriesSub.setFdDescription(seriesDesc);// 阶段描述
					seriesInfoService.save(seriesSub);// 保存阶段信息
				}
				seriesCoursesService.deleteBySeriesId(phasesId);// 首先清理系列课程表中已存在的信息,然后保存最新信息
				if (StringUtil.isNotEmpty(courseList)) {
					// 解析页面传递的素材列表
					List<Map> cousers = JsonUtils.readObjectByJson(courseList,
							List.class);
					if (cousers != null && cousers.size() > 0) {
						for (Map<?, ?> map : cousers) {
							String courseId = (String) map.get("id");
							String index = (String) map.get("index");
							if (StringUtil.isNotEmpty(courseId)) {
								CourseInfo courseInfo = courseService
										.get(courseId);
								if (courseInfo != null
										&& courseInfo.getIsAvailable()) {
									SeriesCourses seriesCourses = new SeriesCourses();
									seriesCourses.setSeries(seriesSub);
									seriesCourses.setCourses(courseInfo);
									seriesCourses.setVersion(0);
									seriesCourses.setFdCourseNo(Integer
											.parseInt(index));
									seriesCoursesService.save(seriesCourses);// 保存阶段课程信息
								}
							}
						}
					}
				}

			}
		}
	}

	/**
	 * 通过阶段id提取课程信息
	 */
	@RequestMapping(value = "getSeriesCourseById")
	@ResponseBody
	public String getSeriesCourseById(HttpServletRequest request) {
		String phasesId = request.getParameter("phasesId");
		List<SeriesCourses> sclist = seriesCoursesService
				.getSeriesCourseByseriesId(phasesId);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Comparable>> courselist = null;
		String phasesDes = "";
		String phasesTitle = "";
		if (sclist != null) {
			courselist = new ArrayList<Map<String, Comparable>>();
			SeriesInfo phases = sclist.get(0).getSeries();
			phasesDes = phases.getFdDescription();
			phasesTitle = phases.getFdName();
			for (SeriesCourses seriescourse : sclist) {
				Map<String, Comparable> courseM = new HashMap<String, Comparable>();
				CourseInfo course = seriescourse.getCourses();
				courseM.put("id", course.getFdId());
				courseM.put("title", course.getFdTitle());
				courseM.put("index", seriescourse.getFdCourseNo());
				courselist.add(courseM);
			}
		}
		map.put("seriesTitle", phasesTitle);
		map.put("sectionsIntro", phasesDes);
		map.put("mediaList", courselist);
		return JsonUtils.writeObjectToJson(map);
	}

	/**
	 * 获取当前系列的阶段信息
	 * 
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "getSeriesBySeriesId")
	@ResponseBody
	public String getSeriesBySeriesId(HttpServletRequest request) {
		// 获取系列id
		String seriesId = request.getParameter("seriesId");
		List<Map> phaseses = null;
		int courseCount = 0;
		if (StringUtil.isNotEmpty(seriesId)) {
			// 根据系列ID取阶段列表
			List<SeriesInfo> serieses = seriesInfoService
					.getSeriesById(seriesId);
			if (serieses != null && serieses.size() > 0) {
				phaseses = new ArrayList<Map>();
				for (SeriesInfo series : serieses) {
					Map<String, Object> phasesM = new HashMap<String, Object>();
					phasesM.put("id", series.getFdId());
					phasesM.put("index", series.getFdSeriesNo());
					phasesM.put("num", serieses.size());
					phasesM.put("title", series.getFdName());
					phaseses.add(phasesM);
					List<SeriesCourses> sOfcourselist = seriesCoursesService
							.getSeriesCourseByseriesId(series.getFdId());
					if (sOfcourselist != null && sOfcourselist.size() > 0) {
						courseCount++;
					}
				}
			}
		}
		// 将阶段信息转换成json返回到页面
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("chapter", phaseses);// 阶段信息
		map.put("courseCount", courseCount);// 阶段课程统计
		return JsonUtils.writeObjectToJson(map);
	}

	/**
	 * 获取系列的基本信息
	 */
	@RequestMapping(value = "getBaseSeriesInfoById")
	@ResponseBody
	public String getBaseSeriesInfoById(HttpServletRequest request) {
		String seriesId = request.getParameter("seriesId");
		Map<String, Comparable> map = new HashMap<String, Comparable>();
		if (StringUtil.isNotEmpty(seriesId)) {
			SeriesInfo seriesInfo = seriesInfoService.get(seriesId);
			map.put("seriesTitle", seriesInfo.getFdName());
			map.put("fdDescription", seriesInfo.getFdDescription());
			// map.put("isavailable", seriesInfo.getIsAvailable());//如果当前没有作者信息
			// 则去创建者的名字
			map.put("seriesAuthor", seriesInfo.getFdAuthor());// 作者
			map.put("seriesAuthorId", seriesInfo.getFdAuthorId());// 作者Id
			map.put("authorDesc", seriesInfo.getFdAuthorDescription());// 作者简介
		}else{
			SysOrgPerson person = accountService.get(ShiroUtils.getUser().getId());
			map.put("seriesAuthor", person.getFdName());// 作者
			map.put("seriesAuthorId", person.getFdId());// 作者Id
			map.put("authorDesc", person.getSelfIntroduction());// 作者简介	
		}
		return JsonUtils.writeObjectToJson(map);
	}

	/**
	 * 系列课程发布或预览
	 */
	@RequestMapping(value = "releaseSeries")
	@ResponseBody
	public void releaseSeries(HttpServletRequest request) {
		String seriesId = request.getParameter("seriesId");
		if (StringUtil.isNotEmpty(seriesId)) {
			SeriesInfo seriesInfo = seriesInfoService.get(seriesId);
			seriesInfo.setIsPublish(true);
			seriesInfoService.save(seriesInfo);
		}
	}

	/**
	 * author hanhl 系列课程列表单一删除
	 */
	@RequestMapping(value = "deleteSeries")
	@ResponseBody
	public void deleteSeries(HttpServletRequest request) {
		String seriesId = request.getParameter("seriesId");
		if (StringUtils.isNotEmpty(seriesId)) {
			String[] courses = seriesId.split(",");
			for (int i = 0; i < courses.length; i++) {
				deleteSeriesById(courses[i]);
			}
		}

	}

	/**
	 * author hanhl 系列课程列表全删
	 */
	@RequestMapping(value = "deleteAllSeries")
	@ResponseBody
	public void deleteAllSeries(HttpServletRequest request) {
		String fdTitle = request.getParameter("fdTitle");
		String pageNoStr = request.getParameter("pageNo");
		String orderbyStr = request.getParameter("order");
		Pagination page = seriesInfoService.findSeriesInfosOrByName(fdTitle,
				pageNoStr, orderbyStr);
		int i = page.getTotalPage();
		if (i > 0) {
			for (int j = 0; j < i; j++) {
				page = seriesInfoService.findSeriesInfosOrByName(fdTitle, "1",
						orderbyStr);
				List<?> list = page.getList();
				if (list != null && list.size() > 0) {
					for (Object obj : list) {
						Map<?, ?> map = (Map<?, ?>) obj;
						String seriesId = (String) map.get("FDID");
						deleteSeriesById(seriesId);
					}
				}
			}
		}
	}

	private void deleteSeriesById(String seriesId) {
		SeriesInfo seriesInfo = seriesInfoService.get(seriesId);// 查找系列
		if (seriesInfo != null) {
			if (seriesInfo.getIsPublish()) {
				// 如果是已发布的系列,则置为无效
				if (ShiroUtils.isAdmin()) {// admin则全部置为无效
					seriesInfoService.deleteSeries(seriesId);
				} else {// 其他用户只能置自己创建的系列为无效;
					if (seriesInfo.getCreator().getFdId()
							.equals(ShiroUtils.getUser().getId())) {
						seriesInfoService.deleteSeries(seriesId);
					}
				}
			} else {
				// 草稿状态,直接删除系列(首先到系列课程中删除,然后删除系列自己)
				// seriesInfoService.delete(seriesId);
				if (ShiroUtils.isAdmin()) {
					List<SeriesInfo> phaseslist = seriesInfoService
							.getSeriesById(seriesId);// 查找系列下的阶段
					if (phaseslist != null) {
						for (SeriesInfo phases : phaseslist) {// 查找阶段下的课程并删除
							seriesCoursesService.deleteBySeriesId(phases
									.getFdId());
							seriesInfoService.delete(phases.getFdId());
						}
					}
					seriesInfoService.delete(seriesId);
				} else {
					if (seriesInfo.getCreator().getFdId()
							.equals(ShiroUtils.getUser().getId())) {
						List<SeriesInfo> phaseslist = seriesInfoService
								.getSeriesById(seriesId);// 查找系列下的阶段
						if (phaseslist != null) {
							for (SeriesInfo phases : phaseslist) {// 查找阶段下的课程并删除
								seriesCoursesService.deleteBySeriesId(phases
										.getFdId());
								seriesInfoService.delete(phases.getFdId());
							}
						}
						seriesInfoService.delete(seriesId);

					}
				}
			}
		}
	}

	/**
	 * 系列首页
	 */
	public SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd h:m:s a");

	@RequestMapping(value = "getSeriesHeardPage")
	@ResponseBody
	public String getSeriesHeardPage(HttpServletRequest request) {
		String seriesId = request.getParameter("seriesId");
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtil.isNotEmpty(seriesId)) {
			SeriesInfo seriesInfo = seriesInfoService.get(seriesId);

			map.put("id", seriesInfo.getFdId());
			map.put("seriesName", seriesInfo.getFdName());
			map.put("seriesDesc", seriesInfo.getFdDescription());
			map.put("createTime", sdf.format(seriesInfo.getFdCreateTime()));
			AttMain attMain1 = attMainService.getByModelIdAndModelName(
					seriesInfo.getFdId(), SeriesInfo.class.getName());
			if (attMain1 != null) {
				map.put("seriesImg", attMain1.getFdId());
			} else {
				map.put("seriesImg", "");
			}
			Map<String, String> author = null;
			// 在创建系列课程时,当作者为空时取的是创建者的名字,此处判断如果创建者和作者名字相同走默认即创建者,否则取作者字段
			if (StringUtil.isEmpty(seriesInfo.getFdAuthorId())) {
				author = new HashMap<String, String>();
				author.put("authorName", seriesInfo.getFdAuthor());// 作者名称
				author.put("imgUrl", ComUtils.getDefaultPoto());// 作者头像
				
				author.put("authorDesc", seriesInfo.getFdAuthorDescription());
			} else {// 否则默认为系列创建者
				author = new HashMap<String, String>();
				author.put("authorName", seriesInfo.getCreator().getRealName());
				// 作者头像
				SysOrgPerson orgPerson = accountService.get(seriesInfo.getFdAuthorId());
				if(orgPerson==null){
					author.put("imgUrl", ComUtils.getDefaultPoto());
				}else{
					author.put("imgUrl", orgPerson.getPoto());
				}
				author.put("authorDesc", seriesInfo.getFdAuthorDescription());
			}

			map.put("author", author);
			int sOfcount = 0;// 记录该系列下有多少课程
			List<SeriesInfo> seriesList = seriesInfoService
					.getSeriesById(seriesId);
			List<Map> phasesList = new ArrayList<Map>();
			if (seriesList != null && seriesList.size() > 0) {
				for (SeriesInfo series : seriesList) {
					Map<String, Object> phasesM = new HashMap<String, Object>();
					phasesM.put("phasesNo", series.getFdSeriesNo());
					phasesM.put("phasesName", series.getFdName());
					phasesM.put("phasesDesc", series.getFdDescription());
					List<SeriesCourses> sOfcourses = seriesCoursesService
							.getSeriesCourseByseriesId(series.getFdId());
					List<Map> courselist = new ArrayList<Map>();
					if (sOfcourses != null && sOfcourses.size() > 0) {
						for (SeriesCourses course : sOfcourses) {
							Map<String, Object> mapc = new HashMap<String, Object>();
							// 课程id
							mapc.put("courseId", course.getCourses().getFdId());
							// 课程编号
							mapc.put("courseNo",
									sOfcount == 0 ? course.getFdCourseNo()
											: sOfcount + course.getFdCourseNo());
							// 课程名称
							mapc.put("courseName", course.getCourses()
									.getFdTitle());
							// 课程摘要
							mapc.put("fdSummary", course.getCourses()
									.getFdSummary());
							// 学习人数
							mapc.put("countStudy", getLearningTotalNo(course
									.getCourses().getFdId()));
							// 课程状态
							BamCourse bamCourse = bamCourseService
									.getCourseByUserIdAndCourseId(ShiroUtils
											.getUser().getId(), course
											.getCourses().getFdId());
							if (bamCourse == null) {
								mapc.put("isThrough", false);
							} else {
								mapc.put("isThrough", true);
							}
							// 课程评分
							ScoreStatistics statistic = scoreStatisticsService
									.findScoreStatisticsByModelNameAndModelId(
											CourseInfo.class.getName(), course
													.getCourses().getFdId());
							if (statistic == null) {
								mapc.put("average", 0);
							} else {
								mapc.put("average",
										statistic.getFdAverage() == null ? 0
												: statistic.getFdAverage());
							}
							// 课程封面
							AttMain attMain = attMainService
									.getByModelIdAndModelName(course
											.getCourses().getFdId(),
											CourseInfo.class.getName());
							if (attMain != null) {
								mapc.put("coverImg", attMain.getFdId()+"?n="+new Random().nextInt(100));
							} else {
								mapc.put("coverImg", "");
							}
							courselist.add(mapc);
						}
						sOfcount += sOfcourses.size();
						phasesM.put("courselist", courselist);
					}

					phasesList.add(phasesM);

				}
				map.put("phasesList", phasesList);
				// 获取最新系列课程
				List<Map> newestSeries = new ArrayList<Map>();
				Pagination page = seriesInfoService.findSeriesInfosOrByName("",
						"1", "fdcreatetime");
				int i = page.getTotalPage();
				if (i > 0) {
					List seriesInfos = page.getList();
					if (seriesInfos != null && seriesInfos.size() > 0) {
						for (int ii = 0; ii < seriesInfos.size() && ii <= 5; ii++) {
							Map seriesm = (Map) seriesInfos.get(ii);
							// 系列id
							Map formatM = new HashMap();
							formatM.put("seriesId", seriesm.get("FDID"));
							// 系列封页
							AttMain attMain = attMainService
									.getByModelIdAndModelName(
											seriesm.get("FDID").toString(),
											SeriesInfo.class.getName());
							if (attMain != null) {
								formatM.put("seriesImg", attMain.getFdId()+"?n="+new Random().nextInt(100));
							} else {
								formatM.put("seriesImg", "");
							}
							// 系列名称fdAuthor
							formatM.put("seriesName", seriesm.get("FDNAME"));
							// 系列作者
							String fdAuthor = (String) seriesm.get("FDAUTHOR");
							SysOrgPerson creator = accountService.get(seriesm
									.get("FDCREATORID").toString());
							formatM.put("author", fdAuthor == null
									|| fdAuthor == "" ? creator.getRealName()
									: fdAuthor);
							newestSeries.add(formatM);
						}
					}

				}
				map.put("newestSeries", newestSeries);
			}
		}

		return JsonUtils.writeObjectToJson(map);
	}

	/**
	 * 从课程学习进程中获取当前学习的教师总数
	 * 
	 * @param courseId
	 *            课程ID
	 * @return int 学习的教师总数
	 */
	private int getLearningTotalNo(String courseId) {
		Finder finder = Finder
				.create(" from BamCourse where courseId = :courseId");
		finder.setParam("courseId", courseId);
		Pagination page = bamCourseService.getPage(finder, 1, 15);
		return page.getTotalCount();
	}

	/**
	 * 获取系列信息
	 */
	@RequestMapping(value = "getSeries")
	@ResponseBody
	public String getSeries(HttpServletRequest request) {
		Map returnMap = new HashMap();
		int pageNo = new Integer(request.getParameter("pageNo"));
		Finder finder = Finder
				.create(" from SeriesInfo s where s.isPublish = :isPublish and s.isAvailable=:isAvailable");
		finder.append(" order by s.fdCreateTime desc");
		finder.setParam("isPublish", true);
		finder.setParam("isAvailable", true);
		int pageSize = 3;
		if(StringUtil.isNotEmpty(request.getParameter("pageSize"))){
			pageSize =  Integer.parseInt(request.getParameter("pageSize")); 
		}
		Pagination pagination = seriesCoursesService.getPage(finder, pageNo, pageSize);
		if (pagination.getTotalPage() >= pageNo) {
			if (pagination.getList().size() == pageSize) {
				returnMap.put("hasMore", true);
			} else {
				returnMap.put("hasMore", false);
			}
		} else {
			returnMap.put("hasMore", false);
		}
		returnMap.put("type", "series");
		List<SeriesInfo> infos = (List<SeriesInfo>) pagination.getList();
		List<Map> lists = new ArrayList<Map>();
		if (pagination.getTotalPage() >= pageNo) {
			for (SeriesInfo seriesInfo : infos) {
				Map map = new HashMap();
				List<AttMain> attMains = attMainService
						.getAttMainsByModelIdAndModelName(seriesInfo.getFdId(),
								SeriesInfo.class.getName());
				map.put("imgUrl", attMains.size() == 0 ? "" : (attMains.get(0)
						.getFdId()
						+"?n="+new Random().nextInt(100)));

				List<CourseInfo> list = seriesCoursesService
						.getCoursesByseriesId1(seriesInfo.getFdId());
				int count = 0;
				for (CourseInfo course : list) {
					int courseSum = getLearningTotalNo(course.getFdId());
					count = count + courseSum;
				}
				map.put("docNum",
						seriesCoursesService.getCoursesByseriesId2(
								seriesInfo.getFdId()).size());
				map.put("learnerNum", count);
				map.put("name", seriesInfo.getFdName());
				map.put("id", seriesInfo.getFdId());
				map.put("issuer", seriesInfo.getFdAuthor());
				if(seriesInfo.getFdDescription()==null){
					map.put("intro","不详");
				}else{
					map.put("intro",
							seriesInfo.getFdDescription().length() >= 35 ? seriesInfo
									.getFdDescription().subSequence(0, 35) + "..."
									: seriesInfo.getFdDescription());
				}
				map.put("statusLearning", "not");
				map.put("isLearning", false);
				map.put("dataId", seriesInfo.getFdId());
				lists.add(map);
			}
		}
		returnMap.put("list", lists);
		returnMap.put("page", pagination);
		return JsonUtils.writeObjectToJson(returnMap);
	}

}
