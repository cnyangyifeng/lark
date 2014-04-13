package com.kuxue.action.letter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jodd.util.StringUtil;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kuxue.common.hibernate4.Finder;
import com.kuxue.common.json.JsonUtils;
import com.kuxue.common.page.Pagination;
import com.kuxue.common.page.SimplePage;
import com.kuxue.model.letter.ConnectLetter;
import com.kuxue.model.letter.PrivateLetter;
import com.kuxue.model.letter.RelationLetter;
import com.kuxue.model.organization.SysOrgPerson;
import com.kuxue.service.AccountService;
import com.kuxue.service.letter.ConnectLetterService;
import com.kuxue.service.letter.PrivateLetterService;
import com.kuxue.service.letter.RelationLetterService;
import com.kuxue.utils.ShiroUtils;

@Controller
@RequestMapping(value = "/ajax/letter")
@Scope("request")
public class PrivateLetterAjaxController {

	@Autowired
	private PrivateLetterService privateLetterService;
	
	@Autowired
	private RelationLetterService relationLetterService;
	
	@Autowired
	private ConnectLetterService connectLetterService;
	
	@Autowired
	private AccountService accountService;
	
	/**
	 * 找出详细对话信息
	 * @param request
	 */
	@RequestMapping(value="findDetailLetter")
	@ResponseBody
	public String findDetailLetter(HttpServletRequest request){
		String sendUserId = ShiroUtils.getUser().getId();
		String acceptUserId = request.getParameter("fdId");
		String pageNoStr = request.getParameter("pageNo");
		RelationLetter relationLetter = relationLetterService.getModelByPersonId(sendUserId, acceptUserId);
		if(relationLetter==null){
			return null;
		}
		SimpleDateFormat sim = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat sdf=new SimpleDateFormat("h:m a");
		Map data =  new HashMap();
	    int pageNo = 1;
	    if(StringUtil.isNotBlank(pageNoStr)){
	    	pageNo = NumberUtils.createInteger(pageNoStr);
	    }
		Pagination page = privateLetterService.findLetterListByRelationId
				(relationLetter.getFdId(), pageNo, SimplePage.DEF_COUNT);//找出私信分页
		List<ConnectLetter> connectLetters = (List<ConnectLetter>) page.getList();
		
		List<String> dateList = new ArrayList<String>();
		for (ConnectLetter connectLetter : connectLetters) {
			String date = sim.format(connectLetter.getPrivateLetter().getFdCreateTime());
			if(!dateList.contains(date)){
				dateList.add(date);//日期对象
			}
		}
		List<Map> returnlist = new ArrayList<Map>();
		for(int j=0;j<dateList.size();j++){
			Map map = new HashMap();
			List<Map> list = new ArrayList<Map>();
			String date = dateList.get(j);
			map.put("date", dateList.get(j));
			for (ConnectLetter connectLetter : connectLetters) {
				PrivateLetter letter = connectLetter.getPrivateLetter();
				String dateTemp = sim.format(connectLetter.getPrivateLetter().getFdCreateTime());
				if(date.equals(dateTemp)){
					Map letterMap = new HashMap();
					SysOrgPerson sendUser = letter.getSendUser();
					letterMap.put("id", connectLetter.getFdId());
					if(sendUser.getFdId().equals(sendUserId)){
						letterMap.put("isMe", true);//发送者是我
					}else{
						letterMap.put("isMe", false);
						//改变私信状态
						if(!letter.getIsRead()){
							updateLetterStatus(letter);
						}
					}
					letterMap.put("msg", letter.getBody());
					String time =sdf.format(letter.getFdCreateTime());
					letterMap.put("time", time);
					list.add(letterMap);
				}
			}
			map.put("list", list);
			returnlist.add(map);
		}
		//ArrayUtils.sortListByProperty(returnlist, "date", SortType.LOW); 
		Map paging = new HashMap();
		paging.put("totalPage", page.getTotalPage());
		paging.put("currentPage", page.getPageNo());
		paging.put("totalCount", page.getTotalCount());
		paging.put("StartPage", page.getStartPage());
		paging.put("EndPage",page.getEndPage());
		paging.put("StartOperate", page.getStartOperate());
		paging.put("EndOperate", page.getEndOperate());
		paging.put("startNum", page.getStartNum());
		paging.put("endNum", page.getEndNum());
		data.put("returnlist", returnlist);
		data.put("paging",paging);
		return JsonUtils.writeObjectToJson(data);
	}
	
