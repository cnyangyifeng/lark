package cn.me.xdf.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.me.xdf.model.base.AttMain;
import cn.me.xdf.model.organization.SysOrgDepart;
import cn.me.xdf.model.organization.SysOrgElement;
import cn.me.xdf.model.organization.SysOrgPerson;
import cn.me.xdf.model.organization.SysOrgPersonTemp;
import cn.me.xdf.service.AccountService;
import cn.me.xdf.service.RegisterService;
import cn.me.xdf.service.SysOrgDepartService;
import cn.me.xdf.service.base.AttMainService;
import cn.me.xdf.utils.MD5Util;
import cn.me.xdf.utils.ShiroUtils;

@Controller
@RequestMapping(value = "/register")
@Scope("request")
public class RegisterController {


    @Autowired
    private RegisterService registerService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SysOrgDepartService SysOrgDepartService;

    @Autowired
    private AttMainService attMainService;
    


    @RequestMapping(value = "add")
    public String registerForm(Model model) {
        List<SysOrgDepart> elements = SysOrgDepartService.findTypeis1();
        model.addAttribute("elements", elements);
        return "/base/register/add";
    }

   

    /**
     * 返回修改图像
     *
     * @return
     */
    @RequestMapping(value = "updateIco")
    public String updateIco(Model model) {
        if (ShiroUtils.getUser() == null) {
            return "redirect:/login";
        }
        model.addAttribute("active", "photo");
        String uid = ShiroUtils.getUser().getId();
        SysOrgPerson person = accountService.load(uid);
        model.addAttribute("fdIcoUrl", person.getPoto());
        return "/base/newTeacher/editIco";
    }

    /**
     * 更新新教师图像
     *
     * @param sysOrgPersonTemp
     * @param request
     * @return
     */
    @RequestMapping(value = "updateTeacherPoto", method = RequestMethod.POST)
    public String updateTeacherPoto(SysOrgPersonTemp sysOrgPersonTemp,
                                HttpServletRequest request) {
        if (ShiroUtils.getUser() == null) {
            return "redirect:/login";
        }
        String uid = ShiroUtils.getUser().getId();
        SysOrgPerson person = accountService.get(uid);
        String attMainId = request.getParameter("attId");
        if (StringUtil.isNotBlank(attMainId)) {
            AttMain attMain = attMainService.get(attMainId);
            attMain.setFdModelId(person.getFdId());
            attMain.setFdModelName(SysOrgPerson.class.getName());
            attMain.setFdKey("Person");
            attMainService.save(attMain);
            if(person.getFdIsEmp().equals("1")){
            	registerService.updatePerToDBIXDF(person.getLoginName(), attMain.getFdFilePath(), uid);
            }else{
            	person.setFdPhotoUrl("common/file/image/"+attMainId);
            	accountService.save(person);
            }
        }
        return "redirect:/register/updateTeacher";
    }

    /**
     * 点击修改密码时返回修改密码页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "changePwd")
    public String changePwd(Model model,HttpServletRequest request) {
        if (ShiroUtils.getUser() == null) {
            return "redirect:/login";
        }
        String uid = request.getParameter("id");
        if(StringUtil.isBlank(uid)){
        	uid = ShiroUtils.getUser().getId();
        }
        model.addAttribute("active", "pwd");
        SysOrgPerson person = accountService.get(uid);
        model.addAttribute("person", person);
        return "/base/newTeacher/changePwd";
    }

    /**
     * 修改密码的方法
     *
     * @param sysOrgPersonTemp
     * @param request
     * @return
     */
    @RequestMapping(value = "updateTeacherPwd", method = RequestMethod.POST)
    public String updateTeacherPwd(SysOrgPerson sysOrgPerson) {
        if (ShiroUtils.getUser() == null) {
            return "redirect:/login";
        }
        String fdId = sysOrgPerson.getFdId();
        SysOrgPerson person = accountService.get(fdId);
        person.setPassword(MD5Util.getMD5String(sysOrgPerson.getPassword()));
        accountService.save(person);
        return "redirect:/register/changePwd";
    }

    /**
     * 新老师更改信息
     *
     * @param model
     */
    @RequestMapping(value = "updateTeacher")
    public String updateTeacher(Model model,HttpServletRequest request) {
    	if (ShiroUtils.getUser() == null) {
            return "redirect:/login";
        }
        String uid = request.getParameter("id");
        if(StringUtil.isBlank(uid)){
        	uid = ShiroUtils.getUser().getId();
        }else{
        	if(!ShiroUtils.isAdmin()){
        		uid = ShiroUtils.getUser().getId();
        	}
        }
        model.addAttribute("active", "user");
        SysOrgPerson person = accountService.get(uid);
        model.addAttribute("person", person);
        model.addAttribute("fdIcoUrl", person.getPoto());
        if (person.getHbmParent() != null) {
        	person.setDeptId(person.getHbmParent().getFdId());
            person.setDeptName(person.getHbmParent().getFdName());
            if(person.getHbmParent().getHbmParentOrg() != null){
            	model.addAttribute("sysParOrg", person.getHbmParent().getHbmParentOrg().getFdName());
                model.addAttribute("sysParOrgId", person.getHbmParent().getHbmParentOrg().getFdId());	
            }
        }
        List<SysOrgDepart> elements = SysOrgDepartService.findTypeis1();
        model.addAttribute("elements", elements);
        return "/base/newTeacher/edit";
    }
    
    /**
     * 更改用户基本信息)
     *
     * @param sysOrgPersonTemp
     * @param request
     * @return
     */
    @RequestMapping(value = "updateOtherData", method = RequestMethod.POST)
    public String updateOtherData(SysOrgPerson sysOrgPerson,HttpServletRequest request) {
        if (ShiroUtils.getUser() == null) {
            return "redirect:/login";
        }
        SysOrgPerson person = accountService.load(sysOrgPerson.getFdId());
        person.setFdBirthDay(sysOrgPerson.getFdBirthDay());
        person.setFdBloodType(sysOrgPerson.getFdBloodType());
        person.setFdSex(sysOrgPerson.getFdSex());
        person.setFdWorkPhone(sysOrgPerson.getFdWorkPhone());
        //person.setFdIdentityCard(sysOrgPerson.getFdIdentityCard());
        person.setSelfIntroduction(sysOrgPerson.getSelfIntroduction());
        if(person.getFdIsEmp().equals("0")){
        	SysOrgElement sysOrgElement = SysOrgDepartService.getSysOrgElementById(sysOrgPerson.getDeptId());
        	person.setHbmParent(sysOrgElement);
        	person.setFdName(sysOrgPerson.getFdName());
        }
        accountService.save(person);
        String adminFlag = request.getParameter("adminFlag");
        if(StringUtil.isNotBlank(adminFlag)){
          return "redirect:/admin/user/updateUserInfo/"+person.getFdId();
        }
        return "redirect:/register/updateTeacher";
    }

}
