package cn.me.xdf.api;

import cn.me.xdf.JunitBaseTest;
import cn.me.xdf.api.bokecc.config.Config;
import cn.me.xdf.api.bokecc.util.APIServiceFunction;
import cn.me.xdf.api.bokecc.util.DemoUtil;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-12-20
 * Time: 下午1:57
 * To change this template use File | Settings | File Templates.
 */
public class ApiTest extends JunitBaseTest {

    public void testApi() throws UnsupportedEncodingException {

        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("userid", Config.userid);
        try {
            paramsMap.put("videoid", "D0B684C4B68625069C33DC5901307461");
            paramsMap.put("userid", Config.userid);
            String title = "09112430y7yu.avi";
            String tag = "NTP";
            String description = title;
            paramsMap.put("title", new String("09112430y7yu.avi ".getBytes("ISO-8859-1"), "UTF-8"));
            paramsMap.put("tag", new String(tag.getBytes("ISO-8859-1"), "UTF-8"));
            paramsMap.put("description", new String(description.getBytes("ISO-8859-1"), "UTF-8"));
            paramsMap.put("categoryid", "0FB948A49BFC3E78");

            long time = System.currentTimeMillis();
            String salt = Config.key;
            String requestURL = APIServiceFunction.createHashedQueryString(paramsMap, time, salt);
            String responsestr = APIServiceFunction.HttpRetrieve(Config.api_updateVideo + "?" + requestURL);
            Document doc = DemoUtil.build(responsestr);
            String videoId = doc.getRootElement().elementText("id");
            System.out.println(videoId);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
