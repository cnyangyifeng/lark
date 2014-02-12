package cn.me.xdf.service.base;

import cn.me.xdf.BaseTest;
import cn.me.xdf.common.hibernate4.Value;
import cn.me.xdf.model.base.AttMain;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-12-6
 * Time: 下午3:26
 * To change this template use File | Settings | File Templates.
 */
public class AttMainServiceTest2 extends BaseTest {

    @Autowired
    private AttMainService attMainService;

    @Test
    public void testFindAttMain() {
        //where fdfiletype='01' and flag=-1 and playcode is not null
        List<AttMain> attMainList = attMainService.findByCriteria(AttMain.class,Value.eq("flag", -1),
                Value.eq("fdFileType", "01"),Value.isNotNull("playCode"));
        if (CollectionUtils.isEmpty(attMainList))
            return;
        for (AttMain attMain : attMainList) {
            if (attMain.getPlayCode() == null) {
                continue;
            }
            String fileUrl = attMain.getFileUrl();
            System.out.println(fileUrl);
            attMain.setFileUrl(fileUrl);
            attMainService.update(attMain);
        }
    }
}
