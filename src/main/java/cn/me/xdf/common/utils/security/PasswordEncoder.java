package cn.me.xdf.common.utils.security;

import org.springframework.dao.DataAccessException;

/**
 * 
 * @author xiaobin
 * 
 */
public interface PasswordEncoder {
	// ~ Methods
	// ========================================================================================================

	public String encodePassword(String rawPass, Object salt)
			throws DataAccessException;

	public boolean isPasswordValid(String encPass, String rawPass, Object salt)
			throws DataAccessException;
}