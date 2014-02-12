package cn.me.xdf.ldap.service;

import cn.me.xdf.service.SimpleService;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-12-12
 * Time: 下午4:46
 * To change this template use File | Settings | File Templates.
 */
public abstract class LdapInService extends SimpleService {


    public abstract void initData();

    public abstract String executeUpdateData(int day);





}
