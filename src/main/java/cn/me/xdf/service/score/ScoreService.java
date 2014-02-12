package cn.me.xdf.service.score;

import java.util.Date;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.me.xdf.common.hibernate4.Finder;
import cn.me.xdf.model.organization.SysOrgPerson;
import cn.me.xdf.model.score.Score;
import cn.me.xdf.model.score.ScoreStatistics;
import cn.me.xdf.service.AccountService;
import cn.me.xdf.service.BaseService;
/**
 * 
 * 评分service
 * 
 * @author zhaoq
 * 
 */
@Service
@Transactional(readOnly = false)
public class ScoreService extends BaseService{

	@Autowired
	private AccountService accountService;
	
	@SuppressWarnings("unchecked")
	@Override
	public  Class<Score> getEntityClass() {
		return Score.class;
	}

	/**
	 * 计算指定 业务Id和分数的记录数量
	 * 
	 */
	public int getCountByModelIdAndScore(String fdModelName, String fdModelId,int fdScore){
		Finder finder = Finder
				.create("select count(*) from Score score ");
		finder.append("where score.fdModelName=:fdModelName and score.fdModelId = :fdModelId and score.fdScore = :fdScore");
		finder.setParam("fdModelName", fdModelName);
		finder.setParam("fdModelId", fdModelId);
		finder.setParam("fdScore", fdScore);
		List list = find(finder);
		long object =(Long)list.get(0);
		return (int)object;
	}
	
	/**
	 * 根据 业务Id、业务Name和用户Id查找评分信息
	 * 
	 */
	public Score findByModelIdAndUserId(String fdModelName, String fdModelId,String userId){
		Finder finder = Finder
				.create("from Score score ");
		finder.append("where score.fdModelName=:fdModelName and score.fdModelId = :fdModelId and score.fdUser.fdId = :userId");
		finder.setParam("fdModelName", fdModelName);
		finder.setParam("fdModelId", fdModelId);
		finder.setParam("userId", userId);
		
		List<Score> scores = find(finder);
		if(scores.size()==0){
			return null;
		}else{
			return scores.get(0);
		}
		
	}
	
	/**
	 * 为相应业务打分
	 * 
	 * @param fdModelName 业务名称
	 * @param fdModelId   业务Id
	 * @param fdScore     分数
	 * @param userId      评分人Id
	 * 
	 * @return 评分信息
	 */
	public Score saveScore(String fdModelName,String fdModelId,String fdScore,String userId){
		Score s = findByModelIdAndUserId(fdModelName,fdModelId, userId);
		Score score ;
		if(s==null){
			score = new Score();
			SysOrgPerson orgPerson = accountService.load(userId);
			score.setFdModelName(fdModelName);
			score.setFdModelId(fdModelId);
			score.setFdCreateTime(new Date());
			score.setFdScore(new Integer(fdScore));
			score.setFdUser(orgPerson);
		}else{
			score = s; 
			score.setFdScore(new Integer(fdScore));
		}
		return save(score);
	}
	
}
