package com.kuxue.model.organization;import javax.persistence.*;/** * <pre> * 	fdOrgType==1:机构 *  * 	fdOrgType==2:部门 * </pre> *  * @author jiaxj 组织架构元素 */@SuppressWarnings("serial")@Entity@Table(name = "SYS_ORG_DEPART")@PrimaryKeyJoinColumn(name = "FDID")public class SysOrgDepart extends SysOrgElement {    public SysOrgDepart() {        super();        setFdOrgType(new Integer(ORG_TYPE_DEPT));    }}