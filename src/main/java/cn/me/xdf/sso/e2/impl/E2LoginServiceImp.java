package cn.me.xdf.sso.e2.impl;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.http.Cookie;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.me.xdf.common.json.JsonUtils;
import cn.me.xdf.sso.ILoginService;
import cn.me.xdf.sso.e2.constant.SSOToE2Constant;
import cn.me.xdf.sso.e2.util.AESX3;
import cn.me.xdf.sso.e2.util.IPUtil;
import cn.me.xdf.sso.e2.vo.OnlineVM;

/**
 * E2认证实现
 * 
 * @author xiaobin
 * 
 */
public class E2LoginServiceImp implements ILoginService {

	private static final Logger log = LoggerFactory
			.getLogger(E2LoginServiceImp.class);

	@Override
	public boolean login(String _username, String _password) throws Exception {
		HttpClient client = new HttpClient();
		
		HttpMethod httpPost = getPostMethod(_username, _password);
		String result = null;
		try {
			client.getParams().setParameter("http.protocol.content-charset",
					"UTF-8");
			int status = client.executeMethod(httpPost);
			if (status == 200) {
				log.info("***print server status==" + httpPost.getStatusLine());
				result = httpPost.getResponseBodyAsString();
				log.info("***print result info==" + result);
			} else {
				log.info("页面请求返回值异常：" + status);
			}

			if (StringUtils.isBlank(result))
				log.info("no result....");
			String[] resultList = result.split("$");
			String resultCode = resultList[0];
			String resultMsg = resultList.length > 1 ? resultList[1]
					: resultCode;
			// 中心返回的E2Token顶级cookie值
			String e2TokenCookieValue = resultList.length > 2 ? resultList[2]
					: null;
			log.info("resultCode==" + resultCode);
			log.info("resultMsg===" + resultMsg);
			System.out.println("resultMsg==="+resultMsg);
			if (resultCode.startsWith("1")) {
				// 第1步：设置中心返回的顶级cookie值，默认有效期24小时
				System.out.println("e2TokenCookieValue==="+e2TokenCookieValue);
				if (!StringUtils.isBlank(e2TokenCookieValue)) {
					Calendar c1 = Calendar.getInstance();
					c1.add(Calendar.SECOND, ssoToE2Constant.getMaxAge());
					org.apache.commons.httpclient.Cookie cookie = new org.apache.commons.httpclient.Cookie(ssoToE2Constant.getE2TopDomain(),
							ssoToE2Constant.getCookieName(), e2TokenCookieValue,"/",c1.getTime(),true);
					client.getState().addCookie(cookie);
				}
				return true;
			} else {
				log.info(resultMsg);
			}
		} catch (HttpException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			httpPost.releaseConnection();
		}
		return false;
	}

	/**
	 * 使用POST方式提交数据
	 * 
	 * @param _username
	 * @param _password
	 * @return
	 * @throws Exception
	 */
	private HttpMethod getPostMethod(String _username, String _password)
			throws Exception {
		String url = ssoToE2Constant.getE2RootUrl() + "apis/users.ashx";
		PostMethod post = new PostMethod(url);
		String method = "CheckLogin"; // 方法名称，固定值
		// String SSOToE2Constant.Appid = "202"; // 得到本应用SSOToE2Constant.Appid
		String email = _username + "@xdf.cn";
		String password = _password;
		String ip = IPUtil.getRealIp();

		// 对象赋值
		OnlineVM jsonVM = new OnlineVM();
		jsonVM.setEmail(email);
		jsonVM.setPwd(password);
		jsonVM.setIp(ip);

		// json对象加密
		String encodeJson = AESX3.aesEncode(
				JsonUtils.writeObjectToJson(jsonVM),
				ssoToE2Constant.getE2AppEn32Key());
		// //直接将所有参数值拼接，并加上key，最后全转为小写
		String signText = (method + ssoToE2Constant.getAppId() + encodeJson + ssoToE2Constant
				.getE2AppKey()).toLowerCase();
		String sign = AESX3.md5(signText); // 签名
		// 属性键值
		NameValuePair nv1 = new NameValuePair("method", method);
		NameValuePair nv2 = new NameValuePair("appid",
				ssoToE2Constant.getAppId());
		NameValuePair nv3 = new NameValuePair("json", encodeJson);
		NameValuePair nv4 = new NameValuePair("sign", sign);

		post.addParameters(new NameValuePair[] { nv1, nv2, nv3, nv4 });
		return post;
	}

	private SSOToE2Constant ssoToE2Constant;

	public SSOToE2Constant getSsoToE2Constant() {
		return ssoToE2Constant;
	}

	public void setSsoToE2Constant(SSOToE2Constant ssoToE2Constant) {
		this.ssoToE2Constant = ssoToE2Constant;
	}
}
