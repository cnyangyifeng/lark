package cn.me.xdf.ldap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.Name;
import javax.naming.directory.SearchControls;

import cn.me.xdf.common.page.Pagination;
import cn.me.xdf.ldap.model.BaseModel;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ldap.control.PagedResultsCookie;
import org.springframework.ldap.control.PagedResultsDirContextProcessor;
import org.springframework.ldap.core.*;
import org.springframework.ldap.core.simple.ParameterizedContextMapper;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.WhitespaceWildcardsFilter;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-12-13
 * Time: 下午2:01
 * To change this template use File | Settings | File Templates.
 */
public class BaseLdapDao<T extends BaseModel> {

    @Autowired
    private LdapTemplate ldapTemplate;

    /**
     * 根据唯一DN进行查找
     * @param t
     * @return
     * @创建人 PengBo
     * @创建时间 2013-7-7  下午6:55:02
     */
    public T findByPrimaryKey(final T t) {
        return (T) ldapTemplate.lookup(buildDn(t),  getContextMapper(t));
    }

    /**
     * 根据dn直接获取实体信息
     * @param t
     * @param dn
     * @return
     * @创建人 PengBo
     * @创建时间 2013-7-29  下午4:39:59
     */
    public T findByDN(final T t,String dn) {
        return (T) ldapTemplate.lookup(dn,  getContextMapper(t));
    }
    /**
     * 根据实体条件精确查找进行查找
     * @param t
     * @return  返回查找的集合
     * @创建人 PengBo
     * @创建时间 2013-7-7  下午6:55:38
     */
    public List<T> findByEqualFilter( final T t) {
        return   ldapTemplate.search(buildDn(t), getEqualFilter(t).encode(), getContextMapper(t));

    }

    /**
     * 根据实体条件进行查找
     * @param t
     * @return  返回查找的集合
     * @创建人 PengBo
     * @创建时间 2013-7-7  下午6:55:38
     */
    public List<T> findByFilter( final T t) {
        return   ldapTemplate.search(buildDn(t), getFilter(t).encode(), getContextMapper(t));

    }

    /**
     * 根据实体类型查找所有实体
     * @param t
     * @return  返回实体集合
     * @创建人 PengBo
     * @创建时间 2013-7-7  下午6:56:13
     */
    public List<T> findAll(final T t) {
        return   ldapTemplate.search(buildDn(t),  getObjectclass(t).encode(), getContextMapper(t));

    }
    /**
     * 根据实体的分页属性进行查获早分页信息
     * @return
     * @创建人 PengBo
     * @创建时间 2013-7-7  下午6:57:00
     */
    public Pagination getPages(int page,int total,T t){
        Pagination p = new Pagination();
        p.setPageSize(total);
        int totalRow = getTotalCount(t);
        p.setList(getAllPageMap(null, t, (p.getPageSize() * page)));
        p.setTotalCount(totalRow);
        //p((totalRow+basePage.getPageSize()-1)/basePage.getPageSize());
        return p;
    }
    /**
     * 根据传入记录数查处所需要的信息
     * @param cookie
     * @param t
     * @param pageSize
     * @return
     * @创建人 PengBo
     * @创建时间 2013-7-10  上午9:23:46
     */
    private List<T> getAllPageMap(PagedResultsCookie cookie,T t,Integer pageSize) {
        PagedResultsDirContextProcessor  control = new PagedResultsDirContextProcessor (pageSize, cookie);
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        List<T> mList = ldapTemplate.search(buildDn(t),  getFilter(t).encode(),searchControls, getContextMapper(t), control);
        return mList;
    }

    /**
     * 查找全部信息所需要的filter检索方法
     * @return
     * @创建人 PengBo
     * @创建时间 2013-7-7  下午6:59:38
     */
    private AndFilter getObjectclass(T t){
        Map<String, String> map=t.getMap();
        AndFilter filter = new AndFilter();
        for(String mkey:map.keySet()){      //根据实体只获得他对应的objectclass的值
            if ("objectclass".equals(mkey)) {
                filter.and(new EqualsFilter(mkey, (String) map.get(mkey)));
            }
        }
        return filter;
    }

