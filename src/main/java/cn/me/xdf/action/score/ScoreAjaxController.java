package cn.me.xdf.action.score;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.me.xdf.common.json.JsonUtils;
import cn.me.xdf.model.course.CourseInfo;
import cn.me.xdf.model.material.MaterialInfo;
import cn.me.xdf.model.score.Score;
import cn.me.xdf.model.score.ScoreStatistics;
import cn.me.xdf.service.score.ScoreService;
import cn.me.xdf.service.score.ScoreStatisticsService;
import cn.me.xdf.utils.ShiroUtils;

/**
 * 评分
 * 
 * @author
 * 
 */
@Controller
@RequestMapping(value = "/ajax/score")
@Scope("request")
public class ScoreAjaxController {
	
	@Autowired
	private ScoreService scoreService ;
	
	@Autowired
	private ScoreStatisticsService scoreStatisticsService;
	
	/**
	 * 根据业务Id和业务Name得到评分统计信息
	 * 
	 * @return 评分统计信息json
	 */
	@RequestMapping(value = "getScoreStatisticsByfdModelId")
	@ResponseBody
	public String getScoreStatisticsByfdModelId(String fdModelId,String fdModelName){
		ScoreStatistics scoreStatistics =  scoreStatisticsService.findScoreStatisticsByModelNameAndModelId(fdModelName, fdModelId);
		
		List<Map> list = new ArrayList<Map>();
		Map map = new HashMap();
		if(scoreStatistics==null){
			map.put("fdId", "");
			map.put("fdModelId", "");
			map.put("fdAverage", 0.0);
			map.put("fdScoreNum", 0);
			map.put("fdFiveScoreNum", 0);
			map.put("fdFourScoreNum", 0);
			map.put("fdThreeScoreNum", 0);
			map.put("fdTwoScoreNum", 0);
			map.put("fdOneScoreNum", 0);
		}else{
			map.put("fdId", scoreStatistics.getFdId());
			map.put("fdModelId", scoreStatistics.getFdModelId());
			map.put("fdAverage", scoreStatistics.getFdAverage());
			map.put("fdScoreNum", scoreStatistics.getFdScoreNum());
			map.put("fdFiveScoreNum", scoreStatistics.getFdFiveScoreNum());
			map.put("fdFourScoreNum", scoreStatistics.getFdFourScoreNum());
			map.put("fdThreeScoreNum", scoreStatistics.getFdThreeScoreNum());
			map.put("fdTwoScoreNum", scoreStatistics.getFdTwoScoreNum());
			map.put("fdOneScoreNum", scoreStatistics.getFdOneScoreNum());
		}
		list.add(map);
		return JsonUtils.writeObjectToJson(list);
	}
	
	/**
	 * 为相应业务打分
	 * 
	 * @param fdModelName 业务名称
	 * @param fdModelId   业务Id
	 * @param fdScore     分数
	 * @param userId      评分人Id
	 * 
	 * @return 该业务的评分统计信息json
	 */
	@RequestMapping(value = "pushScore")
	@ResponseBody
	public String pushScore(String fdModelName,String fdModelId,String fdScore,String userId){
		
		//如果没有评分统计，则先添加评分统计
		ScoreStatistics scoreStatistics = scoreStatisticsService.findScoreStatisticsByModelNameAndModelId(fdModelName, fdModelId);
		if(scoreStatistics==null){
			scoreStatistics = new ScoreStatistics();
			scoreStatistics.setFdModelId(fdModelId);
			scoreStatistics.setFdModelName(fdModelName);
			scoreStatistics.setFdAverage(0.0);
			scoreStatistics.setFdScoreNum(0);
			scoreStatistics.setFdOneScoreNum(0);
			scoreStatistics.setFdTwoScoreNum(0);
			scoreStatistics.setFdThreeScoreNum(0);
			scoreStatistics.setFdFourScoreNum(0);
			scoreStatistics.setFdFiveScoreNum(0);
			scoreStatisticsService.save(scoreStatistics);
		}
		scoreService.saveScore(fdModelName, fdModelId, fdScore, userId);
		scoreStatistics = scoreStatisticsService.updateInfoByFdModelId(fdModelName,fdModelId);
		List<Map> list = new ArrayList<Map>();
		Map map = new HashMap();
		map.put("fdId", scoreStatistics.getFdId());
		map.put("fdModelId", scoreStatistics.getFdModelId());
		map.put("fdModelName", scoreStatistics.getFdModelName());
		map.put("fdAverage", scoreStatistics.getFdAverage());
		map.put("fdScoreNum", scoreStatistics.getFdScoreNum());
		map.put("fdFiveScoreNum", scoreStatistics.getFdFiveScoreNum());
		map.put("fdFourScoreNum", scoreStatistics.getFdFourScoreNum());
		map.put("fdThreeScoreNum", scoreStatistics.getFdThreeScoreNum());
		map.put("fdTwoScoreNum", scoreStatistics.getFdTwoScoreNum());
		map.put("fdOneScoreNum", scoreStatistics.getFdOneScoreNum());
		list.add(map);
		return JsonUtils.writeObjectToJson(list);
	}
	
	/**
	 * 是否可以评分
	 * 
	 * @param fdModelId   业务Id
	 * @param fdModelName   业务Name
	 * @param userId      评分人Id
	 * 
	 * @return  String(isnot:没有评分；“...”：已评分数)
	 */
	@RequestMapping(value = "canPushScore")
	@ResponseBody
	public int canPushScore(String fdModelName, String fdModelId,String userId) {
		Score score = scoreService.findByModelIdAndUserId(fdModelName,fdModelId, userId);
		return (score == null) ? -1 : score.getFdScore();
	}
	
	/**
	 * 为课程打分（当前用户）
	 */
	@RequestMapping(value = "pushScoreToCourse")
	@ResponseBody
	public String pushScoreToCourse(String fdModelId,String fdScore){
		return pushScore(CourseInfo.class.getName(), fdModelId, fdScore,ShiroUtils.getUser().getId());
	}
	
	/**
	 * 为资源打分（当前用户）
	 */
	@RequestMapping(value = "pushMaterialToCourse")
	@ResponseBody
	public String pushMaterialToCourse(String fdModelId,String fdScore){
		return pushScore(MaterialInfo.class.getName(), fdModelId, fdScore,ShiroUtils.getUser().getId());
	}
	
	/**
	 * 是否可以为课程评分（当前用户）
	 */
	@RequestMapping(value = "canPushScoreToCourse")
	@ResponseBody
	public int canPushScoreToCourse(String fdModelId){
		return canPushScore(CourseInfo.class.getName() ,fdModelId ,ShiroUtils.getUser().getId());
	}
	
	/**
	 * 是否可以为资源评分（当前用户）
	 */
	@RequestMapping(value = "canPushScoreToMaterial")
	@ResponseBody
	public int canPushScoreToMaterial(String fdModelId){
		return canPushScore(MaterialInfo.class.getName() ,fdModelId ,ShiroUtils.getUser().getId());
	}
	
	/**
	 * 根据业务Id和业务Name得到评分统计信息
	 * 
	 * @return 评分统计信息json
	 */
	@RequestMapping(value = "getCourseScoreStatisticsByfdModelId")
	@ResponseBody
	public String getCourseScoreStatisticsByfdModelId(String fdModelId){
		return getScoreStatisticsByfdModelId(fdModelId, CourseInfo.class.getName());
	}

}
