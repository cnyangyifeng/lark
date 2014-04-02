package com.kuxue.service.letter;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kuxue.common.hibernate4.Finder;
import com.kuxue.common.hibernate4.Value;
import com.kuxue.common.page.Pagination;
import com.kuxue.model.letter.RelationLetter;
import com.kuxue.model.organization.SysOrgPerson;
import com.kuxue.service.AccountService;
import com.kuxue.service.BaseService;
@Service
@Transactional(readOnly = false)
public class RelationLetterService extends BaseService{
	
	@Autowired
	private AccountService accountService;
	
	/**
	 * 找出我的私信列表
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public  Pagination findList(String sendUserId, Integer pageNo, Integer pageSize){
		Finder finder = Finder.create("select * from (");
		finder.append(" select r.acceptuserid,r.senduserid,l.body,l.fdcreatetime,");
		finder.append(" dense_rank() over(partition by c.relationletterid order by l.fdcreatetime desc) rank");
		finder.append(" from IXDF_NTP_LETTER_RELATION r,IXDF_NTP_LETTER_CONNECT c,IXDF_NTP_LETTER l");
		finder.append(" where c.relationletterid = r.fdid and r.senduserid='"+sendUserId+"'");
		finder.append(" and c.privateletterid = l.fdid ) e");
		finder.append(" where e.rank = 1 order by e.fdcreatetime desc");
		Pagination page = getPageBySql(finder, pageNo, pageSize);
		return page;
	}
	/**
	 * 保存私信的关系表
	 * @param sendUserId
	 * @param acceptUsesId
	 * @param letterId
	 */
	public RelationLetter saveRelationLetter(String sendUserId,String acceptUsesId){
		RelationLetter relationLetter = getModelByPersonId(sendUserId, acceptUsesId);
		if(relationLetter==null){
			SysOrgPerson acceptUser = accountService.get(acceptUsesId);
			SysOrgPerson sendUser = accountService.get(sendUserId);
			relationLetter = new RelationLetter();
			relationLetter.setAcceptUser(acceptUser);
			relationLetter.setSendUser(sendUser);
			save(relationLetter);
		}
		return relationLetter;
	}
	/**
	 * 根据发送者id和接受者id找两人私信关系表
	 * @param sendUserId
	 * @param acceptUsesId
	 * @return
	 */
	public RelationLetter getModelByPersonId(String sendUserId,
			String acceptUserId) {
		List<RelationLetter> relations = findByCriteria(RelationLetter.class,
				Value.eq("sendUser.fdId", sendUserId),
				Value.eq("acceptUser.fdId", acceptUserId));
		if (CollectionUtils.isNotEmpty(relations)) {
			return relations.get(0);
		}
		return null;
	}
	@Override
	@SuppressWarnings("unchecked")
	public  Class<RelationLetter> getEntityClass() {
		return  RelationLetter.class;
	}
}
