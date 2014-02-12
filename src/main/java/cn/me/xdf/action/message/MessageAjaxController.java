package cn.me.xdf.action.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.me.xdf.common.hibernate4.Finder;
import cn.me.xdf.common.json.JsonUtils;
import cn.me.xdf.common.page.Pagination;
import cn.me.xdf.model.bam.BamCourse;
import cn.me.xdf.model.base.Constant;
import cn.me.xdf.model.course.CourseInfo;
import cn.me.xdf.model.material.MaterialInfo;
import cn.me.xdf.model.message.Message;
import cn.me.xdf.model.message.MessageReply;
import cn.me.xdf.model.organization.RoleEnum;
import cn.me.xdf.model.organization.SysOrgPerson;
import cn.me.xdf.model.score.Score;
import cn.me.xdf.service.AccountService;
import cn.me.xdf.service.UserRoleService;
import cn.me.xdf.service.bam.BamCourseService;
import cn.me.xdf.service.message.MessageReplyService;
import cn.me.xdf.service.message.MessageService;
import cn.me.xdf.service.score.ScoreService;
import cn.me.xdf.utils.DateUtil;
import cn.me.xdf.utils.ShiroUtils;

/**
 * 消息
 * 
 * @author
 * 
 */
@Controller
@RequestMapping(value = "/ajax/message")
@Scope("request")
public class MessageAjaxController {

	@Autowired
	private MessageService messageService ;
	
	@Autowired
	private MessageReplyService messageReplyService ;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private ScoreService scoreService;
	
	@Autowired
	private BamCourseService bamCourseService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	/**
	 * 支持或反对评论
	 * 
	 * @return 支持数和反对数，格式：支持数_反对数（例：12_11）
	 */
	@RequestMapping(value = "supportOrOpposeMessage")
	@ResponseBody
	public String supportOrOpposeMessage(String messageId,String fdType) {
		return messageService.supportOrOpposeMessage(ShiroUtils.getUser().getId(), messageId, fdType);
	}
	
	/**
	 * 根据消息ID删除消息信息(先删去消息的全部回复信息，再删除消息)
	 * 
	 */
	@RequestMapping(value = "deleteMessageById")
	@ResponseBody
	public void deleteMessageById(String messageId) {
		List<MessageReply> list = messageService.findMessageReplysByMessageId(messageId);
		System.out.println(list.size());
		for (MessageReply messageReply : list) {
			messageReplyService.delete(messageReply);
		}
		messageService.delete(messageId);
	}
	
	/**
	 * 查看用户是否可以支持评论
	 * 
	 * @return String(true:能；support：支持过了；isme：自己的)
	 */
	@RequestMapping(value = "canSupport")
	@ResponseBody
	public String canSupport(String messageId) {
		String userId = ShiroUtils.getUser().getId();
		if(messageReplyService.isSupportMessage(userId, messageId)!=null){
			return "support";
		}else{
			if(messageService.canSupport(userId, messageId)){
				return "true";
			}else{
				return "isme";
			}
		}
	}
	
	/**
	 * 查看用户是否可以反对评论
	 * 
	 * @return String(true:能；opposeed：评论过了；isme：自己的)
	 */
	@RequestMapping(value = "canOppose")
	@ResponseBody
	public String canOppose(String messageId) {
		String userId = ShiroUtils.getUser().getId();
		if(messageReplyService.isOpposeMessage(userId, messageId)!=null){
			return "supportOrOpposeed";
		}else{
			if(messageService.canOppose(userId, messageId)){
				return "true";
			}else{
				return "isme";
			}
		}
	}
	
	/**
	 * 添加评论
	 * 
	 */
	private Message addMessage(String fdModelName,String fdModelId,String fdContent,String fdType,boolean isAnonymous,String userId) {
		Message message = new Message();
		message.setFdModelName(fdModelName);
		message.setFdModelId(fdModelId);
		message.setFdContent(fdContent);
		message.setFdCreateTime(new Date());
		message.setFdType(fdType);
		message.setIsAnonymous(isAnonymous);
		message.setFdUser((SysOrgPerson)accountService.load(userId));
		if(fdType.equals(Constant.MESSAGE_TYPE_REVIEW)||fdType.equals(Constant.MESSAGE_TYPE_REPLY)){
			Finder finder = Finder
					.create("");
			finder.append("from Message m where (m.fdType='01' or m.fdType='04') and m.fdModelName=:fdModelName and m.fdModelId=:fdModelId order by m.fdFloorNo desc");
			finder.setParam("fdModelName", fdModelName);
			finder.setParam("fdModelId", fdModelId);
			Pagination pagination = messageService.getPage(finder, 1,1);
			int no=0;
			if(pagination.getList().size()==0){
				no=1;
			}else{
				Message m = (Message) pagination.getList().get(0);
				no = m.getFdFloorNo()+1;
			}
			message.setFdFloorNo(no);
		}
		

		return messageService.save(message);
	}
	
