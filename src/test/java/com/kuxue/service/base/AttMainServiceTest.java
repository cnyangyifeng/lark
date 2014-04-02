package com.kuxue.service.base;

import com.kuxue.BaseTest;
import com.kuxue.model.base.DocInterfaceModel;
import com.kuxue.service.plugin.AttMainPlugin;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-12-2
 * Time: 下午3:03
 * To change this template use File | Settings | File Templates.
 */
public class AttMainServiceTest extends BaseTest {

    @Autowired
    private AttMainService attMainService;

    @Test
    public void testAddAttMain() {
      attMainService.save(null);
       // System.out.println(AttMainPlugin.getSwfPath(attMainService.get("142bbbca857f4b136ef702041b59781a"),DocInterfaceModel.getPlayCode));
    	
    //System.out.println(attMainService.get("142bbbca857f4b136ef702041b59781a").getFileUrl());
    	
    	//System.out.println(attMainService.get("142bce9c6e47bc8a2ead7054ef49a26c").getFileUrl());
    }
}
