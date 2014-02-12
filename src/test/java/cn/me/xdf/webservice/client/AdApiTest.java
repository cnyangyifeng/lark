package cn.me.xdf.webservice.client;

import cn.me.xdf.BaseTest;
import cn.me.xdf.JunitBaseTest;
import cn.me.xdf.service.RegisterService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-12-12
 * Time: 下午2:32
 * To change this template use File | Settings | File Templates.
 */
public class AdApiTest extends BaseTest {

    @Autowired
    private RegisterService registerService;

    @Test
    public void testUpdateUserPoto(){
        registerService.updatePerToDBIXDF("yangyifeng","D:\\temp\\resources\\www\\201311\\28124343yaz4.jpg","");
    }


}
