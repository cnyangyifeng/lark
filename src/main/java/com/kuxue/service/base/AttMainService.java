package com.kuxue.service.base;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kuxue.api.bokecc.config.Config;
import com.kuxue.api.bokecc.util.APIServiceFunction;
import com.kuxue.api.bokecc.util.DemoUtil;
import com.kuxue.common.utils.MyBeanUtils;
import com.kuxue.service.plugin.AttMainPlugin;
import com.kuxue.task.AttMainTask;
import org.apache.commons.collections.CollectionUtils;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kuxue.common.file.FileUtil;
import com.kuxue.common.hibernate4.Value;
import com.kuxue.model.base.AttMain;
import com.kuxue.service.SimpleService;

/**
 *
 */
@Service
@Transactional(readOnly = true)
public class AttMainService extends SimpleService {

    @Autowired
    private TaskExecutor taskExecutor;


    public AttMain get(String id) {
        return get(AttMain.class, id);
    }

    /**
     * 保存附件
     *
     * @param attMain
     * @return
     */
    @Transactional(readOnly = false)
    public AttMain save(AttMain attMain) {
        return super.save(attMain);
        //return get("142c0f7aa5b3faa6efca6da479cb0c54");//图片
        //return get("142bce9c6e47bc8a2ead7054ef49a26c");//文档
        //return get("142bbbca857f4b136ef702041b59781a");//视频
    }

    @Transactional(readOnly = false)
    public AttMain meger(AttMain attMain) {
        return super.save(attMain);
    }

    @Transactional(readOnly = false)
    public void updateConvertThrough(String playCode) {
        List<AttMain> attMains = findByCriteria(AttMain.class, Value.eq("playCode", playCode));
        for (AttMain attMain : attMains) {
            attMain.setFlag(1);
            covertVideoToFlag(attMain);
            update(attMain);
        }
    }

    private void covertVideoToFlag(AttMain attMain) {
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("userid", Config.userid);
        try {
            paramsMap.put("videoid", attMain.getPlayCode());
            paramsMap.put("userid", Config.userid);
            //String title = attMain.getFdFileName();
            String tag = "NTP";
            String description = attMain.getFdFileName();
            //paramsMap.put("title", title);
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
                update(attMain);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Transactional(readOnly = false)
    public AttMain saveOnInit(AttMain attMain) {
        return super.save(attMain);
    }

    public List<AttMain> getByModeslIdAndModelNameAndKey(String modelId,
                                                         String modelName, String key) {
        List<AttMain> attMains = findByCriteria(AttMain.class,
                Value.eq("fdModelId", modelId),
                Value.eq("fdModelName", modelName), Value.eq("fdKey", key));
        if (CollectionUtils.isNotEmpty(attMains)) {
            return attMains;
        }
        return null;
    }

    //OK
    public void executeInterfaceDelete(final AttMain attMain){
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                AttMainPlugin.deleteDoc(attMain);
                if ("01".equals(attMain.getFdFileType())) {
                    AttMainPlugin.DeleteDocToCC(attMain);
                }
            }
        });
    }

    @Transactional(readOnly = false)
    public AttMain deleteAttMain(String id) {
        AttMain attMain = get(id);
        AttMain attMainCopy = new AttMain();
        MyBeanUtils.copyProperties(attMain,attMainCopy);
        String file = attMain.getFdFilePath();
        delete(AttMain.class, id);
        FileUtil.delete(file);
        executeInterfaceDelete(attMainCopy);
        return attMainCopy;
    }

    @Transactional(readOnly = false)
    public void delete(Class<AttMain> clazz, String id) {
        getBaseDao().deleteById(clazz, id);
    }


    @Transactional(readOnly = false)
    public void deleteAttMainByModelId(String modelId) {
        List<AttMain> attMains = getAttsByModelId(modelId);
        for (AttMain attMain : attMains) {
            deleteAttMain(attMain.getFdId());
        }
    }
    
    @Transactional(readOnly = false)
    public void deleteAttMainByModelIdExpAttId(String modelId,List<String> attIds) {
        List<AttMain> attMains = getAttsByModelId(modelId);
        for (AttMain attMain : attMains) {
        	if(!attIds.contains(attMain.getFdId())){
        		deleteAttMain(attMain.getFdId());
        	}
        }
    }

    @Transactional(readOnly = false)
    public void deleteAttMainByIds(String[] ids) {
        for (String id : ids) {
            deleteAttMain(id);
        }
    }

    @Transactional(readOnly = false)
    public List<AttMain> getAttMainsByModelIdAndModelName(String modelId, String modelName) {
        return findByCriteria(AttMain.class, Value.eq("fdModelId", modelId), Value.eq("fdModelName", modelName));
    }

    /**
     * 根据模型ID查询上传附件的信息
     *
     * @param modelId
     * @return
     */
    public AttMain getByModelId(String modelId) {
        List<AttMain> attMains = findByCriteria(AttMain.class,
                Value.eq("fdModelId", modelId));
        if (CollectionUtils.isNotEmpty(attMains)) {
            return attMains.get(0);
        }
        return null;
    }


    /**
     * 根据模型ID查询上传附件的信息
     *
     * @param modelId
     * @return
     */
    public List<AttMain> getAttsByModelId(String modelId) {
        return findByCriteria(AttMain.class,
                Value.eq("fdModelId", modelId));
    }

    public List<AttMain> getAttsByIds(String[] ids) {
        return findByCriteria(AttMain.class, Value.in("fdId", ids));
    }


    /**
     * 根据模型ID和模型名称查询上传附件的信息
     *
     * @param modelId
     * @param modelName
     * @return
     */
    public AttMain getByModelIdAndModelName(String modelId, String modelName) {
        List<AttMain> attMains = findByCriteria(AttMain.class,
                Value.eq("fdModelId", modelId),
                Value.eq("fdModelName", modelName));
        if (CollectionUtils.isNotEmpty(attMains)) {
            return attMains.get(0);
        }
        return null;
    }


}
