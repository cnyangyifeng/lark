package cn.me.xdf.common.utils.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

public class Md5CredentialsMatcher extends SimpleCredentialsMatcher{
	
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token,
			AuthenticationInfo info) {
		
		Object tokenCredentials = DigestUtils.md5Hex(toBytes(token.getCredentials()));
		Object accountCredentials = getCredentials(info);
		return equals(tokenCredentials, accountCredentials);
	}


}
