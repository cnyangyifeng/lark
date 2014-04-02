package com.kuxue.service.system;

import com.kuxue.common.hibernate4.Value;
import com.kuxue.model.system.SysAppConfig;
import com.kuxue.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-12-17
 * Time: 下午1:21
 * To change this template use File | Settings | File Templates.
 */
@Service
public class SysAppConfigService extends BaseService {

    public SysAppConfig findByKeyAndParam(String key, String param) {
        List<SysAppConfig> lists = findByCriteria(SysAppConfig.class, Value.eq("fdKey", key), Value.eq("fdParam", param));
        if (!lists.isEmpty()) {
            return lists.get(0);
        }
        return null;
    }

    @Override
    public Class<SysAppConfig> getEntityClass() {
        return SysAppConfig.class;
    }
}
