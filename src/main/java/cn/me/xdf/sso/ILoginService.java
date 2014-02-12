package cn.me.xdf.sso;

/**
 * E2登录接口
 * 
 * <pre>
 * 摘自ixdf平台苟玉超写的登录认证接口
 * </pre>
 * 
 * <pre>
 * Spring Bean 的ID为:ssoLoginService
 * </pre>
 * 
 * @author xiaobin
 * 
 */
public interface ILoginService {

	public boolean login(String _username, String _password) throws Exception;

}
