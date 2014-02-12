package cn.me.xdf.ldap.init;


import cn.me.xdf.BaseTest;
import cn.me.xdf.ldap.service.DepartLdapInService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-12-12
 * Time: 上午11:47
 * To change this template use File | Settings | File Templates.
 */
public class DepartLdapTest extends BaseTest {

    @Autowired
    private DepartLdapInService departLdapInService;


    @Test
    public void testInit() {
        departLdapInService.initData();
    }
}
