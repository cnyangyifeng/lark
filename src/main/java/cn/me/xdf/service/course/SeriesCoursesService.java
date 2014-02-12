package cn.me.xdf.service.course;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.me.xdf.common.hibernate4.Finder;
import cn.me.xdf.model.course.CourseContent;
import cn.me.xdf.model.course.CourseInfo;
import cn.me.xdf.model.course.SeriesCourses;
import cn.me.xdf.model.course.SeriesInfo;
import cn.me.xdf.service.BaseService;

/**
 * 
 * 系列课程关系表service
 * 
 * @author zuoyi
 * 
 */
@Service
@Transactional(readOnly = true)
public class SeriesCoursesService  extends BaseService{
	@SuppressWarnings("unchecked")
	@Override
	public  Class<SeriesCourses> getEntityClass() {
		return SeriesCourses.class;
	}
	
	@Autowired
	private SeriesInfoService seriesInfoService;
	
	/**
	 * 根据课程ID删除课程与系列的关系
	 * @param courseId 课程ID
	 */
	@Transactional(readOnly = false)
	public void deleteByCourseId(String courseId) {
		Finder finder = Finder
				.create(" from SeriesCourses seriesCourse where  seriesCourse.courses.fdId=:courseId");	
		finder.setParam("courseId", courseId);
		List<SeriesCourses> list = super.find(finder);
		if(list!=null && list.size()>0){
			for(SeriesCourses series:list){
				super.deleteEntity(series);
			}
		}
	}
	/*根据系列ID删除课程与系列的关系
	 * author hanhl
	 */
	@Transactional(readOnly = false)
	public void deleteBySeriesId(String seriesId) {
		Finder finder = Finder
				.create(" from SeriesCourses seriesCourse where  seriesCourse.series.fdId=:seriesId");	
		finder.setParam("seriesId", seriesId);
		List<SeriesCourses> list = super.find(finder);
		if(list!=null && list.size()>0){
			for(SeriesCourses series:list){
				super.deleteEntity(series);
			}
		}
	}
	 /**
     * 根据阶段id查询课程信息
     */
	@Transactional(readOnly=false)
	public List<SeriesCourses> getSeriesCourseByseriesId(String seriesId){
		Finder finder=Finder.create("from SeriesCourses sc ");
		finder.append(" where sc.series.fdId=:seriesId");
		finder.append(" order by  sc.fdCourseNo ");
		finder.setParam("seriesId", seriesId);	
		List<SeriesCourses> sclist=find(finder);
		if(sclist!=null&&sclist.size()>0){
			return sclist;
		}
		return null;
	}
	
	/**
     * 查找系列下所有课程(去除重复)
     */
	@Transactional(readOnly=false)
	public List<CourseInfo> getCoursesByseriesId1(String seriesId){
		List<CourseInfo> list = new ArrayList<CourseInfo>();
		List<SeriesInfo> infos = seriesInfoService.getSeriesById(seriesId);
		for (SeriesInfo seriesInfo : infos) {
			 List<SeriesCourses> seriesCourses = getSeriesCourseByseriesId(seriesInfo.getFdId());
			 if(seriesCourses!=null){
				 for (SeriesCourses seriesCourses2 : seriesCourses) { 
					 if(!list.contains(seriesCourses2.getCourses())){
						 list.add(seriesCourses2.getCourses());
					 }
					 
				} 
			 }
			 
		}
		return list;
	}
	/**
     * 查找系列下所有课程(不去除重复)
     */
	@Transactional(readOnly=false)
	public List<CourseInfo> getCoursesByseriesId2(String seriesId){
		List<CourseInfo> list = new ArrayList<CourseInfo>();
		List<SeriesInfo> infos = seriesInfoService.getSeriesById(seriesId);
		for (SeriesInfo seriesInfo : infos) {
			 List<SeriesCourses> seriesCourses = getSeriesCourseByseriesId(seriesInfo.getFdId());
			 if(seriesCourses!=null){
				 for (SeriesCourses seriesCourses2 : seriesCourses) { 
						 list.add(seriesCourses2.getCourses());				 
				 }
			 }
		}
		return list;
	}
}
