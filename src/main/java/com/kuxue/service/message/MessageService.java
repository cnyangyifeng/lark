package com.kuxue.service.message;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import jodd.util.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;

import com.kuxue.common.hibernate4.Finder;
import com.kuxue.common.page.Pagination;
import com.kuxue.model.bam.BamCourse;
import com.kuxue.model.base.Constant;
import com.kuxue.model.course.CourseCatalog;
import com.kuxue.model.course.CourseInfo;
import com.kuxue.model.course.CourseParticipateAuth;
import com.kuxue.model.material.MaterialInfo;
import com.kuxue.model.message.Message;
import com.kuxue.model.message.MessageReply;
import com.kuxue.model.organization.SysOrgPerson;
import com.kuxue.model.process.SourceNote;
import com.kuxue.service.AccountService;
import com.kuxue.service.BaseService;
import com.kuxue.service.bam.BamCourseService;
import com.kuxue.service.bam.process.SourceNodeService;
import com.kuxue.service.course.CourseParticipateAuthService;
import com.kuxue.service.course.CourseService;
import com.kuxue.service.material.MaterialService;
import com.kuxue.utils.ShiroUtils;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 
 * 消息service
 * 
 * @author zhaoq
 * 
 */
@Service
@Transactional(readOnly = false)
public class MessageService extends BaseService implements InitializingBean{

	private static final Logger LOGER = LoggerFactory
            .getLogger(MessageService.class);
	
	/**
     * 模板缓存
     */
    protected Map<String, Template> templateCache;
    
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private SourceNodeService sourceNodeService;
	
	@Autowired
	private MaterialService materialService;
	
	@Autowired
	private CourseParticipateAuthService courseParticipateAuthService;
	
	@Autowired
	private CourseService courseService;
	
	@SuppressWarnings("unchecked")
	@Override
	public  Class<Message> getEntityClass() {
		return Message.class;
	}
	
	@Autowired
	private MessageReplyService messageReplyService;
	
	protected MessageAssembleBuilder messageAssembleBuilder;

    @Autowired
    public void setMessageAssembleBuilder(
			MessageAssembleBuilder messageAssembleBuilder) {
		this.messageAssembleBuilder = messageAssembleBuilder;
	}
	
    @Autowired
    private BamCourseService bamCourseService;
    
	/**
	 * 查看用户是否可以支持指定评论
	 * 
	 * @return boolean
	 */
	public boolean canSupport(String userId, String messageId){
		Message message = findUniqueByProperty("fdId", messageId);
		/*if(!message.getFdType().equals("01")){
			throw new RuntimeException("只有评论消息才能支持或反对");
		}*/
		if(message.getFdType().equals(Constant.MESSAGE_TYPE_SYS)){
			return true;
		}
		if(messageReplyService.isSupportMessage(userId, messageId)!=null){
			return false;
		}else{
			if(message.getIsAnonymous().equals(false)){
				if(userId.equals(message.getFdUser().getFdId())){
					return false;
				}else{
					return true;
				}
			}else{
				return true;
			}
		}
	}
	

	/**
	 * 查看用户是否可以反对指定评论
	 * 
	 * @return boolean
	 */
	public boolean canOppose(String userId, String messageId){
		Message message = findUniqueByProperty("fdId", messageId);
		/*if(!message.getFdType().equals("01")){
			throw new RuntimeException("只有评论消息才能支持或反对");
		}*/
		if(message.getFdType().equals(Constant.MESSAGE_TYPE_SYS)){
			return true;
		}
		if(messageReplyService.isOpposeMessage(userId, messageId)!=null){
			return false;
		}else{
			if(message.getIsAnonymous().equals(false)){
				if(userId.equals(message.getFdUser().getFdId())){
					return false;
				}else{
					return true;
				}
			}else{
				return true;
			}
		}
	}
	/**
	 * 对评论支持或反对
	 * 
	 * @return 支持数和反对数，格式：支持数_反对数（例：12_11）
	 */
	public String supportOrOpposeMessage(String userId, String messageId,String fdType){
		Message message = findUniqueByProperty("fdId", messageId);
		/*if(!message.getFdType().equals("01")&&!message.getFdType().equals("04")){
			throw new RuntimeException("只有评论消息才能支持或反对");
		}*/
		if(fdType.equals("02")&&!canOppose(userId,messageId)){
			return "cannot";
		}
		if(fdType.equals("01")&&!canSupport(userId,messageId)){
			return "cannot";
		}
		MessageReply messageReply = new MessageReply();
		messageReply.setMessage(message);
		SysOrgPerson orgPerson = accountService.load(userId);
		messageReply.setFdUser(orgPerson);
		messageReply.setFdCreateTime(new Date());
		messageReply.setFdType(fdType);
		messageReplyService.save(messageReply);
		return getSupportCount(messageId)+"_"+getOpposeCount(messageId);
	}
	
