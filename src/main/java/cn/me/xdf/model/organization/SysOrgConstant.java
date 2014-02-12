package cn.me.xdf.model.organization;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-12-12
 * Time: 上午10:42
 * To change this template use File | Settings | File Templates.
 */
public interface SysOrgConstant {

    /**
     * 机构
     */
    public final static int ORG_TYPE_ORG = 0x1;

    /**
     * 部门
     */
    public final static int ORG_TYPE_DEPT = 0x2;

    /**
     * 岗位
     */
    public final static int ORG_TYPE_POST = 0x4;

    /**
     * 个人
     */
    public final static int ORG_TYPE_PERSON = 0x8;

    /**
     * 群组
     */
    public final static int ORG_TYPE_GROUP = 0x10;
}
