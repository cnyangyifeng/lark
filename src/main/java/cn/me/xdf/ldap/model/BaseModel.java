package cn.me.xdf.ldap.model;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-12-13
 * Time: 下午2:03
 * To change this template use File | Settings | File Templates.
 */
public class BaseModel {

    /** 映射实体与LDAP属性*/
    private Map<String, String> map;

    /** 映射实体与LDAPDN*/
    public String getDN() {
        return null;
    }

    public Map<String, String> getMap() {
        return map;
    }
    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
