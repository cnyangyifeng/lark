package com.kuxue.quartz;

import com.kuxue.api.bokecc.config.Config;
import com.kuxue.api.bokecc.util.APIServiceFunction;
import com.kuxue.api.bokecc.util.DemoUtil;
import com.kuxue.common.hibernate4.Value;
import com.kuxue.model.base.AttMain;
import com.kuxue.service.base.AttMainService;
import com.kuxue.task.AttMainTask;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-12-6
 * Time: 下午2:21
 * To change this template use File | Settings | File Templates.
 */
public class AttMainInterfaceQuartz implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(AttMainInterfaceQuartz.class);

    @Autowired
    private AttMainService attMainService;


    public void executeTodo() {
        log.info("开始执行定时任务--attMain");
        List<AttMain> attMainList = attMainService.findByCriteria(AttMain.class,Value.eq("flag", -1),
                Value.eq("fdFileType", "01"),Value.isNotNull("playCode"),Value.isNotNull("fdModelId"),Value.isNotNull("fdModelName"));
        if (CollectionUtils.isEmpty(attMainList))
            return;

        for (AttMain attMain : attMainList) {
            covertVideoToFlag(attMain);
        }
    }

    private void covertVideoToFlag(AttMain attMain) {
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("userid", Config.userid);
        try {
            paramsMap.put("videoid", attMain.getPlayCode());
            paramsMap.put("userid", Config.userid);
            String title = attMain.getFdFileName();
            String tag = "NTP";
            String description = attMain.getFdFileName();
            //paramsMap.put("title", new String(title.getBytes("ISO-8859-1"), "UTF-8"));
            paramsMap.put("tag", tag);
            paramsMap.put("description", description);
            paramsMap.put("categoryid", "0FB948A49BFC3E78");

            long time = System.currentTimeMillis();
            String salt = Config.key;
            String requestURL = APIServiceFunction.createHashedQueryString(paramsMap, time, salt);
            String responsestr = APIServiceFunction.HttpRetrieve(Config.api_updateVideo + "?" + requestURL);
            Document doc = DemoUtil.build(responsestr);
            String videoId = doc.getRootElement().elementText("id");
            if (videoId != null) {
                attMain.setFlag(1);
                log.info("----="+attMain.getFdFileName()+",1111");
                attMainService.update(attMain);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
