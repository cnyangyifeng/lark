package cn.me.xdf.ldap.service;

import cn.me.xdf.common.page.Pagination;
import cn.me.xdf.common.page.SimplePage;
import cn.me.xdf.common.utils.Identities;
import cn.me.xdf.ldap.BaseLdapDao;
import cn.me.xdf.ldap.LdapUtils;
import cn.me.xdf.ldap.model.PeronLdap;
import cn.me.xdf.model.organization.SysOrgConstant;
import cn.me.xdf.model.organization.SysOrgElement;
import cn.me.xdf.service.SysOrgPersonService;
import cn.me.xdf.utils.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ldap.control.PagedResult;
import org.springframework.ldap.control.PagedResultsCookie;
import org.springframework.ldap.control.PagedResultsDirContextProcessor;
import org.springframework.ldap.control.PagedResultsRequestControl;
import org.springframework.ldap.core.*;
import org.springframework.stereotype.Service;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import java.sql.Connection;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-12-12
 * Time: 下午6:01
 * To change this template use File | Settings | File Templates.
 */
@Service
public class PersonLdapInService extends LdapInService {

    private static final Logger log = LoggerFactory.getLogger(PersonLdapInService.class);

    @Autowired
    private LdapLogService ldapLogService;

    @Autowired
    private LdapTemplate ldapTemplate;


    public void initData(List<Map<String, Object>> values) {
        log.info("开始初始化人员表");
        updateOrg(values);
    }

    @Override
    public void initData() {
    	List<Map<String, Object>> list = ldapTemplate.search(
                "cn=users", "(&(objectClass=xdf-person))",
                new PersonContextMapper());
    	 updateOrg(list);
    }

    @Override
    public String executeUpdateData(int day) {
        String date = DateUtil.convertDateToString(DateUtils.addDays(new Date(), 0 - day), "yyyyMMddHHmmss.ssssss");
        List<Map<String, Object>> list = ldapTemplate.search(
                "cn=users", "(&(objectClass=xdf-person)(modifyTimeStamp>=" + date + "))",
                new PersonContextMapper());
        String msg = updateOrg(list);
        ldapLogService.saveLog(msg);
        return msg;
    }


    public String updateOrg(List<Map<String, Object>> values) {
        int insertSize = 0;
        int updateSize = 0;
        for (Map<String, Object> map : values) {
            List<Map> lists = findByNamedQuery("person.selectElementByKey", map, Map.class);
            List<Map> departList = findByNamedQuery("selectElementByKey", map, Map.class);
            if (departList.size() > 0) {
                Map departMap = departList.get(0);
                Object v = departMap.get("FDID");
                if (v != null) {
                    map.put("FD_PARENTID", v);
                }else{
                	map.put("FD_PARENTID", "");
                }
            }else{
            	map.put("FD_PARENTID", "");
            }
            if (CollectionUtils.isEmpty(lists)) {
                log.info("开始初始化人员表-Insert:" + map.get("FD_NO"));
                updateByNamedQuery("saveElement", map);
                updateByNamedQuery("updateElementParent", map);
                updateByNamedQuery("person.saveElement", map);
                insertSize++;
            } else {
                log.info("开始初始化人员表-Update" + map.get("FD_NO")+";parentid:"+map.get("PARENTID"));
                map.put("FDID", lists.get(0).get("FDID"));
                updateByNamedQuery("person.updateElement", map);
                updateByNamedQuery("updateElement", map);
                updateSize++;
            }
        }


        return "人员：本次新增" + insertSize + ",更新:" + updateSize;
    }
    
    
    
    
    public String updateOrg2(List<Map<String, Object>> values) {
        int insertSize = 0;
        int updateSize = 0;
        for (Map<String, Object> map : values) {
            List<Map> lists = findByNamedQuery("person.selectElementByKey", map, Map.class);
            if(updateSize>2){
            	break;
            }
            map.put("FD_PARENTID", map.get("PARENTID"));
            if (CollectionUtils.isEmpty(lists)) {
                log.info("开始初始化人员表-Insert:" + map.get("FD_NO"));
                updateByNamedQuery("saveElement", map);
                updateByNamedQuery("person.saveElement", map);
                insertSize++;
            } else {
                log.info("开始初始化人员表-Update" + map.get("FD_NO"));
                map.put("FDID", lists.get(0).get("FDID"));
                updateByNamedQuery("person.updateElement", map);
                updateByNamedQuery("updateElement", map);
                updateSize++;
            }
        }


        return "人员：本次新增" + insertSize + ",更新:" + updateSize;
    }


    private static class PersonContextMapper implements ContextMapper {
        public Object mapFromContext(Object ctx) {
            DirContextAdapter context = (DirContextAdapter) ctx;
            Map<String, Object> map = new ConcurrentHashMap<String, Object>();
            //FD_ID,AVAILABLE,CREATETIME,FD_NAME,FD_NO,FD_ORG_TYPE,LDAPDN,FD_PARENTID
            map.put("FDID", Identities.generateID());

            map.put("AVAILABLE", "1".equals(context.getStringAttribute("displayed")));
            map.put("CREATETIME", new Date());

            map.put("LDAPDN",context.getDn().toString());
            LdapUtils.setStringAttribute(context, map, "FD_LOGIN_NAME", "cn");
            LdapUtils.setStringAttribute(context, map, "FD_NAME", "name_attribute");
            LdapUtils.setStringAttribute(context, map, "FD_NO", "employeeNumber");
            LdapUtils.setStringAttribute(context, map, "PARENTID", "departmentNumber");
            LdapUtils.setStringAttribute(context, map, "FD_EMAIL", "mail");
            LdapUtils.setStringAttribute(context, map, "FDMOBILENO", "mobile");
            LdapUtils.setStringAttribute(context, map, "FD_WORK_PHONE", "telephonenumber");
            LdapUtils.setStringAttribute(context, map, "FD_IDENTITY_CARD", "uid");
            LdapUtils.setStringAttribute(context, map, "FD_IS_EMP", "fdIsEmp");
            LdapUtils.setStringAttribute(context, map, "FD_SEX", "sex");
            map.put("FD_ORG_TYPE", SysOrgConstant.ORG_TYPE_PERSON);

            map.put("FD_PASSWORD", "c4ca4238a0b923820dcc509a6f75849b");

            return map;
        }
    }


}
