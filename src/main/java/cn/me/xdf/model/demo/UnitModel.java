package cn.me.xdf.model.demo;

import cn.me.xdf.annotaion.AttMainMachine;
import cn.me.xdf.annotaion.AttValues;
import cn.me.xdf.model.base.AttMain;
import cn.me.xdf.model.base.IAttMain;
import cn.me.xdf.model.base.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-10-23
 * Time: 上午9:52
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "IXDF_NTP_DEMO_UNIT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@AttMainMachine(value = {@AttValues(fild="attIds")},modelName = "cn.me.xdf.model.demo.UnitModel")
public class UnitModel extends IdEntity implements IAttMain{

    private String fdName;

    private String fdContext;


    public String getFdName() {
        return fdName;
    }

    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    public String getFdContext() {
        return fdContext;
    }

    public void setFdContext(String fdContext) {
        this.fdContext = fdContext;
    }

    private List<AttMain> attIds;

    @Transient
    public List<AttMain> getAttIds() {
        return attIds;
    }

    public void setAttIds(List<AttMain> attIds) {
        this.attIds = attIds;
    }
}
