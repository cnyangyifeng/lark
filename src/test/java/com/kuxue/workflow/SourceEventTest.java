package com.kuxue.workflow;

import com.kuxue.BaseTest;
import com.kuxue.workflow.event.SourceEvent;
import com.kuxue.workflow.model.BamPackage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-10-25
 * Time: 下午12:52
 * To change this template use File | Settings | File Templates.
 */
public class SourceEventTest extends BaseTest {


    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testSourceEvent() {
        BamPackage bamPackage = new BamPackage();
        applicationContext.publishEvent(new SourceEvent(bamPackage));
    }
}