    /**
     * 根据条件模糊查找的条件方法
     * @param t
     * @return
     * @创建人 PengBo
     * @创建时间 2013-7-7  下午7:00:10
     */
    private AndFilter getFilter(T t) {
        AndFilter filter = new AndFilter();
        Map<String, String> map=t.getMap();
        for(String mkey:map.keySet()){
            if ("objectclass".equals(mkey)) {
                filter.and(new EqualsFilter(mkey, (String) map.get(mkey)));
            }
            if(StringUtils.isNotBlank(map.get(mkey)) && !"objectclass".equals(mkey))
                filter.and(new WhitespaceWildcardsFilter(mkey, (String) map.get(mkey)));
        }
        return filter;
    }
    /**
     * 根据条件精确查找的条件方法
     * @param t
     * @return
     * @创建人 PengBo
     * @创建时间 2013-7-8  下午3:10:43
     */
    private AndFilter getEqualFilter(T t) {
        AndFilter filter = new AndFilter();
        Map<String, String> map=t.getMap();
        for(String mkey:map.keySet()){
            if ("objectclass".equals(mkey)) {
                filter.and(new EqualsFilter(mkey, (String) map.get(mkey)));
            }
            if(StringUtils.isNotBlank(map.get(mkey)) && !"objectclass".equals(mkey))
                filter.and(new EqualsFilter(mkey, (String) map.get(mkey)));
        }
        return filter;
    }
    /**
     * 构造查询实体UUID方法
     * @param t
     * @return
     * @创建人 PengBo
     * @创建时间 2013-7-7  下午7:00:49
     */
    private Name buildDn(T t) {
        String a = t.getDN();
        DistinguishedName dn = new DistinguishedName(a);
        return dn;
    }

    /**
     * 构造查找组织的dn
     * @param t
     * @return
     * @创建人 PengBo
     * @创建时间 2013-7-16  上午9:45:57
     */
    public String findDn(T t) {
        Name dn= buildDn( t);
        return dn.toString();
    }
    /**
     * 查询获得实体属性构造器
     * @param t
     * @return
     * @创建人 PengBo
     * @创建时间 2013-7-7  下午7:01:12
     */
    private ContextMapper getContextMapper(final T t) {
        return new ParameterizedContextMapper<T>() {
            @Override
            public T mapFromContext(Object ctx) {
                DirContextAdapter adapter = (DirContextAdapter) ctx;
                T newT=null;
                try {
                    newT = (T) t.getClass().newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                Map<String, String> map= t.getMap();
                Map<String, String> smap=new HashMap<String, String>();
                for(String mkey:map.keySet()){
                    if (!"objectclass".equals(mkey)) {
                        if(!"userPassword".equals(mkey)){
                            if (StringUtils.isNotBlank(adapter.getStringAttribute(mkey))) {
                                smap.put(mkey, adapter.getStringAttribute(mkey));
                            }else {
                                smap.put(mkey, null);
                            }
                        }
                    }
                }
                newT.setMap(smap);
                return newT;
            }
        };
    }


    /**
     * 查询表的行数
     */
    private int getTotalCount(T t) {
        long starTime = System.currentTimeMillis();

        // 条目列表
        List list = null;
        // 设置filter
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        searchControls.setReturningAttributes(null);
        CollectingNameClassPairCallbackHandler handler
                = new LdapTemplate.MappingCollectingNameClassPairCallbackHandler(new DefaultNameClassPairMapper());
        PagedResultsDirContextProcessor requestControl
                = new PagedResultsDirContextProcessor(300000);
        try {
            // 只获取第一页
            ldapTemplate.search(buildDn(t),  getObjectclass(t).encode(), searchControls, handler,
                    requestControl);
            list = handler.getList();
            long endTime = System.currentTimeMillis();
            System.out.println("获得记录数所用时间:" + (endTime - starTime));
        } catch (Exception e) {
            System.out.println("分页查询异常1" + e);
        }

        return list.size();
    }
}
