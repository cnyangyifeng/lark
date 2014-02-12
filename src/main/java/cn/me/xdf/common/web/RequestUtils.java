package cn.me.xdf.common.web;


import org.springframework.beans.PropertyValues;
import org.springframework.web.bind.ServletRequestParameterPropertyValues;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebRequestDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;


/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-11-6
 * Time: 下午5:30
 * To change this template use File | Settings | File Templates.
 */
public class RequestUtils {


    /**
     * 通过Request抽取Bean
     * <p/>
     * 例子：
     * View层：
     * <input name="name"/>
     * <p/>
     * 后台：
     * User user = new User();
     * RequestUtils.getBeanByAttr(request, user);
     *
     * @param servletRequest
     * @param entity
     */
    public static void getBeanByAttr(WebRequest servletRequest, Object entity) {
        WebRequestDataBinder dataBinder = new WebRequestDataBinder(entity);
        dataBinder.bind(servletRequest);
    }

    /**
     * 通过Request抽取Bean
     * <p/>
     * 例子：
     * View层：
     * <input name="user.name"/>
     * <p/>
     * 后台：
     * User user = new User();
     * RequestUtils.getBeanByAttr(request, "user", user);
     *
     * @param webRequest
     * @param prefix
     * @param entity
     */
    public static void getBeanByAttr(NativeWebRequest webRequest, String prefix, Object entity) {

        HttpServletRequest servletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        PropertyValues pvs = new ServletRequestParameterPropertyValues(servletRequest,
                prefix, ".");

        WebDataBinder binder = new WebDataBinder(entity, prefix);
        binder.bind(pvs);
    }

}
