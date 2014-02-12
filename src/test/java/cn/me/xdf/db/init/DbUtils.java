package cn.me.xdf.db.init;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DbUtils {

    private static String driver;
    private static String url;
    private static String dbUser;
    private static String dbPwd;

    public static void getParam() {
        driver = "oracle.jdbc.driver.OracleDriver";
        url = "jdbc:oracle:thin:@10.200.130.15:1521:SABAKMDB";
        dbUser = "db_otp";
        dbPwd = "p_1qaz#$";
    }

    private static int size = 0;
    private static ThreadLocal<Connection> thread = new ThreadLocal<Connection>();

    /**
     * 根据四个全局变量参数构造连接对象并返回
     *
     * @return Connection对象
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        if (size++ > 500) {
            Connection conn = thread.get();
            if (conn != null) {
                conn.close();
                conn = null;
                thread.set(null);
            }
            size = 0;
        }
        if (thread.get() == null) {
            Connection conn = null;
            try {
                getParam();
                Class.forName(driver);
                conn = DriverManager.getConnection(url, dbUser, dbPwd);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return conn;
        }
        return thread.get();
    }

    public static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
