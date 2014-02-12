package cn.me.xdf.quartz;

import cn.me.xdf.ldap.service.DepartLdapInService;
import cn.me.xdf.ldap.service.GroupLdapInService;
import cn.me.xdf.ldap.service.PersonLdapInService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-12-25
 * Time: 下午1:57
 * To change this template use File | Settings | File Templates.
 */
public class OrganInLdapQuartz implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(OrganInLdapQuartz.class);

    @Autowired
    private DepartLdapInService departLdapInService;

    @Autowired
    private PersonLdapInService personLdapInService;

    @Autowired
    private GroupLdapInService groupLdapInService;


    public void executeTodo() {
        try {
            log.info("开始执行组织架构同步");
            departLdapInService.executeUpdateData(5);
            personLdapInService.executeUpdateData(5);
            groupLdapInService.executeUpdateData(5);
            log.info("组织架构同步执行完毕");
        } catch (Exception ex) {
            log.error("组织架构同步出错:" + ex.getMessage());
        }
    }
}
