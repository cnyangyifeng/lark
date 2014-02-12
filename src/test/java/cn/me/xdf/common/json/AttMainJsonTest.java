package cn.me.xdf.common.json;

import cn.me.xdf.JunitBaseTest;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-12-2
 * Time: 上午11:56
 * To change this template use File | Settings | File Templates.
 */
public class AttMainJsonTest extends JunitBaseTest {

    public void testJsonToObject() {
        String json = "{\"state\":1,\"errorMsg\":\"sss\"} ";
        Map<String, Object> map = JsonUtils.readObjectByJson(json, Map.class);
        System.out.println(map.get("state"));


    }


}
