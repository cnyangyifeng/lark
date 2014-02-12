package cn.me.xdf.model.organization;

import cn.me.xdf.model.base.IdEntity;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-12-12
 * Time: 上午10:24
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "SYS_ORG_ELEMENT")
@Entity
@Inheritance(strategy= InheritanceType.JOINED)
public class SysOrgElement extends IdEntity implements SysOrgConstant{

    /**
     * 组织结构名称
     */
    private String fdName;

    /**
     * 组织结构编码
     */
    private String fdNo;

    /**
     * 组织结构类型
     */
    private Integer fdOrgType;

    /**
     * 上级
     */
    private SysOrgElement hbmParent;


    /*
	 * 子（此关系由父维护，不提供set方法）
	 */
    private List<SysOrgElement> hbmChildren;


    /**
     * 是否有效
     */
    private Boolean isAvailable;

    /**
     * LDAP导入时使用的字段
     */
    private String ldapDN;

    /**
     * 创建时间
     */
    private Date createTime;


    @Column(name = "FD_NAME",length = 500)
    public String getFdName() {
        return fdName;
    }

    public void setFdName(String fdName) {
        this.fdName = fdName;
    }


    @Column(name = "FD_NO")
    public String getFdNo() {
        return fdNo;
    }

    public void setFdNo(String fdNo) {
        this.fdNo = fdNo;
    }
    /**
     * 类型
     *
     * @return
     */
    @Column(name = "FD_ORG_TYPE")
    public Integer getFdOrgType() {
        return fdOrgType;
    }

    /**
     *
     *
     * @param fdOrgType
     */
    public void setFdOrgType(Integer fdOrgType) {
        this.fdOrgType = fdOrgType;
    }


    /**
     * 获取上级
     *
     * @return
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FD_PARENTID")
    public SysOrgElement getHbmParent() {
        return hbmParent;
    }

    /**
     * 上级
     *
     * @param parent
     */
    public void setHbmParent(SysOrgElement parent) {
        this.hbmParent = parent;
    }


    /**
     * 获取下级
     *
     * @return
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hbmParent")
    public List<SysOrgElement> getHbmChildren() {
        return hbmChildren;
    }

    /**
     * 下级
     *
     * @param children
     */
    public void setHbmChildren(List<SysOrgElement> children) {
        this.hbmChildren = children;
    }

    @org.hibernate.annotations.Type(type="yes_no")
    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    @Column(length = 2000)
    public String getLdapDN() {
        return ldapDN;
    }

    public void setLdapDN(String ldapDN) {
        this.ldapDN = ldapDN;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }



    /**
     * 获取上级编码
     *
     * @return
     */
    @Transient
    public String getFdParentId() {
        if (hbmParent == null)
            return null;
        return hbmParent.getFdId();
    }


    /**
     * 获取上级的机构
     *
     * @return
     */
    @Transient
    public SysOrgElement getHbmParentOrg() {
        if (getFdOrgType().intValue() == 1)
            return null;
        SysOrgElement element = getHbmParent();
        while (element != null && element.getFdOrgType().intValue() > 1) {
            element = element.getHbmParent();
        }
        return element;
    }


    @Transient
    public List<SysOrgElement> getFdChildren() {
        List<SysOrgElement> rtnVal = new ArrayList<SysOrgElement>();
        if (getHbmChildren() != null)
            rtnVal.addAll(getHbmChildren());
        return rtnVal;
    }

    @Transient
    public List<SysOrgElement> getFdAllChildren() {
        List<SysOrgElement> rtnVal = new ArrayList<SysOrgElement>();
        if (getHbmChildren() != null) {
            List<SysOrgElement> elements = getHbmChildren();
            for (SysOrgElement e : elements) {
                List<SysOrgElement> e2 = e.getHbmChildren();
                if (e2 != null) {
                    for (SysOrgElement e3 : e2) {
                        if (e3 != null) {
                            List<SysOrgElement> e4 = e3.getFdChildren();
                            if (e4 != null) {
                                rtnVal.addAll(e4);
                            }
                        }
                    }
                    rtnVal.addAll(e2);
                }
            }
            rtnVal.addAll(elements);
        }
        return rtnVal;
    }
}
