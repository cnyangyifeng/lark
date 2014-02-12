package cn.me.xdf.common.spring;

import cn.me.xdf.BaseTest;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-10-25
 * Time: 下午2:11
 * To change this template use File | Settings | File Templates.
 */
public class SpringUtilsTest extends BaseTest{

    @Test
    public void testSpringUtils(){
        ApplicationContextHelper.getBean("sourceListener");
    }
}


