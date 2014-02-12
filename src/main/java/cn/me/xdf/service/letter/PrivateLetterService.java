package cn.me.xdf.service.letter;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.me.xdf.common.hibernate4.Finder;
import cn.me.xdf.common.hibernate4.Value;
import cn.me.xdf.common.page.Pagination;
import cn.me.xdf.model.letter.ConnectLetter;
import cn.me.xdf.model.letter.PrivateLetter;
import cn.me.xdf.model.letter.RelationLetter;
import cn.me.xdf.model.organization.SysOrgPerson;
import cn.me.xdf.service.AccountService;
import cn.me.xdf.service.BaseService;
/**
 * 私信的service
 * @author yuhuizhe
 *
 */
@Service
@Transactional(readOnly = false)
public class PrivateLetterService extends BaseService{
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private RelationLetterService relationLetterService;
	
	@Autowired
	private ConnectLetterService connectLetterService;
	
	/**
	 * 根据私信内容和发送对象保存私信
	 * @param body
	 * @param acceptUsesId
	 */
	public PrivateLetter saveLetter(String body,String sendUserId, String acceptUserId){
		PrivateLetter letter = new PrivateLetter();
		SysOrgPerson sendUser = accountService.load(sendUserId);
		letter.setFdCreateTime(new Date());//时间
		letter.setBody(body);//内容
		letter.setIsRead(false);//接受者未阅读
		letter.setSendUser(sendUser);//发送者
		SysOrgPerson acceptUser = accountService.load(acceptUserId);
		letter.setAcceptUser(acceptUser);//接受者
		save(letter);
		return letter;
	}
	/**
	 * 找出相关两个人之间的未读数量
	 * @param sendUserId
	 * @param acceptUsesId
	 * @return
	 */
	public List<ConnectLetter> getTotalNum(String sendUserId,String acceptUserId){
		RelationLetter  re = relationLetterService.getModelByPersonId(sendUserId, acceptUserId);
		List<ConnectLetter> list = connectLetterService.findByProperty("relationLetter.fdId", re.getFdId());
		return list;
	}
	/**
	 * 根据发送者id和接受者id找出私信
	 * @param sendUserId
	 * @param acceptUsesId
	 * @return
	 */
	public List<PrivateLetter> getModelsBySendIdAndAcceptId(String sendUserId,
			String acceptUsesId) {
		List<PrivateLetter> letters = findByCriteria(PrivateLetter.class,
				Value.eq("sendUser.fdId", sendUserId),
				Value.eq("acceptUser.fdId", acceptUsesId));
		return letters;
	}
	/**
	 * 通过私信的人员关系id找出私信内容
	 * @param letterId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination findLetterListByRelationId(String relationId, int pageNo, int pageSize){
		Finder finder = Finder.create("from ConnectLetter cl");
		finder.append("where cl.relationLetter.fdId =:relationId");
		finder.setParam("relationId", relationId);
		finder.append(" order by cl.privateLetter.fdCreateTime desc");
		Pagination page = getPage(finder, pageNo, pageSize);
		return page;
	}
	public List<ConnectLetter> findMaxListByRelationId(String relationId, int maxResult){
		Finder finder = Finder.create("from ConnectLetter cl");
		finder.append("where cl.relationLetter.fdId =:relationId");
		finder.setParam("relationId", relationId);
		finder.append(" order by cl.privateLetter.fdCreateTime desc");
		finder.setMaxResults(maxResult);
		List<ConnectLetter> list = this.find(finder);
		return list;
	}
	/**
	 * 获得最新的私信内容(找出两个人最新的对话)
	 * @return
	 */
	public PrivateLetter findNewestLetter(String sendUserId, String acceptUserId){
		Finder finder = Finder.create(" from PrivateLetter t ");
		finder.append("where (t.sendUser.fdId=:sendUserId");
		finder.append("and t.acceptUser.fdId=:acceptUserId)");
		finder.append("or(t.sendUser.fdId=:acceptUserId");
		finder.append("and t.acceptUser.fdId=:sendUserId)");
		finder.setParam("sendUserId", sendUserId);
		finder.setParam("acceptUserId", acceptUserId);
		finder.append(" order by t.fdCreateTime desc");
		finder.setMaxResults(1);
		List<PrivateLetter> list = find(finder);
        if(CollectionUtils.isNotEmpty(list)){
        	return list.get(0);
		}
		return null;
	}
	/**
	 * 根据人员id判断该人员是否给当前用户发送过消息且当前用户未读
	 * @param sendUserId
	 * @param acceptUserId
	 * @return
	 */
	public boolean getIsUnRead(String sendUserId, String acceptUserId){
		Finder finder = Finder.create(" from PrivateLetter t ");
		finder.append("where t.sendUser.fdId=:sendUserId");
		finder.append("and t.acceptUser.fdId=:acceptUserId");
		finder.append("and t.isRead=:isRead");
		finder.setParam("sendUserId", sendUserId);
		finder.setParam("acceptUserId", acceptUserId);
		finder.setParam("isRead", false);
		List<PrivateLetter> list = find(finder);
        if(CollectionUtils.isNotEmpty(list)){
        	return true;
		}
		return false;
	}
	
	/**
	 * 根据发送者和接受者改变该私信是否已读状态
	 * @param sendUserId
	 * @param acceptUserId
	 */
	public void updateLetterStatus(String sendUserId, String acceptUserId){
		List<PrivateLetter> letters = getModelsBySendIdAndAcceptId(sendUserId,acceptUserId);
		for(PrivateLetter letter : letters){
			if(!letter.getIsRead()){
				letter.setIsRead(true);
				save(letter);
			}
		}
	}
	/**
	 * 根据人员id改变改人员的私信全部为已读状态
	 * @param sendUserId
	 */
	public void updateLetterStatusBySendId(String userId){
		List<PrivateLetter> letters = findByProperty("acceptUser.fdId", userId);
		for(PrivateLetter letter : letters){
			if(!letter.getIsRead()){
				letter.setIsRead(true);
				save(letter);
			}
		}
	}
	

	@Override
	@SuppressWarnings("unchecked")
	public  Class<PrivateLetter> getEntityClass() {
		return  PrivateLetter.class;
	}

}
