package cn.me.xdf.model.system;

import cn.me.xdf.model.base.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-12-17
 * Time: 下午1:19
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("serial")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "SYS_APP_CONFIG")
public class SysAppConfig extends IdEntity {

    public static final String FD_KEY = "cn.me.xdf.model.system.SysAppConfig";

    private String fdKey;


    private String fdParam;

    private String fdValue;


    public String getFdKey() {
        return fdKey;
    }

    public void setFdKey(String fdKey) {
        this.fdKey = fdKey;
    }

    public String getFdParam() {
        return fdParam;
    }

    public void setFdParam(String fdParam) {
        this.fdParam = fdParam;
    }

    public String getFdValue() {
        return fdValue;
    }

    public void setFdValue(String fdValue) {
        this.fdValue = fdValue;
    }
}
