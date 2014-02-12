package cn.me.xdf.sso.e2.constant;

/**
 * E2 SSO常用变量
 * 
 * @author xiaobin
 * 
 */
public class SSOToE2Constant {

	private String appId;

	private String e2AppKey;

	private String e2AppEn32Key;

	// E2Token 顶级Cookie加密
	private String e2Token32Key;
	// E2跟路径
	private String e2RootUrl;
	// 顶级域名
	private String e2TopDomain;

	/**
	 * Cookie的最大有效期
	 */
	private Integer maxAge;

	/**
	 * cookie name
	 */
	private String cookieName;
	
	private boolean open;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getE2AppKey() {
		return e2AppKey;
	}

	public void setE2AppKey(String e2AppKey) {
		this.e2AppKey = e2AppKey;
	}

	public String getE2AppEn32Key() {
		return e2AppEn32Key;
	}

	public void setE2AppEn32Key(String e2AppEn32Key) {
		this.e2AppEn32Key = e2AppEn32Key;
	}

	public String getE2Token32Key() {
		return e2Token32Key;
	}

	public void setE2Token32Key(String e2Token32Key) {
		this.e2Token32Key = e2Token32Key;
	}

	public String getE2RootUrl() {
		return e2RootUrl;
	}

	public void setE2RootUrl(String e2RootUrl) {
		this.e2RootUrl = e2RootUrl;
	}

	public String getE2TopDomain() {
		return e2TopDomain;
	}

	public void setE2TopDomain(String e2TopDomain) {
		this.e2TopDomain = e2TopDomain;
	}

	public Integer getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(Integer maxAge) {
		this.maxAge = maxAge;
	}

	public String getCookieName() {
		return cookieName;
	}

	public void setCookieName(String cookieName) {
		this.cookieName = cookieName;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
	
	

}
