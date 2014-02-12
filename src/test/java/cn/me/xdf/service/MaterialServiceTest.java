package cn.me.xdf.service;

import cn.me.xdf.BaseTest;
import cn.me.xdf.service.material.MaterialService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-10-31
 * Time: 下午5:28
 * To change this template use File | Settings | File Templates.
 */
public class MaterialServiceTest extends BaseTest {

    @Autowired
    private MaterialService materialService;

    @Test
    public void testFind_test(){
      //  materialService.find_test("m_01");
    }
}
