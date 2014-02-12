package cn.me.xdf.ldap.service;

import cn.me.xdf.model.log.LdapLog;
import cn.me.xdf.service.SimpleService;
import cn.me.xdf.utils.ShiroUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-12-12
 * Time: 下午5:56
 * To change this template use File | Settings | File Templates.
 */
@Service
public class LdapLogService extends SimpleService {

    public void saveLog(String msg) {
        LdapLog log = new LdapLog();
        log.setCreateTime(new Date());
        log.setMsg(msg);
        if (ShiroUtils.getSubject() != null) {
            log.setUserName(ShiroUtils.getUser().getName());
        }
        super.save(log);
    }
}