	/**
	 * 添加评论(针对课程，当前用户)
	 * 
	 */
	@RequestMapping(value = "addCourseMessage")
	@ResponseBody
	private void addCourseMessage(String courseId,String fdContent) {
		addMessage(CourseInfo.class.getName(), courseId, fdContent, Constant.MESSAGE_TYPE_REVIEW, false, ShiroUtils.getUser().getId());
	}
	
	/**
	 * 添加评论(针对资源，当前用户)
	 * 
	 */
	@RequestMapping(value = "addMaterialMessage")
	@ResponseBody
	private void addMaterialMessage(String materialId,String fdContent,String isAnonymity) {
		addMessage(MaterialInfo.class.getName(), materialId, fdContent, Constant.MESSAGE_TYPE_REVIEW, isAnonymity.equals("true")?true:false, ShiroUtils.getUser().getId());
	}
	
	/**
	 * 添加备课心情
	 * 
	 */
	@RequestMapping(value = "addMessageFeeling")
	@ResponseBody
	public void addMessageFeeling(String userId ,String courseId,String fdContent) {
		BamCourse bamCourse = bamCourseService.getCourseByUserIdAndCourseId(userId, courseId);
		if(bamCourse!=null){
			addMessage(BamCourse.class.getName(), bamCourse.getFdId(), fdContent, Constant.MESSAGE_TYPE_MOOD, false, ShiroUtils.getUser().getId());

		}
	}
	
	/**
	 * 添加评论的评论(针对资源，当前用户)
	 * 
	 */
	@RequestMapping(value = "addMaterialMessagesMessage")
	@ResponseBody
	private void addMaterialMessagesMessage(String materialId,String fdContent,String messageId) {
		Message m = addMessage(MaterialInfo.class.getName(), materialId, fdContent, Constant.MESSAGE_TYPE_REPLY, false, ShiroUtils.getUser().getId());
		MessageReply messageReply = new MessageReply();
		messageReply.setFdContent(fdContent);
		messageReply.setFdType("03");
		messageReply.setFdCreateTime(new Date());
		messageReply.setFdUser((SysOrgPerson)accountService.load(ShiroUtils.getUser().getId()));
		Message message = new Message();
		message.setFdId(messageId);
		messageReply.setMessage(message);
		messageReply.setFdId(m.getFdId());
		messageReplyService.save(messageReply);
	}
	
	/**
	 * 添加评论的评论(针对课程，当前用户)
	 * 
	 */
	@RequestMapping(value = "addCourseMessagesMessage")
	@ResponseBody
	private void addCourseMessagesMessage(String materialId,String fdContent,String messageId) {
		Message m = addMessage(CourseInfo.class.getName(), materialId, fdContent, Constant.MESSAGE_TYPE_REPLY, false, ShiroUtils.getUser().getId());
		MessageReply messageReply = new MessageReply();
		messageReply.setFdContent(fdContent);
		messageReply.setFdType("03");
		messageReply.setFdCreateTime(new Date());
		messageReply.setFdUser((SysOrgPerson)accountService.load(ShiroUtils.getUser().getId()));
		Message message = new Message();
		message.setFdId(messageId);
		messageReply.setMessage(message);
		messageReply.setFdId(m.getFdId());
		messageReplyService.save(messageReply);
	}
	
	
	/**
	 * 添加评论的评论(针对备课心情，当前用户)
	 * 
	 */
	@RequestMapping(value = "addFeelingMessagesMessage")
	@ResponseBody
	private String addFeelingMessagesMessage(String fdContent,String messageId) {
		MessageReply messageReply = new MessageReply();
		messageReply.setFdContent(fdContent);
		messageReply.setFdType("03");
		messageReply.setFdCreateTime(new Date());
		messageReply.setFdUser((SysOrgPerson)accountService.load(ShiroUtils.getUser().getId()));
		Message message = new Message();
		message.setFdId(messageId);
		messageReply.setMessage(message);
		messageReplyService.save(messageReply);
		Map map = new HashMap();
		map.put("comment", messageReply.getFdContent());
		map.put("time", DateUtil.getInterval(DateUtil.convertDateToString(messageReply.getFdCreateTime()), "yyyy/MM/dd hh:mm aa"));
		SysOrgPerson orgPerson = messageReply.getFdUser();
		Map userMap = new HashMap();
		userMap.put("imgUrl", orgPerson.getPoto());
		userMap.put("link", "/course/courseIndex?userId="+orgPerson.getFdId());
		userMap.put("name", orgPerson.getRealName());
		userMap.put("mail", orgPerson.getFdEmail()==null?"不详":orgPerson.getFdEmail());
		userMap.put("org", orgPerson.getDeptName()==null?"不详":orgPerson.getDeptName());
		map.put("issuer", userMap);
		map.put("msaageeRId", messageReply.getFdId());
		if(messageReply.getFdUser()==null){
			map.put("canDeleteMr", false);
		}else{
			if((messageReply.getFdUser().getFdId().equals(ShiroUtils.getUser().getId()) || !userRoleService.isEmptyPerson(ShiroUtils.getUser().getId(), RoleEnum.admin))){
				map.put("canDeleteMr", true);
			}else{
				map.put("canDeleteMr", false);
			}
		}
		return JsonUtils.writeObjectToJson(map);
	}
	
