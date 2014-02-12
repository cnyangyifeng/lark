package cn.me.xdf.workflow.model;

import cn.me.xdf.model.base.BamProcess;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-10-25
 * Time: 下午1:35
 * To change this template use File | Settings | File Templates.
 */
public class BamPackage implements BamProcess{


    @Override
    public String getFdId() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean getThrough() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setThrough(boolean through) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
