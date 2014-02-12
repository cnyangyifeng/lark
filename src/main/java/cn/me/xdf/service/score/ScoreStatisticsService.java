package cn.me.xdf.service.score;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.me.xdf.common.hibernate4.Finder;
import cn.me.xdf.model.score.ScoreStatistics;
import cn.me.xdf.service.BaseService;
/**
 * 
 * 评分统计service
 * 
 * @author zhaoq
 * 
 */
@Service
@Transactional(readOnly = false)
public class ScoreStatisticsService extends BaseService{

	@Autowired
	private ScoreService scoreService;
	
	@SuppressWarnings("unchecked")
	@Override
	public  Class<ScoreStatistics> getEntityClass() {
		return ScoreStatistics.class;
	}

	/**
	 * 重新计算评分统计
	 * 
	 */
	@Transactional(readOnly = false)
	public ScoreStatistics updateInfoByFdId(String ScoreStatisticsId){
		ScoreStatistics scoreStatistics = findUniqueByProperty("fdId", ScoreStatisticsId);
		String fdModelId = scoreStatistics.getFdModelId();
		String fdModelName = scoreStatistics.getFdModelName();
		return updateInfoByFdModelId(fdModelName,fdModelId);
	}
	
	/**
	 * 通过fdModelName和fdModelId得到评分统计
	 * 
	 */
	@Transactional(readOnly = false)
	public ScoreStatistics findScoreStatisticsByModelNameAndModelId(String fdModelName, String fdModelId){
		Finder finder = Finder
				.create("from ScoreStatistics scoreStatistics ");
		finder.append("where scoreStatistics.fdModelName=:fdModelName and scoreStatistics.fdModelId = :fdModelId");
		finder.setParam("fdModelId", fdModelId);
		finder.setParam("fdModelName", fdModelName);
		List<ScoreStatistics> list = find(finder);
		if(list.size()==0){
			return null;
		}else{
			return list.get(0);
		}
		
	}
	
	/**
	 * 重新计算评分统计
	 * 
	 */
	@Transactional(readOnly = false)
	public ScoreStatistics updateInfoByFdModelId(String fdModelName, String fdModelId){
		ScoreStatistics scoreStatistics = findScoreStatisticsByModelNameAndModelId(fdModelName, fdModelId);
		if(scoreStatistics==null){
			throw new RuntimeException("不存在评分统计信息");
		}
		int score_1 = scoreService.getCountByModelIdAndScore(fdModelName,fdModelId, 1);
		int score_2 = scoreService.getCountByModelIdAndScore(fdModelName,fdModelId, 2);
		int score_3 = scoreService.getCountByModelIdAndScore(fdModelName,fdModelId, 3);
		int score_4 = scoreService.getCountByModelIdAndScore(fdModelName,fdModelId, 4);
		int score_5 = scoreService.getCountByModelIdAndScore(fdModelName,fdModelId, 5);
		int num = score_1+score_2+score_3+score_4+score_5;
		Double average = (score_1+score_2*2+score_3*3+score_4*4+score_5*5)/new Double(num);
	    DecimalFormat decimalFormat = new DecimalFormat(".#");
	    double c =Double.parseDouble(decimalFormat.format(average)) ;
		scoreStatistics.setFdAverage(c);
		scoreStatistics.setFdScoreNum(num);
		scoreStatistics.setFdOneScoreNum(score_1);
		scoreStatistics.setFdTwoScoreNum(score_2);
		scoreStatistics.setFdThreeScoreNum(score_3);
		scoreStatistics.setFdFourScoreNum(score_4);
		scoreStatistics.setFdFiveScoreNum(score_5);
		return update(scoreStatistics);
	}
}
