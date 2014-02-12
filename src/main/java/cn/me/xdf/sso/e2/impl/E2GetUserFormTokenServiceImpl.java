package cn.me.xdf.sso.e2.impl;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.me.xdf.common.json.JsonUtils;
import cn.me.xdf.sso.IGetUserFromTokenService;
import cn.me.xdf.sso.e2.constant.SSOToE2Constant;
import cn.me.xdf.sso.e2.util.AESX3;
import cn.me.xdf.sso.e2.util.CookieHelper;
import cn.me.xdf.sso.e2.vo.UserModel;
import cn.me.xdf.utils.ComUtils;

/**
 * 根据用户的Cookie获取用户的Token 信息
 * 
 * @author xiaobin
 * 
 */
public class E2GetUserFormTokenServiceImpl implements IGetUserFromTokenService {

	private static final Logger log = LoggerFactory
			.getLogger(E2GetUserFormTokenServiceImpl.class);


	private String getUserByToken(HttpServletRequest request,
			HttpServletResponse response, String cookieValue) throws Exception{
		// cookieValue(shipeng3 登陆) =
		// "0A37D14C62F4763094972ED208711488_B8430060694E59C0BCA8FA94F36D2F8D";
		if (StringUtils.isBlank(cookieValue))
			return null;
		// System.out.println("e2Token:" + cookieValue);
		HttpClient client = new HttpClient();
		HttpMethod httpPost = getPostMethod(cookieValue);
		String result = null;
		String resultMsg = null;
		try {
			client.getParams().setParameter("http.protocol.content-charset",
					"UTF-8");
			int status = client.executeMethod(httpPost);
			if (status == 200) {
				result = httpPost.getResponseBodyAsString();
			} else {
				log.info("页面请求返回值为：" + status);
			}
			if (StringUtils.isBlank(result))
				return null;
			String[] resultList = result.split("\\$");
			String resultCode = resultList[0];
			resultMsg = resultList.length > 1 ? resultList[1] : resultCode;
			// 中心返回的E2Token顶级cookie值
			if (resultCode.equals("1")) {
				// 第1步：设置中心返回的顶级cookie值，默认有效期24小时
				log.info("用户合法");
				return resultMsg;
			} else if (resultCode == "-9") {// -9 当前用户已退出
				// 用户已退出则自动删除缓存
				CookieHelper.clearCookie(request, response, cookieValue);
			} else {
				// return null;
				log.info("resultCode:" + resultCode);
			}
		} catch (IOException e) {
			e.printStackTrace(System.err);
		} finally {
			httpPost.releaseConnection();
		}
		return null;
	}

	/**
	 * 获取当前登录用户
	 */
	@SuppressWarnings("unchecked")
	public UserModel getCurrentUser(HttpServletRequest request,
			HttpServletResponse response, String cookieValue) throws Exception {
		// TODO Auto-generated method stub
		if (StringUtils.isBlank(cookieValue))
			return null;
		String tokenStr= getUserByToken(request, response, cookieValue);
		if(tokenStr!=null){
			Map<String, Object> map = JsonUtils.readObjectByJson(tokenStr, Map.class);
			UserModel model = new UserModel();
			model.setUserId(map.get("UserId").toString());
			model.setNickName(map.get("NickName").toString());
			model.setToken(map.get("Token").toString());
			model.setEmail(map.get("Email").toString());
			model.setLoginName(ComUtils.getLoginNameByEmail(map.get("Email").toString()));
			return model;
		}
		return null;
	}

	/**
	 * 使用POST方式提交数据
	 * 
	 * @return
	 * @throws Exception
	 */
	/**
	 * 使用POST方式提交数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public HttpMethod getPostMethod(String cookieValue) throws Exception {
		String url = ssoToE2Constant.getE2RootUrl() + "apis/users.ashx";
		PostMethod post = new PostMethod(url);

		String method = "GetOnlineUser"; // 方法名称，固定值
		String appId = ssoToE2Constant.getAppId(); // 得到本应用appId
		String e2AppKey = ssoToE2Constant.getE2AppKey();

		String signText = (method + appId + cookieValue + e2AppKey)
				.toLowerCase();
		String sign = AESX3.md5(signText); // 签名
		// 属性键值

		NameValuePair nv1 = new NameValuePair("method", method);
		NameValuePair nv2 = new NameValuePair("appid", appId);
		NameValuePair nv3 = new NameValuePair("ck", cookieValue);
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
