package cn.me.xdf.action.organization;import java.io.UnsupportedEncodingException;import java.util.ArrayList;import java.util.HashMap;import java.util.List;import java.util.Map;import javax.servlet.http.HttpServletRequest;import cn.me.xdf.common.hibernate4.Value;import org.apache.commons.lang3.StringUtils;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.context.annotation.Scope;import org.springframework.stereotype.Controller;import org.springframework.web.bind.annotation.RequestMapping;import org.springframework.web.bind.annotation.ResponseBody;import cn.me.xdf.common.json.JsonUtils;import cn.me.xdf.model.organization.RoleEnum;import cn.me.xdf.model.organization.SysOrgElement;import cn.me.xdf.model.organization.SysOrgPerson;import cn.me.xdf.model.organization.User;import cn.me.xdf.service.AccountService;import cn.me.xdf.service.SysOrgDepartService;import cn.me.xdf.service.SysOrgPersonService;import cn.me.xdf.utils.ShiroUtils;@Controller@RequestMapping(value = "/ajax/user")@Scope("request")public class UserAjaxController {    @Autowired    private AccountService accountService;    @Autowired    private SysOrgPersonService sysOrgPersonService;    @Autowired	private SysOrgDepartService departService;	    /**     * 根据人员名称查询人员信息     *     * @param request     * @return     */    @RequestMapping(value = "findByName")    @ResponseBody    public List<User> findByName(HttpServletRequest request) {        String key = request.getParameter("q");        List<User> users = new ArrayList<User>();        if (StringUtils.isBlank(key))            return null;        // 根据用户输入的关键字查询登录名和姓名，        // 排除最高用户admin        List<SysOrgPerson> orgPersons = sysOrgPersonService.findUserByLinkLoginAndRealNameTop10(key);        User user = null;        for (SysOrgPerson person : orgPersons) {            user = new User();            user.setId(person.getFdId());            user.setImgUrl(person.getPoto());            user.setName(person.getRealName());            user.setMail(person.getFdEmail());            user.setDepartment(person.getDeptName());            user.setOrg("");            user.setSelfIntroduction(person.getSelfIntroduction());            users.add(user);        }        return users;    }    /**     * 根据人员名称和学校角色查询     *     * @param request     * @return     */    @RequestMapping(value = "findByNameAndRole")    @ResponseBody    public List<User> findByNameAndRole(HttpServletRequest request) {        String key = request.getParameter("q");        List<User> users = new ArrayList<User>();        if (StringUtils.isBlank(key))            return users;        // 根据用户输入的关键字查询登录名和姓名，        // 排除最高用户admin        List<SysOrgPerson> orgPersons = accountService.findUserByLinkLoginOrRealNameAndRole(key, RoleEnum.campus);        User user = null;        for (SysOrgPerson person : orgPersons) {            user = new User();            user.setId(person.getFdId());            user.setImgUrl(person.getPoto());            user.setName(person.getRealName());            user.setMail(person.getFdEmail());            user.setDepartment(person.getDeptName());            user.setOrg("");            users.add(user);        }        return users;    }    /**     * 根据机构编码查询人员的信息     *     * @param request     * @return     */    @RequestMapping(value = "findByDept")    @ResponseBody    public List<User> findByDept(HttpServletRequest request) {        String key = request.getParameter("q");// 人员名称        String deptId = request.getParameter("deptId");// 部门编码        List<User> users = new ArrayList<User>();        if (StringUtils.isBlank(key))            return users;        if (StringUtils.isBlank(deptId))            return users;        // 根据用户输入的关键字查询登录名和姓名，        // 排除最高用户admin        List<SysOrgPerson> orgPersons = null;        if (StringUtils.isBlank(deptId)) {            orgPersons = accountService.findByCriteria(SysOrgPerson.class,                    Value.or(Value.like("loginName", key), Value.like("fdName", key)));        } else {            orgPersons = accountService.findByCriteria(SysOrgPerson.class,                    Value.or(Value.like("loginName", key), Value.like("fdName", key)), Value.eq("hbmParent.fdId", deptId));        }        User user = null;        for (SysOrgPerson person : orgPersons) {            user = user = new User();            user.setId(person.getFdId());            user.setImgUrl(person.getPoto());            user.setName(person.getRealName());            user.setMail(person.getFdEmail());            user.setDepartment(person.getDeptName());            user.setOrg("");            ;            users.add(user);        }        return users;    }    /**     * 根据机构编码查询人员的信息     *     * @param request     * @return     * @throws UnsupportedEncodingException     */    @RequestMapping(value = "findByOrg")    @ResponseBody    public List<User> findByOrg(HttpServletRequest request)            throws UnsupportedEncodingException {        String key = request.getParameter("q");// 人员名称        String deptId = request.getParameter("deptId");// 机构编码        List<User> users = new ArrayList<User>();        if (StringUtils.isBlank(key))            return users;        if (StringUtils.isBlank(deptId))            return users;        // 根据用户输入的关键字查询登录名和姓名，        // 排除最高用户admin        List<SysOrgPerson> orgPersons = new ArrayList<SysOrgPerson>();        if (ShiroUtils.isAdmin()) {            orgPersons = accountService.findUserByLinkLoginAndRealName(key);        } else {            orgPersons = accountService.findUserByLinkLoginAndRealNameAndOrg(key, deptId);        }        User user = null;        for (SysOrgPerson person : orgPersons) {            user = user = new User();            user.setId(person.getFdId());            user.setImgUrl(person.getPoto());            user.setName(person.getRealName());            user.setMail(person.getFdEmail());            user.setDepartment(person.getDeptName());            user.setOrg("");            ;            users.add(user);        }        return users;    }    /**     * 根据多个机构编码查询人员的信息     *     * @param request     * @return     */    @RequestMapping(value = "findByOrgs")    @ResponseBody    public List<User> findByOrgs(HttpServletRequest request) {        String key = request.getParameter("q");// 人员名称        String schIds = request.getParameter("schIds");// 机构编码        List<User> users = new ArrayList<User>();        if (StringUtils.isBlank(key))            return users;        // 根据用户输入的关键字查询登录名和姓名，        // 排除最高用户admin        List<SysOrgPerson> orgPersons = null;        User user = null;        if (StringUtils.isBlank(schIds)) {            orgPersons = accountService.findUserByLinkLoginAndRealName(key);            for (SysOrgPerson person : orgPersons) {                user = user = new User();                user.setId(person.getFdId());                user.setImgUrl(person.getPoto());                user.setName(person.getRealName());                user.setMail(person.getFdEmail());                user.setDepartment(person.getDeptName());                user.setOrg("");                ;                users.add(user);            }        } else {            String[] arrSchid = schIds.split(",");            for (String schid : arrSchid) {                if (StringUtils.isNotBlank(schid)) {                    orgPersons = accountService.findUserByLinkLoginAndRealNameAndOrg(key, schid);                    for (SysOrgPerson person : orgPersons) {                        user = user = new User();                        user.setId(person.getFdId());                        user.setImgUrl(person.getPoto());                        user.setName(person.getRealName());                        user.setMail(person.getFdEmail());                        user.setDepartment(person.getDeptName());                        user.setOrg("");                        ;                        users.add(user);                    }                }            }        }        return users;    }    @RequestMapping(value="getOrg")	@ResponseBody	public String getOrg(HttpServletRequest request){		String key=request.getParameter("q");		List<SysOrgElement> orgs = new ArrayList<SysOrgElement>();        if (StringUtils.isBlank(key))            return null;        orgs=departService.getSchools(key);        List<Map> schools=new ArrayList<Map>();        for(SysOrgElement sysOrgElement:orgs){        	Map school=new HashMap();        	school.put("id", sysOrgElement.getFdId());        	school.put("name", sysOrgElement.getFdName());        	schools.add(school);        }        return JsonUtils.writeObjectToJson(schools);	}}