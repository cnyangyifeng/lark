package cn.me.xdf.common;

import cn.me.xdf.JunitBaseTest;
import cn.me.xdf.common.utils.MyBeanUtils;
import cn.me.xdf.model.base.AttMain;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-12-24
 * Time: 下午2:57
 * To change this template use File | Settings | File Templates.
 */
public class MyBeanUtilTest extends JunitBaseTest {

    public void testCopyProperties() {
        AttMain attMain = new AttMain();

        AttMain attMainCopy = new AttMain();

        attMain.setFdModelName("ssss");
        MyBeanUtils.copyProperties(attMain, attMainCopy);
        System.out.println(attMainCopy.getFdModelName());
    }
}
