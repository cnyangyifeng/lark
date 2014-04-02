package com.kuxue.ldap.init;

import com.kuxue.BaseTest;
import com.kuxue.common.page.Pagination;
import com.kuxue.ldap.model.PeronLdap;
import com.kuxue.ldap.service.PersonLdapInService;
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
