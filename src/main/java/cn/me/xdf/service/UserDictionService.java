package cn.me.xdf.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.me.xdf.common.hibernate4.Value;
import cn.me.xdf.model.organization.SysOrgPerson;
import cn.me.xdf.model.organization.UserDiction;


@Service
@Transactional(readOnly = true)
public class UserDictionService extends SimpleService {

	/**
	 * 根据人员ID获取项目的ID
	 * 
	 * @param userId
	 * @return
	 */
	public List<String> getDictionIdByUser(String userId) {
		List<UserDiction> userDictions = getUserDictionByUser(userId);
		List<String> dictionIds = new ArrayList<String>();
		for (UserDiction diction : userDictions) {
			dictionIds.add(diction.getDictionId());
		}
		return dictionIds;
	}

	public List<UserDiction> getUserDictionByUser(String userId) {
		return findByCriteria(UserDiction.class,
				Value.eq("sysOrgPerson.fdId", userId));
	}

	public void saveUserDiction(SysOrgPerson person, List<String> dictionIds) {
		List<UserDiction> dictions = getUserDictionByUser(person.getFdId());
		if (dictions != null) {
			for (UserDiction userDiction : dictions) {
				deleteEntity(userDiction);
			}
		}
		for (String dictionId : dictionIds) {
			UserDiction userDiction = new UserDiction();
			userDiction.setSysOrgPerson(person);
			userDiction.setDictionId(dictionId);
			super.save(userDiction);
		}
	}

}
