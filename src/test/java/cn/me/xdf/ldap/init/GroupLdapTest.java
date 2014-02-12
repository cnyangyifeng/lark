package cn.me.xdf.ldap.init;

import cn.me.xdf.BaseTest;
import cn.me.xdf.ldap.service.GroupLdapInService;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-12-24
 * Time: 下午3:41
 * To change this template use File | Settings | File Templates.
 */
public class GroupLdapTest extends BaseTest {
    @Autowired
    private GroupLdapInService groupLdapInService;

    @Test
    public void testInit() {
        groupLdapInService.initData();
    }


}
