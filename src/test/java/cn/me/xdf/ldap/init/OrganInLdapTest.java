package cn.me.xdf.ldap.init;

import cn.me.xdf.BaseTest;
import cn.me.xdf.ldap.service.DepartLdapInService;
import cn.me.xdf.ldap.service.GroupLdapInService;
import cn.me.xdf.ldap.service.PersonLdapInService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-12-25
 * Time: 下午2:03
 * To change this template use File | Settings | File Templates.
 */
public class OrganInLdapTest extends BaseTest {


    private static final Logger log = LoggerFactory.getLogger(OrganInLdapTest.class);

    @Autowired
    private DepartLdapInService departLdapInService;

    @Autowired
    private PersonLdapInService personLdapInService;

    @Autowired
    private GroupLdapInService groupLdapInService;

    @Test
    public void executeTodo() {
        log.info("开始执行组织架构同步");
        departLdapInService.executeUpdateData(5);
        personLdapInService.executeUpdateData(5);
        groupLdapInService.executeUpdateData(5);
        log.info("组织架构同步执行完毕");
    }
}
