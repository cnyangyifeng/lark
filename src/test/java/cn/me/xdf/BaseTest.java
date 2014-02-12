package cn.me.xdf;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-10-25
 * Time: 下午12:02
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = {
        "/spring/root-context.xml",
        "/spring/applicationContext-shiro.xml",
        "/spring/applicationContext-service.xml",
        "/spring/applicationContext-quartz.xml",
        "/spring/applicationContext-ldap.xml"})
@ActiveProfiles("production")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class BaseTest extends AbstractTransactionalJUnit4SpringContextTests {

    private long starttime;
    private long endtime;

    protected DataSource dataSource;

    @Override
    @Autowired
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
        this.dataSource = dataSource;
    }

    @Before
    public void setup() {
        starttime = System.currentTimeMillis();
    }

    @After
    public void setdown() {
        endtime = System.currentTimeMillis();
        System.out.println("用时:" + (endtime - starttime));
    }
}
