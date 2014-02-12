package cn.me.xdf.service.letter;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.me.xdf.common.hibernate4.Value;
import cn.me.xdf.model.letter.ConnectLetter;
import cn.me.xdf.service.BaseService;
/**
 * 私信和私信人员关系
 * @author hpnn
 *
 */
@Service
@Transactional(readOnly = false)
public class ConnectLetterService extends BaseService {
	
	/**
	 * 根据letterid和relationid的找出
	 * @param letterId
	 * @param relationId
	 * @return
	 */
	public ConnectLetter getModelsByletterIdAndRelationId(String letterId,
			String relationId) {
		List<ConnectLetter> relations = findByCriteria(ConnectLetter.class,
				Value.eq("privateLetter.fdId", letterId),
				Value.eq("relationLetter.fdId", relationId));
		if (CollectionUtils.isNotEmpty(relations)) {
			return relations.get(0);
		}
		return null;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public  Class<ConnectLetter> getEntityClass() {
		return  ConnectLetter.class;
	}
}
