package cn.me.xdf.ldap.init;

import cn.me.xdf.BaseTest;
import cn.me.xdf.common.page.Pagination;
import cn.me.xdf.ldap.model.PeronLdap;
import cn.me.xdf.ldap.service.PersonLdapInService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-12-13
 * Time: 上午10:02
 * To change this template use File | Settings | File Templates.
 */
public class PersonLdapTest extends BaseTest {

    @Autowired
    private PersonLdapInService personLdapInService;

    @Test
    public void testInitData(){
        personLdapInService.executeUpdateData(5);
    }
}
