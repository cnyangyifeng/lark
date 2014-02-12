package cn.me.xdf.service.bam;

import cn.me.xdf.BaseTest;
import cn.me.xdf.model.process.SourceNote;
import cn.me.xdf.service.bam.process.SourceNodeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-11-7
 * Time: 下午4:27
 * To change this template use File | Settings | File Templates.
 */
public class SourceNodeServiceTest extends BaseTest {

    @Autowired
    private SourceNodeService sourceNodeService;

    @Test
    public void testAspect(){
        SourceNote sourceNote = new SourceNote();
        sourceNote.setFdComment("comment");
        sourceNodeService.saveSourceNode(sourceNote);
    }
}
