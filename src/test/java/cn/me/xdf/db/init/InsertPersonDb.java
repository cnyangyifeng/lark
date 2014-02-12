package cn.me.xdf.db.init;

import cn.me.xdf.BaseTest;
import cn.me.xdf.common.utils.Identities;
import cn.me.xdf.ldap.LdapUtils;
import cn.me.xdf.ldap.service.PersonLdapInService;
import cn.me.xdf.model.organization.SysOrgConstant;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-12-13
 * Time: 下午3:35
 * To change this template use File | Settings | File Templates.
 */
public class InsertPersonDb extends BaseTest {

    @Autowired
    private PersonLdapInService personLdapInService;

    @Test
    public void testInsert() throws SQLException {
        List<Map<String, Object>> lists = builderMap();
       
        personLdapInService.updateOrg(lists);
    }


    private List<Map<String, Object>> builderMap2() throws SQLException {

        List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
        String sql = "SELECT A.FD_NO AS PFDNO,B.FD_NO AS DFDNO FROM SYS_ORG_PERSON A,SYS_ORG_ELEMENT B WHERE a.depatid = B.FDID";
        Connection conn = DbUtils.getConnection();


        Statement stmt = null;

        PreparedStatement pst = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            Map<String, Object> map = null;
            while (rs.next()) {
                map = new ConcurrentHashMap<String, Object>();
                if (rs.getString("PFDNO") == null)
                    continue;
                map.put("PFDNO", rs.getString("PFDNO"));

                map.put("DFDNO", rs.getString("DFDNO") == null ? "" : rs.getString("DFDNO"));
                lists.add(map);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (pst != null) {
                pst.close();
            }
            //DbUtils.close(conn);
        }

        return lists;


    }


    private List<Map<String, Object>> builderMap() throws SQLException {

        List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
        String sql = "SELECT A.*,B.FD_NO AS PARENTID FROM SYS_ORG_PERSON A,SYS_ORG_ELEMENT B WHERE a.depatid = B.FDID AND a.fd_login_name!='admin' ";

        //String sql = "SELECT A.*,'' AS PARENTID FROM SYS_ORG_PERSON A WHERE a.fd_login_name='admin'";

        Connection conn = DbUtils.getConnection();


        Statement stmt = null;

        PreparedStatement pst = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            Map<String, Object> map = null;
            while (rs.next()) {
                map = new ConcurrentHashMap<String, Object>();
                //FD_ID,AVAILABLE,CREATETIME,FD_NAME,FD_NO,FD_ORG_TYPE,LDAPDN,FD_PARENTID

                map.put("FDID", Identities.generateID());
                map.put("FD_LOGIN_NAME", rs.getString("FD_LOGIN_NAME") == null ? "" : rs.getString("FD_LOGIN_NAME"));
                map.put("AVAILABLE", true);
                map.put("CREATETIME", new Date());
                map.put("FD_NAME", rs.getString("REALNAME"));

                map.put("FD_NO", rs.getString("FD_NO") == null ? "" : rs.getString("FD_NO"));
                map.put("FD_NAME", rs.getString("REALNAME") == null ? "" : rs.getString("REALNAME"));
                map.put("LDAPDN", "");
                map.put("FD_EMAIL", rs.getString("FD_EMAIL") == null ? "" : rs.getString("FD_EMAIL"));
                map.put("PARENTID", rs.getString("PARENTID") == null ? "" : rs.getString("PARENTID"));
                map.put("FD_WORK_PHONE", rs.getString("FD_WORK_PHONE") == null ? "" : rs.getString("FD_WORK_PHONE"));
                map.put("FDMOBILENO", rs.getString("FD_MOBILE_NO") == null ? "" : rs.getString("FD_MOBILE_NO"));
                map.put("FD_IDENTITY_CARD", rs.getString("FD_IDENTITY_CARD") == null ? "" : rs.getString("FD_IDENTITY_CARD"));
                map.put("FD_IS_EMP", rs.getString("FD_IS_EMP") == null
                        ? "" : rs.getString("FD_IS_EMP"));
                map.put("FD_SEX", rs.getString("FD_SEX") == null ? "" : rs.getString("FD_SEX"));

                map.put("FD_ORG_TYPE", SysOrgConstant.ORG_TYPE_PERSON);

                map.put("FD_PASSWORD", "c4ca4238a0b923820dcc509a6f75849b");
                lists.add(map);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (pst != null) {
                pst.close();
            }
            //DbUtils.close(conn);
        }

        return lists;


    }


}
