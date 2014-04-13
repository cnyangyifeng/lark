package com.kuxue.sso.model;

import org.apache.shiro.authc.UsernamePasswordToken;

public class ssoToken extends UsernamePasswordToken{

	public ssoToken(String loginName, String password) {
        super(loginName, password);
    }
}