	/**
	 * 根据Id评论分页信息
	 * 
	 */
	@RequestMapping(value = "findCommentByModelId")
	@ResponseBody
	private String findCommentByModelId(String modelName,String modelId,int pageNo,int pageSize) {
		
		Pagination pagination =messageService.findCommentPage(modelName, modelId, pageNo, pageSize);
		List<Message> messages = (List<Message>) pagination.getList();
		List<Map> list = new ArrayList<Map>();
		for (int i=0;i<messages.size();i++){
			Message message =messages.get(i);
			Map map = new HashMap();
			map.put("fdId", message.getFdId());
			map.put("content", message.getFdContent());
			map.put("isAnonymous", message.getIsAnonymous());
			map.put("fdCreateTime", DateUtil.getInterval(DateUtil.convertDateToString(message.getFdCreateTime()), "yyyy/MM/dd hh:mm aa"));
			map.put("fdUserName", message.getFdUser().getRealName());
			map.put("fdUserURL", message.getFdUser().getPoto());
			map.put("fdUserEmail", message.getFdUser().getFdEmail());
			map.put("fdUserDept", message.getFdUser().getDeptName());
			map.put("supportCount", messageService.getSupportCount(message.getFdId()));
			map.put("opposeCount", messageService.getOpposeCount(message.getFdId()));
			map.put("replyCount", messageService.getReplyCount(message.getFdId()));
			//int no = pagination.getTotalCount()-i-(pageNo-1)*pageSize;
			map.put("no", message.getFdFloorNo());
			Score score = scoreService.findByModelIdAndUserId(modelName, modelId, message.getFdUser().getFdId());
			map.put("isShowScore", message.getFdType().equals("04")?false:true);
			map.put("score", score==null?0:score.getFdScore());
			map.put("canSport", messageReplyService.isSupportMessage(ShiroUtils.getUser().getId(), message.getFdId())==null?true:false);
			map.put("canOppose", messageReplyService.isOpposeMessage(ShiroUtils.getUser().getId(), message.getFdId())==null?true:false);
			if(message.getFdUser().getFdId().equals(ShiroUtils.getUser().getId()) || !userRoleService.isEmptyPerson(ShiroUtils.getUser().getId(), RoleEnum.admin)){
				map.put("canDelete", true);
			}else{
				map.put("canDelete", false);
			}
			map.put("userId", message.getFdUser().getFdId());
			list.add(map);
		}
		Map maps = new HashMap();
		maps.put("listComments", list);
		return JsonUtils.writeObjectToJson(maps);
	}
	
	/**
	 * 根据课程Id查找分页信息
	 * 
	 */
	@RequestMapping(value = "getCommentPageInfo")
	@ResponseBody
	private String getCommentPageInfo(String modelName , String modelId,int pageNo,int pageSize) {
		Pagination pagination = messageService.findCommentPage(modelName, modelId, pageNo, pageSize);
		int totalSize = pagination.getTotalCount();
		int startLine = (pageNo-1)*(pageSize)+1;
		int totalPage = pagination.getTotalPage();
		int endLine = 0;
		if(totalPage==pageNo){
			if(totalSize==0){
				startLine=0;
				endLine=0;
			}else if(totalSize%pageSize==0){
				endLine = startLine + pageSize-1;
			}else{
				endLine = startLine + totalSize%pageSize-1;
			}
		}else{
			endLine = startLine + pageSize-1;
		}
		Map map = new HashMap();
		map.put("totalSize", totalSize);
		map.put("pageNo", pageNo);
		map.put("pageSize", pageSize);
		map.put("startLine",startLine);
		map.put("endLine", endLine);
		map.put("totalPage", totalPage);
		return JsonUtils.writeObjectToJson(map);
	}
	
	/**
	 * 删除message
	 * 
	 */
	@RequestMapping(value = "removeMessage")
	@ResponseBody
	private String removeMessage(String messageId) {
		messageService.deleteMessage(messageId);
		return "";
	}
	
	/**
	 *  删除messageReplyService
	 * 
	 */
	@RequestMapping(value = "removeMessageReply")
	@ResponseBody
	private String removeMessageReply(String messageReplyId) {
		MessageReply messageReply = messageReplyService.get(messageReplyId);
		Map map = new HashMap();
		map.put("messageId", messageReply.getMessage().getFdId());
		map.put("messageRId", messageReplyId);
		messageReplyService.delete(messageReplyId);
		return JsonUtils.writeObjectToJson(map);
	}

}