	/**
	 * 计算指定评论的支持数
	 * 
	 * @return 支持数
	 */
	@Transactional(readOnly = true)
	public int getSupportCount(String messageId){
		Finder finder = Finder
				.create("select count(*) from MessageReply messageReply ");
		finder.append("where messageReply.message.fdId = :messageId and messageReply.fdType = :fdType");
		finder.setParam("messageId", messageId);
		finder.setParam("fdType", "01");
		List scores = find(finder);
		long obj = (Long)scores.get(0);
		return (int)obj;
	}
	/**
	 * 计算指定评论的反对数
	 * 
	 * @return 反对数
	 */
	@Transactional(readOnly = true)
	public int getOpposeCount(String messageId){
		Finder finder = Finder
				.create("select count(*) from MessageReply messageReply ");
		finder.append("where messageReply.message.fdId = :messageId and messageReply.fdType = :fdType");
		finder.setParam("messageId", messageId);
		finder.setParam("fdType", "02");
		List scores = find(finder);
		long obj = (Long)scores.get(0);
		return (int)obj;
	}
	
	/**
	 * 根据MessageId得到消息回复
	 * 
	 * @return List
	 */
	public List<MessageReply> findMessageReplysByMessageId(String messageId){
		Finder finder = Finder
				.create("from MessageReply messageReply ");
		finder.append("where messageReply.message.fdId = :messageId");
		finder.setParam("messageId", messageId);
		return messageReplyService.find(finder);
	}
	
	/**
	 * 分页查找Message
	 * 
	 * @return List
	 */
	public Pagination findCommentPage(String fdModelName,String fdModelId ,int pageNo, int pageSize){
		Finder finder = Finder
				.create("from Message message ");
		finder.append("where message.fdModelName=:fdModelName and message.fdModelId=:fdModelId and (message.fdType=:fdType1 or message.fdType=:fdType2) ");
		finder.append("order by message.fdCreateTime desc ");
		
		finder.setParam("fdModelName", fdModelName);
		finder.setParam("fdModelId", fdModelId);
		finder.setParam("fdType1", Constant.MESSAGE_TYPE_REVIEW);
		finder.setParam("fdType2", Constant.MESSAGE_TYPE_REPLY);
		List<Message> messages = (List<Message>) getPage(finder, pageNo, pageSize).getList();
		return getPage(finder, pageNo, pageSize);
	}

	/**
	 * 计算指定评论的评论数
	 * 
	 * @return 评论的评论数
	 */
	@Transactional(readOnly = true)
	public int getReplyCount(String messageId){
		Finder finder = Finder
				.create("select count(*) from MessageReply messageReply ");
		finder.append("where messageReply.message.fdId = :messageId and messageReply.fdType = :fdType");
		finder.setParam("messageId", messageId);
		finder.setParam("fdType", "03");
		List scores = find(finder);
		long obj = (Long)scores.get(0);
		return (int)obj;
	}
	
	/**
	 * 保存课程学习过程中系统发的课程通过消息
	 * @param bamCourse 进程
	 */
	public void saveCourseMessage(BamCourse bamCourse){
		Map<String, String> param = new HashMap<String, String>();
		param.put("courseName", bamCourse.getCourseInfo().getFdTitle());
		param.put("link", "getCertificate?bamId="+bamCourse.getFdId());
		saveSysMessage(bamCourse.getFdId(),"source",param,null);
	}
	