	/**
	 * 找出详细对话信息(用于侧边栏) 找出前10条(不带分页)
	 * @param request
	 */
	@RequestMapping(value="findLeftDetailLetter")
	@ResponseBody
	public String findLeftDetailLetter(HttpServletRequest request){
		String sendUserId = ShiroUtils.getUser().getId();
		String acceptUserId = request.getParameter("fdId");
		RelationLetter relationLetter = relationLetterService.getModelByPersonId(sendUserId, acceptUserId);
		List<Map> returnlist = new ArrayList<Map>();
		Map data =  new HashMap();
		if(relationLetter==null){
			return null;
		}
		SimpleDateFormat sim = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat sdf=new SimpleDateFormat("h:m a");
	    List<ConnectLetter> connectLetters = privateLetterService
	    		.findMaxListByRelationId(relationLetter.getFdId() ,SimplePage.DEF_COUNT);//找出私信分页
	    Collections.reverse(connectLetters);//集合反转 侧边栏的时候需要反转
		List<String> dateList = new ArrayList<String>();
		for (ConnectLetter connectLetter : connectLetters) {
			String date = sim.format(connectLetter.getPrivateLetter().getFdCreateTime());
			if(!dateList.contains(date)){
				dateList.add(date);//日期对象
			}
		}
		for(int j=0;j<dateList.size();j++){
			Map map = new HashMap();
			List<Map> list = new ArrayList<Map>();
			String date = dateList.get(j);
			map.put("date", dateList.get(j));
			for (ConnectLetter connectLetter : connectLetters) {
				PrivateLetter letter = connectLetter.getPrivateLetter();
				String dateTemp = sim.format(connectLetter.getPrivateLetter().getFdCreateTime());
				if(date.equals(dateTemp)){
					Map letterMap = new HashMap();
					SysOrgPerson sendUser = letter.getSendUser();
					letterMap.put("id", connectLetter.getFdId());
					if(sendUser.getFdId().equals(sendUserId)){
						letterMap.put("isMe", true);//发送者是我
					}else{
						letterMap.put("isMe", false);
						//改变私信状态
						if(!letter.getIsRead()){
							updateLetterStatus(letter);
						}
					}
					letterMap.put("msg", letter.getBody());
					String time =sdf.format(letter.getFdCreateTime());
					letterMap.put("time", time);
					list.add(letterMap);
				}
			}
			map.put("list", list);
			returnlist.add(map);
		}
		data.put("returnlist", returnlist);
		return JsonUtils.writeObjectToJson(data);
	}
	
