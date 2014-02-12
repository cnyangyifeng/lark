package cn.me.xdf.ldap;

import cn.me.xdf.model.organization.SysOrgConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.ldap.core.DirContextAdapter;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-12-12
 * Time: 下午12:40
 * To change this template use File | Settings | File Templates.
 */
public class LdapUtils {

    public static String getDn(DirContextAdapter context) {
        Enumeration<String> dns = context.getDn().getAll();
        StringBuilder sb = new StringBuilder();
        while (dns.hasMoreElements()) {
            sb.append(dns.nextElement()).append(",");
        }
        return StringUtils.removeEnd(sb.toString(), ",");
    }


    public static void setStringAttribute(DirContextAdapter context, Map<String, Object> map, String key, String name) {
        String v = context.getStringAttribute(name);
        if (v != null)
            map.put(key, v);
        else
            map.put(key, "");
    }

    /**
     * 只针对机构和部门来判断
     *
     * @param orgNo
     * @return
     */
    public static int getOrgType(String orgNo) {
        if (map.containsKey(orgNo)) {
            return SysOrgConstant.ORG_TYPE_ORG;
        } else {
            return SysOrgConstant.ORG_TYPE_DEPT;
        }
    }

    private static Map<String, String> map = new HashMap<String, String>();

    /**
     * 初始化机构编码
     */
    static {

        map.put("105", "105");
        map.put("100", "100");
        map.put("99", "99");
        map.put("106", "106");
        map.put("107", "107");
        map.put("108", "108");
        map.put("109", "109");
        map.put("110", "110");
        map.put("111", "111");
        map.put("112", "112");
        map.put("113", "113");
        map.put("114", "114");
        map.put("115", "115");
        map.put("116", "116");
        map.put("117", "117");
        map.put("118", "118");
        map.put("119", "119");
        map.put("120", "120");
        map.put("121", "121");
        map.put("122", "122");
        map.put("123", "123");
        map.put("124", "124");
        map.put("126", "126");
        map.put("127", "127");
        map.put("128", "128");
        map.put("129", "129");
        map.put("131", "131");
        map.put("130", "130");
        map.put("135", "135");
        map.put("136", "136");
        map.put("138", "138");
        map.put("140", "140");
        map.put("172", "172");
        map.put("176", "176");
        map.put("179", "179");
        map.put("182", "182");
        map.put("183", "183");
        map.put("200", "200");
        map.put("213", "213");
        map.put("221", "221");
        map.put("224", "224");
        map.put("225", "225");
        map.put("226", "226");
        map.put("227", "227");
        map.put("228", "228");
        map.put("229", "229");
        map.put("230", "230");
        map.put("238", "238");
        map.put("142", "142");
        map.put("150", "150");
        map.put("151", "151");
        map.put("152", "152");
        map.put("153", "153");
        map.put("154", "154");
        map.put("155", "155");
        map.put("156", "156");
        map.put("157", "157");
        map.put("158", "158");
        map.put("159", "159");
        map.put("160", "160");
        map.put("161", "161");
        map.put("162", "162");
        map.put("163", "163");
        map.put("164", "164");
        map.put("165", "165");
        map.put("166", "166");
        map.put("167", "167");
        map.put("168", "168");
        map.put("169", "169");
        map.put("170", "170");
        map.put("177", "177");
        map.put("186", "186");
        map.put("187", "187");
        map.put("201", "201");
        map.put("208", "208");
        map.put("215", "215");
        map.put("143", "143");
        map.put("144", "144");
        map.put("145", "145");
        map.put("188", "188");
        map.put("189", "189");
        map.put("190", "190");
        map.put("191", "191");
        map.put("192", "192");
        map.put("193", "193");
        map.put("194", "194");
        map.put("195", "195");
        map.put("196", "196");
        map.put("197", "197");
        map.put("198", "198");
        map.put("199", "199");
        map.put("202", "202");
        map.put("203", "203");
        map.put("204", "204");
        map.put("205", "205");
        map.put("206", "206");
        map.put("207", "207");
        map.put("209", "209");
        map.put("210", "210");
        map.put("211", "211");
        map.put("212", "212");
        map.put("222", "222");
        map.put("232", "232");
        map.put("233", "233");
        map.put("234", "234");
        map.put("235", "235");
        map.put("148", "148");
        map.put("149", "149");
        map.put("185", "185");
        map.put("236", "236");
        map.put("237", "237");
        map.put("241", "241");
    }
}