	/**
	 * 保存课程学习过程中系统发的节通过消息
	 * @param bamCourse 进程
	 * @param catalog 节
	 */
	public void saveLectureMessage(BamCourse bamCourse,CourseCatalog catalog){
		Map<String, String> param = new HashMap<String, String>();
		param.put("lectureNo", catalog.getFdNo().toString());
		param.put("lectureName", catalog.getFdName());
		saveSysMessage(bamCourse.getFdId(),"lecture",param,null);
	}
	
	/**
	 * 保存课程学习过程中系统发的素材通过消息
	 * @param bamCourse 进程
	 * @param catalog 节
	 * @param material 素材
	 */
	public void saveMaterialMessage(BamCourse bamCourse,SourceNote note,CourseCatalog catalog,MaterialInfo material){
		Map<String, String> param = new HashMap<String, String>();
		param.put("lectureNo", catalog.getFdNo().toString());
		param.put("lectureName", catalog.getFdName());
		param.put("materialName", material.getFdName());
		String type = catalog.getFdMaterialType();
		if(Constant.MATERIAL_TYPE_TEST.equals(type) || Constant.MATERIAL_TYPE_JOBPACKAGE.equals(type)){
			param.put("ispass", "通过了");
			
			//总分
			param.put("totalscore", String.valueOf(materialService.getTotalSorce(material.getFdId()).get("totalscore")));
			
			//得分
			if(note!=null){
				param.put("score", note.getFdScore()!=null?String.valueOf(note.getFdScore()):"0");
			}else{
				param.put("score", param.get("totalscore"));
			}
			//及格分
			param.put("passscore", material.getFdScore()!=null?String.valueOf(material.getFdScore()):"0");
			if(Constant.MATERIAL_TYPE_JOBPACKAGE.equals(type)){
				SysOrgPerson person = null;
				if(StringUtils.isNotBlank(bamCourse.getGuideTeachId())){
					person = accountService.load(bamCourse.getGuideTeachId());
				}
				//导师
				param.put("teacher", person!=null?person.getFdName():" ");
				param.put("key", "key");
			}
		}
		String fdKey = material.getFdType();
		if(note!=null){
			fdKey += ":"+note.getFdId();
		}
		saveSysMessage(bamCourse.getFdId(),catalog.getMaterialType(),param,fdKey);
	}
	
	/**
	 * 保存课程学习过程中系统发的素材通过消息
	 * @param bamCourse 进程
	 * @param catalog 节
	 * @param material 素材
	 */
	public void saveMaterialMessage(SourceNote note){
		BamCourse bamCourse = bamCourseService.getCourseByUserIdAndCourseId(note.getFdUserId(), note.getFdCourseId());
		CourseCatalog catalog = bamCourse.getCatalogById(note.getFdCatalogId());
		MaterialInfo material = bamCourse.getMaterialInfoById(note.getFdCatalogId(),note.getFdMaterialId());
		Map<String, String> param = new HashMap<String, String>();
		param.put("lectureNo", catalog.getFdNo().toString());
		param.put("lectureName", catalog!=null?catalog.getFdName():"");
		param.put("materialName", material!=null?material.getFdName():"");
		if(note.getIsStudy()!=null){
			if(!note.getIsStudy()){
				param.put("ispass", "没有通过");
			}else if(note.getIsStudy()){
				param.put("ispass", "通过了");
			}
			//得分
			param.put("score", note.getFdScore()!=null?String.valueOf(note.getFdScore()):"0");
			//总分
			param.put("totalscore", String.valueOf(materialService.getTotalSorce(material.getFdId()).get("totalscore")));
			//及格分
			param.put("passscore", material.getFdScore()!=null?String.valueOf(material.getFdScore()):"0");
			
			if(Constant.MATERIAL_TYPE_JOBPACKAGE.equals(material.getFdType())){
				SysOrgPerson person = null;
				if(StringUtils.isNotBlank(bamCourse.getGuideTeachId())){
					person = accountService.load(bamCourse.getGuideTeachId());
				}
				//导师
				param.put("teacher", person!=null?person.getFdName():" ");
				param.put("key", "key");
			}
		}else{
			param.put("ispass", "提交了");
		}
		String fdKey = material.getFdType();
		if(note!=null && note.getIsStudy()!=null){
			fdKey += ":"+note.getFdId();
		}
		saveSysMessage(bamCourse.getFdId(),catalog.getMaterialType(),param,fdKey);
	}
	
