package cn.me.xdf.model.base;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-10-25
 * Time: 下午1:29
 *
 * 流程实例需要实现此接口。
 *
 * 1、资源进度实例
 * 2、节进度实例
 * 3、章进度实例
 * 4、课程进度实例
 *
 *
 *
 *
 **/
public interface BamProcess {

    public static final String THROGHT_FIELD = "through";

    public static final String PRI_FIELD = "fdId";

    public static final String PRI_THROUGH = "through";

    public boolean getThrough();

    public void setThrough(boolean through);
    public String getFdId();

}