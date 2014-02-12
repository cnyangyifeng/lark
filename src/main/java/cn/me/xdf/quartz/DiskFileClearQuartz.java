package cn.me.xdf.quartz;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.me.xdf.common.file.FileUtil;
import cn.me.xdf.common.hibernate4.Finder;
import cn.me.xdf.model.base.AttMain;
import cn.me.xdf.service.base.AttMainService;

public class DiskFileClearQuartz implements Serializable {
	
	private static final Logger log= LoggerFactory.getLogger(DiskFileClearQuartz.class);
	
	@Autowired
	private AttMainService attMainService;
	
	public void executeTodo() {
		//只删除视频,删除的视频必须是成功上传到filenet及cc的
		log.info("开始执行定时任务:清除磁盘上的视频");
		Finder finder = Finder.create("from AttMain t ");
		finder.append("where t.fdFileType='01' ");
		finder.append("and t.playCode is not null and t.fileNetId is not null ");
		finder.append("and to_char(t.fdCreateTime,'yyyymm') = to_char(add_months(sysdate,-3),'yyyymm') ");
		List<AttMain> attMainList = attMainService.find(finder);
		for (AttMain attMain : attMainList) {
			String filePath = attMain.getFdFilePath();
		    FileUtil.delete(filePath);
	    }
		log.info("结束执行定时任务:清除磁盘上的视频");
	}

}