	/**
	 * 保存课程学习过程中系统发的通过消息
	 * @param bamId 进程ID
	 * @param name 消息配置中的name
	 * @param parameters 消息配置中填入的参数 
	 */
	public void saveSysMessage(String bamId,final String name,final Map<String, ?> parameters,String fdKey){
		Template template = templateCache.get(name);
        String content = processTemplate(template, parameters);
        Message message = new Message();
        message.setFdType(Constant.MESSAGE_TYPE_SYS);
        message.setFdContent(content);
        message.setFdModelId(bamId);
        message.setFdModelName(BamCourse.class.getName());
        message.setFdCreateTime(new Date());
        if(StringUtils.isNotBlank(fdKey)){
        	message.setFdKey(fdKey);
        }
        super.save(message);
	}
	
	@Override
    public void afterPropertiesSet() throws Exception {
		templateCache = new ConcurrentHashMap<String, Template>();
        messageAssembleBuilder.init();
        Map<String, String> message = messageAssembleBuilder.getMessages();
        Configuration configuration = new Configuration();
        configuration.setNumberFormat("#");
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        for (Entry<String, String> entry : message.entrySet()) {
            stringLoader.putTemplate(entry.getKey(), entry.getValue());
            templateCache.put(entry.getKey(),new Template(entry.getKey(),
                                            new StringReader(entry.getValue()),
                                            configuration));
        }
        configuration.setTemplateLoader(stringLoader);
    }

    protected String processTemplate(Template template,
                                     Map<String, ?> parameters) {
        StringWriter stringWriter = new StringWriter();
        try {
        	template.process(parameters, stringWriter);
        } catch (Exception e) {
            LOGER.error("处理系统消息参数模板时发生错误：{}", e.toString());
            throw new RuntimeException(e);
        }
        return stringWriter.toString();
    }
    
    public void deleteMessage(String messageId){
    	Message message = get(messageId);
    	if(message!=null){
    		//删除MessageReply
        	List<MessageReply> list = messageReplyService.findByProperty("message.fdId", messageId);
        	for (MessageReply message2 : list) {
        		messageReplyService.delete(message2.getFdId());
			}
        	delete(messageId);
        	if(message.getFdType().equals(Constant.MESSAGE_TYPE_REPLY)){
        		MessageReply r =  messageReplyService.get(messageId);
        		if(r!=null){
        			messageReplyService.delete(messageId);
        		}
        	}
    	}
    }
    
    /**
	 * 获取消息内容，如果是作业的话，需要根据权限显示作业包链接
	 * @param message 消息
	 * @param courseId 课程ID
	 * @param userId 所查看的用户的ID
	 * @return String 消息内容
	 */
	public String getMsgContent(Message message,String courseId,String userId) {
		String msg = message.getFdContent();
		CourseInfo course = courseService.get(courseId);
		CourseParticipateAuth auth = courseParticipateAuthService.findAuthByCourseIdandUserId(courseId,userId);
		if(auth==null || auth.getFdAuthorizer()==null){
			return msg;
		}
		//如果是管理员，或者是课程的创建者，或者是该用户学习课程的授权人，都可以查看作业包
		if(ShiroUtils.isAdmin()||ShiroUtils.getUser().getId().equals(course.getCreator().getFdId()) || 
				ShiroUtils.getUser().getId().equals(auth.getFdAuthorizer().getFdId())){
			if(StringUtil.isNotBlank(message.getFdKey())){
				String msgKey[] = message.getFdKey().split(":");
				if(msgKey!=null && msgKey.length>1){
					if(Constant.MATERIAL_TYPE_JOBPACKAGE.equals(msgKey[0]) && StringUtil.isNotBlank(msgKey[1])){
						String ctx = ContextLoader.getCurrentWebApplicationContext().getServletContext().getContextPath();
						msg += "<a class='send task' href='"+ctx+"/adviser/getTaskDetail?noteId="+msgKey[1]+"&fdType=checked' target='_blank'>查看批改</a>";
					}
				}
			}
		}
		return msg;
	}
    
    
    
    
    
    
    
	
}
