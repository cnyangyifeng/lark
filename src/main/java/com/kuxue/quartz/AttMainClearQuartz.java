package com.kuxue.quartz;

import com.kuxue.common.hibernate4.Value;
import com.kuxue.model.base.AttMain;
import com.kuxue.service.base.AttMainService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-12-24
 * Time: 下午3:29
 * To change this template use File | Settings | File Templates.
 */
public class AttMainClearQuartz implements Serializable {

    @Autowired
    private AttMainService attMainService;

    public void executeTodo() {
        List<AttMain> attMains = attMainService.findByCriteria(AttMain.class, Value.isNull("fdModelId"), Value.isNull("fdModelName"));
        for (AttMain attMain : attMains) {
            attMainService.delete(AttMain.class, attMain.getFdId());
        }
    }
}