	private void updateLetterStatus(PrivateLetter letter){
		letter.setIsRead(true);
		privateLetterService.save(letter);
	}
	@RequestMapping(value="findPersonPoto")
	@ResponseBody
	public String findPersonPoto(HttpServletRequest request){
		String sendUserId = ShiroUtils.getUser().getId();
		String acceptUserId = request.getParameter("fdId");
		Map<String,String> data =  new HashMap<String,String>();
		SysOrgPerson sendPerson = accountService.get(sendUserId);
		SysOrgPerson acceptPerson = accountService.get(acceptUserId);
		data.put("sendPoto", sendPerson.getPoto());
		data.put("acceptPoto", acceptPerson.getPoto());
		return JsonUtils.writeObjectToJson(data);
	}
	@RequestMapping(value="findPersonById")
	@ResponseBody
	public String findPersonById(HttpServletRequest request){
		String fdId = request.getParameter("fdId");
		SysOrgPerson person = accountService.load(fdId);
		Map<String,String> map = new HashMap<String,String>();
		map.put("department", person.getDeptName());
		map.put("org", person.getHbmParentOrg()!=null?person.getHbmParentOrg().getFdName():"");
		map.put("mail", person.getFdEmail());
		map.put("tel", person.getFdWorkPhone()==null?"不详":person.getFdWorkPhone());
		map.put("imgUrl", person.getPoto());
		map.put("id", person.getFdId());
		map.put("name", person.getRealName());
		return JsonUtils.writeObjectToJson(map);
	}
	/**
	 * 找出我的私信列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="findLetterList")
	@ResponseBody
	public String findLetterList(HttpServletRequest request,Model model){
		String pageNoStr = request.getParameter("pageNo");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd h:m:s a");
	    int pageNo = 1;
	    if(StringUtil.isNotBlank(pageNoStr)){
	    	pageNo = NumberUtils.createInteger(pageNoStr);
	    }
		Pagination page = relationLetterService.findList(
				ShiroUtils.getUser().getId(),pageNo,SimplePage.DEF_COUNT);
		List list = page.getList();
		Map data =  new HashMap();
		List<Map> listMsgData = new ArrayList<Map>();
		for (Object obj : list) {
			Map map = (Map) obj;
			String sendUserId = (String) map.get("SENDUSERID");
			String acceptUserId = (String) map.get("ACCEPTUSERID");
			SysOrgPerson acceptUser = accountService.get(acceptUserId);
			map.put("id", acceptUser.getFdId());
			List<ConnectLetter> totalList = privateLetterService.getTotalNum(sendUserId, acceptUser.getFdId());
			Integer unReadNum = getUnreadUnm(totalList,sendUserId);
			map.put("hasUnread", unReadNum>0?true:false);
			map.put("numUnread", unReadNum);
			map.put("numTotal", totalList.size());
			map.put("msg", (String) map.get("BODY"));
			map.put("timeMsg", sdf.format(map.get("FDCREATETIME")));
			Map user = new HashMap();
			user.put("imgUrl", acceptUser.getPoto());
			user.put("name", acceptUser.getFdName());
			user.put("mail", acceptUser.getFdEmail());
			user.put("org", acceptUser.getHbmParentOrg()!=null?acceptUser.getHbmParentOrg().getFdName():"");
			user.put("department", acceptUser.getDeptName());
			map.put("user", user);
			listMsgData.add(map);
		}
		Map paging = new HashMap();
		paging.put("totalPage", page.getTotalPage());
		paging.put("currentPage", page.getPageNo());
		paging.put("totalCount", page.getTotalCount());
		paging.put("StartPage", page.getStartPage());
		paging.put("EndPage",page.getEndPage());
		paging.put("StartOperate", page.getStartOperate());
		paging.put("EndOperate", page.getEndOperate());
		paging.put("startNum", page.getStartNum());
		paging.put("endNum", page.getEndNum());
		data.put("listMsgData", listMsgData);
		data.put("paging",paging);
		return JsonUtils.writeObjectToJson(data);
	}
	/**
	 * 保存私信
	 * @return
	 */
	@RequestMapping(value="saveLetter", method = RequestMethod.POST)
	@ResponseBody
	public void saveLetter(HttpServletRequest request){
		String body = request.getParameter("body");
		String acceptUsesId = request.getParameter("fdId");
		PrivateLetter letter = privateLetterService.saveLetter(body,ShiroUtils.getUser().getId(), acceptUsesId);
		RelationLetter relationLetter = relationLetterService
				  .getModelByPersonId(ShiroUtils.getUser().getId(), acceptUsesId);
		if(relationLetter==null){
		   relationLetter = relationLetterService.saveRelationLetter(ShiroUtils.getUser().getId(),acceptUsesId);
		}
		saveConnect(letter,relationLetter);
		RelationLetter resverRelation = relationLetterService
				  .getModelByPersonId(acceptUsesId, ShiroUtils.getUser().getId());
		if(resverRelation==null){
			resverRelation = relationLetterService.saveRelationLetter
					(acceptUsesId,ShiroUtils.getUser().getId());
		}
		saveConnect(letter,resverRelation);
		
	}
	private void saveConnect(PrivateLetter letter,RelationLetter relationLetter){
		ConnectLetter connect = new ConnectLetter();
		connect.setPrivateLetter(letter);
		connect.setRelationLetter(relationLetter);
		connectLetterService.save(connect);
	}
	/**
	 * 更改我的所有私信状态
	 * @param request
	 */
	@RequestMapping(value="updateletterStatus")
	@ResponseBody
	public void updateletterStatus(HttpServletRequest request){
		privateLetterService.updateLetterStatusBySendId(ShiroUtils.getUser().getId());
	}
	
	/**
	 * 删除单个私信
	 * @param request
	 */
	@RequestMapping(value="deleteSingleLetter")
	@ResponseBody
	public void deleteSingleLetter(HttpServletRequest request){
		String connectId = request.getParameter("fdId");
		if(StringUtil.isNotBlank(connectId)){
			ConnectLetter connectLetter = connectLetterService.get(connectId);
			String privateLetterId = connectLetter.getPrivateLetter().getFdId();
			String RelationLetterId = connectLetter.getRelationLetter().getFdId();
			connectLetterService.delete(connectId);
			Finder finder = Finder.create(" from ConnectLetter c where c.relationLetter.fdId = :RelationLetterId");
			finder.setParam("RelationLetterId", RelationLetterId);
			int countRelationLetter = connectLetterService.getPage(finder, 1,1).getTotalCount();
			if(countRelationLetter<=0){
				relationLetterService.delete(RelationLetterId);
			}
			int countPrivateLetter = connectLetterService.findByProperty("privateLetter.fdId", privateLetterId).size();
			if(countPrivateLetter<=0){
				privateLetterService.delete(privateLetterId);
			}

		}
	}
	
