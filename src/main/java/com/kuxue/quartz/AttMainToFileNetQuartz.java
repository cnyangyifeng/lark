package com.kuxue.quartz;

import com.kuxue.common.hibernate4.Value;
import com.kuxue.model.base.AttMain;
import com.kuxue.service.base.AttMainService;
import com.kuxue.service.plugin.AttMainPlugin;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 14-1-7
 * Time: 上午10:22
 * To change this template use File | Settings | File Templates.
 */
public class AttMainToFileNetQuartz implements Serializable {


    private static final Logger log = LoggerFactory.getLogger(AttMainInterfaceQuartz.class);

    private static Map<String, String> map = new HashMap<String, String>();

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private AttMainService attMainService;

    /**
     * 执行
     */
    public void executeTodo() {
        log.info("开始执行定时任务--attMain");
        List<AttMain> attMainList = attMainService.findByCriteria(AttMain.class,
                //Value.in("fdFileType", new String[]{"01", "02", "04", "05"}),Value.eq("flag", -1),
                Value.isNull("fileNetId"), Value.isNotNull("fdModelId"), Value.isNotNull("fdModelName"));
        if (CollectionUtils.isEmpty(attMainList))
            return;

        for (AttMain attMain : attMainList) {
            if (map.get(attMain.getFdId()) != null) {
                continue;
            }
            executeInterfaceSave(attMain);
        }
    }


    public void executeInterfaceSave(final AttMain attMain) {
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                map.put(attMain.getFdId(), attMain.getFdId());
                String isCovert = "0";
                if ("04".equals(attMain.getFdFileType()) || "05".equals(attMain.getFdFileType())) {
                    isCovert = "1";
                }
                String fileNetId = AttMainPlugin.addDoc(attMain, isCovert);
                if (StringUtils.isNotBlank(fileNetId)) {
                    attMain.setFileNetId(fileNetId);

                    attMainService.update(attMain);
                    map.remove(attMain.getFdId());
                }
            }
        });
    }

}
