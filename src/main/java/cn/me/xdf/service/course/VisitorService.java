package cn.me.xdf.service.course;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.me.xdf.common.hibernate4.Value;
import cn.me.xdf.model.bam.BamCourse;
import cn.me.xdf.model.course.Visitor;
import cn.me.xdf.model.organization.SysOrgPerson;
import cn.me.xdf.service.AccountService;
import cn.me.xdf.service.BaseService;
import cn.me.xdf.service.bam.BamCourseService;

/**
 * 
 * 最近访客service
 * 
 * @author zhaoq
 * 
 */
@Service
@Transactional(readOnly = false)
public class VisitorService extends BaseService{

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private BamCourseService bamCourseService;
	
	@SuppressWarnings("unchecked")
	@Override
	public  Class<Visitor> getEntityClass() {
		return Visitor.class;
	}
	
	public Visitor getVisitorByBamIdAndPersonId(String bamId,String personId){
		List<Visitor> visitors = findByCriteria(Visitor.class,
                Value.eq("fdUser.fdId", personId),
                Value.eq("bamCourse.fdId", bamId));
        if (CollectionUtils.isNotEmpty(visitors)) {
            return visitors.get(0);
        }
        return null;
	}
	
	/**
	 * 添加最近访客
	 * 
	 */
	@Transactional(readOnly = false)
	public void saveVisitor(String personId,String bamId){
		SysOrgPerson orgPerson = accountService.load(personId);
		BamCourse bamCourse = bamCourseService.get(BamCourse.class, bamId);
		if(!personId.equals(bamCourse.getPreTeachId())){
			Visitor visitor = getVisitorByBamIdAndPersonId(bamId, personId);
			if(visitor==null){
				visitor=new Visitor();
				visitor.setFdUser(orgPerson);
				visitor.setBamCourse(bamCourse);
				visitor.setFdTime(new Date());
				save(visitor);
			}else{
				visitor.setFdTime(new Date());
				update(visitor);
			}
		}
		
		
	}
	
}
