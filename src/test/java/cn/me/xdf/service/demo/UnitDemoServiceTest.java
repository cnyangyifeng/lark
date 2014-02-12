package cn.me.xdf.service.demo;

import cn.me.xdf.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-11-8
 * Time: 下午2:30
 * To change this template use File | Settings | File Templates.
 */
public class UnitDemoServiceTest extends BaseTest {

    @Autowired
    private UnitModelService unitModelService;

    @Test
    public void testTransactionSaveAndUpdate() {
       // unitModelService.findTest();
    }
}
