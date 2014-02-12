package cn.me.xdf.ldap.service;

import cn.me.xdf.common.hibernate4.Value;
import cn.me.xdf.model.organization.SysOrgConstant;
import cn.me.xdf.model.organization.SysOrgElement;
import cn.me.xdf.model.organization.SysOrgGroup;
import cn.me.xdf.utils.DateUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-12-25
 * Time: 上午9:50
 * To change this template use File | Settings | File Templates.
 */
@Service
public class GroupLdapInService extends LdapInService {


    private static final Logger log = LoggerFactory.getLogger(GroupLdapInService.class);

    @Autowired
    private LdapTemplate ldapTemplate;

    @Autowired
    private LdapLogService ldapLogService;


    @Override
    public void initData() {
        List<SysOrgGroup> list = ldapTemplate.search(
                "ou=群组", "(&(objectClass=xdf-group))",
                new GroupContextMapper());
        String msg = updateGroup(list);
        ldapLogService.saveLog(msg);
    }

    @Override
    public String executeUpdateData(int day) {
        String date = DateUtil.convertDateToString(DateUtils.addDays(new Date(), 0 - day), "yyyyMMddHHmmss.ssssss");
        List<SysOrgGroup> list = ldapTemplate.search(
                "ou=群组", "(&(objectClass=xdf-group)(modifyTimeStamp>=" + date + "))",
                new GroupContextMapper());
        String msg = updateGroup(list);
        ldapLogService.saveLog(msg);
        return msg;
    }


    private String updateGroup(List<SysOrgGroup> groups) {

        int insertSize = 0;
        int updateSize = 0;

        for (SysOrgGroup group : groups) {
            List<SysOrgGroup> groupList = findByCriteria(SysOrgGroup.class, Value.eq("ldapDN", group.getLdapDN()));
            if (groupList != null && groupList.size() > 0) {
                updateSize++;
                group.setFdId(groupList.get(0).getFdId());
            } else {
                insertSize++;
            }

            String[] members = group.getPersonMemeber();
            if (members != null) {
                for (String s : members) {
                    String base = s.replaceAll(",dc=xdf,dc=cn", "");
                    log.info(base);
                    if (base.indexOf(',') - base.indexOf('=') <= 1) {
                        continue;
                    }
                    List<String> personFdNos = ldapTemplate.search(base, "(&(objectClass=xdf-person))", new PersonContextMapper());
                    for (String personFdNo : personFdNos) {
                        List<SysOrgElement> personElements = findByCriteria(SysOrgElement.class, Value.eq("fdNo", personFdNo));
                        if (personElements.size() > 0) {
                            group.getFdMembers().addAll(personElements);
                        }
                    }
                }
            }

            String[] ibmMembers = group.getOrgMember();
            if (ibmMembers != null) {
                for (String s : ibmMembers) {
                    String base = s.substring(3, s.indexOf(','));
                    List<SysOrgElement> groupElements = findByCriteria(SysOrgElement.class, Value.eq("fdNo", base));
                    if (groupElements.size() > 0) {
                        group.getFdMembers().addAll(groupElements);
                    }
                }
            }

            save(group);
        }

        return "群组：本次新增" + insertSize + ",更新:" + updateSize;
    }


    private static class PersonContextMapper implements ContextMapper {
        public Object mapFromContext(Object ctx) {
            DirContextAdapter context = (DirContextAdapter) ctx;
            return context.getStringAttribute("employeeNumber");
        }
    }

    private static class GroupContextMapper implements ContextMapper {
        public Object mapFromContext(Object ctx) {
            DirContextAdapter context = (DirContextAdapter) ctx;
           /* Map<String, Object> map = new HashMap<String, Object>();
            map.put("FDID", Identities.generateID());
            map.put("AVAILABLE", true);
            map.put("CREATETIME", new Date());
            map.put("FD_NAME", context.getStringAttribute("cn"));
            map.put("FD_NO", "");
            map.put("FD_ORG_TYPE", SysOrgConstant.ORG_TYPE_GROUP);
            map.put("LDAPDN", context.getDn());
            map.put("member", context.getStringAttributes("member"));
            map.put("ibm-memberGroup", context.getStringAttributes("ibm-memberGroup"));

            return map;*/

            SysOrgGroup element = new SysOrgGroup();
            element.setAvailable(true);
            element.setCreateTime(new Date());
            element.setFdName(context.getStringAttribute("cn"));
            element.setFdNo("");
            element.setFdOrgType(SysOrgConstant.ORG_TYPE_GROUP);
            element.setLdapDN(context.getDn().toString());
            element.setPersonMemeber(context.getStringAttributes("member"));
            element.setOrgMember(context.getStringAttributes("ibm-memberGroup"));
            return element;
        }
    }
}
