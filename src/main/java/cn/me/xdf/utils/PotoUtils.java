package cn.me.xdf.utils;

import cn.me.xdf.sso.e2.util.AESX3;
import org.apache.commons.lang3.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-12-13
 * Time: 上午9:46
 * To change this template use File | Settings | File Templates.
 */
public class PotoUtils {


    public static String getPhoto(String _loginName) throws Exception {
        String url = getURLByLoginName(_loginName);
        if (StringUtils.isNotBlank(url) && url.indexOf('?') <= 0) {
            url += "?cache=no";
        }
        return url;
    }


    // 调用得到人员头像URL
    private static String getURLByLoginName(String loginName) throws Exception {
        final String _password = "123.com!@#!@#";
        // 一共3种_original, _129129, _9494
        final String _type = "_129129";
        String _key = loginName + _password;
        String _enKey = AESX3.md5(_key);

        StringBuffer _url = new StringBuffer

                ("http://img.staff.xdf.cn/Photo/");

        if (StringUtils.isBlank(_enKey))
            return null;

        String _hash12 = _enKey.substring(_enKey.length() - 2,

                _enKey.length());
        String _hash34 = _enKey.substring(_enKey.length() - 4,
                _enKey.length() - 2);

        _url.append(_hash12 + "/");
        _url.append(_hash34 + "/");
        _url.append(_enKey.toLowerCase());
        _url.append(_type);
        _url.append(".jpg");

        return _url.toString();
    }
}