	/**
	 * 清除当前用户与某个人的私信
	 */
	@RequestMapping(value="deleteLetterByUserId")
	@ResponseBody
	public void deleteLetterByUserId(HttpServletRequest request){
		String sendUserId = ShiroUtils.getUser().getId();
		String acceptUserId = request.getParameter("fdId");
		RelationLetter relationLetter = relationLetterService
				              .getModelByPersonId(sendUserId,acceptUserId);
		deleteLetterByRelation(relationLetter);
	}
	/**
	 * 删除我的所有私信
	 */
	@RequestMapping(value="deleteAllLetter")
	@ResponseBody
	public void deleteAllLetter(){
		String userId = ShiroUtils.getUser().getId();
		List<RelationLetter> list = relationLetterService.findByProperty("sendUser.fdId", userId);
		for(RelationLetter relation:list){
			deleteLetterByRelation(relation);
		}
	}
	private void deleteLetterByRelation(RelationLetter relationLetter){
		List<ConnectLetter> connects = connectLetterService
	            .findByProperty("relationLetter.fdId", relationLetter.getFdId());
		
		//删掉私信关系
		Finder finder = Finder.create("delete from IXDF_NTP_LETTER_CONNECT c where c.RELATIONLETTERID = '"+relationLetter.getFdId()+"'");
		connectLetterService.executeSql(finder.getOrigHql());
		//删relationLetter
		relationLetterService.delete(relationLetter.getFdId());
		//删掉私信
		RelationLetter relationLetter2 = relationLetterService.getModelByPersonId(relationLetter.getAcceptUser().getFdId(), relationLetter.getSendUser().getFdId());
		if(relationLetter2==null){
			Finder finder2 = Finder.create("delete from IXDF_NTP_LETTER p where (p.SENDUSERID = '"+relationLetter.getSendUser().getFdId()+"' and p.ACCEPTUSERID = '"+relationLetter.getAcceptUser().getFdId()+"')" +
					" or (p.SENDUSERID = '"+relationLetter.getAcceptUser().getFdId()+"' and p.ACCEPTUSERID = '"+relationLetter.getSendUser().getFdId()+"') ");
			privateLetterService.executeSql(finder2.getOrigHql());
		}
	}
	
	/**
	 * 得到总共多少未读私信
	 * @return
	 */
	@RequestMapping(value="getUnReadNum")
	@ResponseBody
	public Integer getUnReadNum(){
		if (ShiroUtils.getUser() == null) {
            return 0;
        }
		Integer unReadNum = 0;
		String fdId = ShiroUtils.getUser().getId();
		List<RelationLetter> list = relationLetterService.findByProperty("sendUser.fdId", fdId);
		for(RelationLetter relation:list){
			List<ConnectLetter> connects = connectLetterService
					.findByProperty("relationLetter.fdId", relation.getFdId());
			unReadNum = unReadNum + getUnreadUnm(connects,fdId);
		}
		return unReadNum;
	}
	private Integer getUnreadUnm(List<ConnectLetter> connects,String fdId){
		Integer unReadNum = 0;
		for (ConnectLetter connectLetter : connects) {
			PrivateLetter letter = connectLetter.getPrivateLetter();
			boolean isRead = letter.getIsRead();
			String acceptId = letter.getAcceptUser().getFdId();
			if((!isRead)&&(acceptId.equals(fdId))){
				unReadNum++;
			}
		}
		return unReadNum;
	}
	
	/**
	 * 得到总共多少私信
	 * @return
	 */
	@RequestMapping(value="getTotalNum")
	@ResponseBody
    public Integer getTotalNum(){
		Integer total = 0;
		String fdId = ShiroUtils.getUser().getId();
		List<RelationLetter> list = relationLetterService.findByProperty("sendUser.fdId", fdId);
		for(RelationLetter relation:list){
			List<ConnectLetter> connects = connectLetterService
					.findByProperty("relationLetter.fdId", relation.getFdId());
			total = total + connects.size();
		}
		return total;
	}
}
