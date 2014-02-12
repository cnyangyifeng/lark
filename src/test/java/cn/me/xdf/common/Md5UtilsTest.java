package cn.me.xdf.common;

import cn.me.xdf.JunitBaseTest;
import cn.me.xdf.utils.MD5Util;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-12-17
 * Time: 下午4:50
 * To change this template use File | Settings | File Templates.
 */
public class Md5UtilsTest extends JunitBaseTest {

    public void testPassword(){
        System.out.println(MD5Util.getMD5String("password"));
    }
}
