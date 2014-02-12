package cn.me.xdf.service.bam.process;

import cn.me.xdf.common.hibernate4.Finder;
import cn.me.xdf.model.base.Constant;
import cn.me.xdf.model.material.MaterialInfo;
import cn.me.xdf.model.process.SourceNote;
import cn.me.xdf.service.SimpleService;
import cn.me.xdf.utils.ShiroUtils;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-11-6
 * Time: 下午2:46
 * 素材学习记录
 */
@Service
@Transactional(readOnly = true)
public class SourceNodeService extends SimpleService {

    @Transactional(readOnly = false)
    public Object saveSourceNode(SourceNote sourceNote) {
        return this.save(sourceNote);
    }
    
    /**
     * 获取最新的学习记录
     * @param minfo 素材信息
     * @param catalogId   节ID
     * @param userId 用户ID
     * @return SourceNote 最新学习记录
     */
    @Transactional(readOnly = true)
    public SourceNote getSourceNote(String materialId,String catalogId,String userId) {
    	Finder finder = Finder
				.create("from SourceNote sourceNote ");
		finder.append("where sourceNote.fdCatalogId=:fdCatalogId and sourceNote.fdMaterialId=:fdMaterialId and fdUserId=:fdUserId ");
		finder.append("order by sourceNote.fdOperationDate desc");
		finder.setParam("fdCatalogId", catalogId);
		finder.setParam("fdMaterialId", materialId);
		finder.setParam("fdUserId", userId);
		if(getPage(finder, 1, 1).getList().size()==0){
			return null;
		}else{
			return (SourceNote) getPage(finder, 1, 1).getList().get(0);
		}
		
    }
    
    /**
     * 获取素材通过状态
     * @param minfo 素材信息
     * @param catalogId   节ID
     * @param userId 用户ID
     * @return String 状态（unfinish：待答；finish：答完；fail：未通过；pass：通过）
     */
    public String getStatus(MaterialInfo minfo,String catalogId,String userId){
		if(minfo.getThrough()){
			return "pass";
		}else{
			SourceNote node = this.getSourceNote(minfo.getFdId(), catalogId, userId);
			if(node==null){
				return "unfinish";
			}
			if(node.getFdStatus()!=null){
				if(node.getFdStatus().equals(Constant.TASK_STATUS_FINISH)){//完成
					return "finish";
				} else if(node.getFdStatus().equals(Constant.TASK_STATUS_REJECT)){//驳回
					return "unfinish";
				} else if(node.getFdStatus().equals(Constant.TASK_STATUS_UNFINISH)){//未完成
					return "unfinish";
				} 
				/*Boolean iStudy=node.getIsStudy();
				if(iStudy==null){
					return "finish";
				}*/
			}
		}
		return "fail";
	}
    
}
