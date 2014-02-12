package cn.me.xdf.common.utils.security;

/**
 * 
 * @author xiaobin
 * 
 */
public abstract class BaseDigestPasswordEncoder extends BasePasswordEncoder {
	// ~ Instance fields
	// ================================================================================================

	private boolean encodeHashAsBase64 = false;

	// ~ Methods
	// ========================================================================================================

	public boolean getEncodeHashAsBase64() {
		return encodeHashAsBase64;
	}

	/**
	 * return Hex (32 char) version of the hash bytes
	 */
	public void setEncodeHashAsBase64(boolean encodeHashAsBase64) {
		this.encodeHashAsBase64 = encodeHashAsBase64;
	}
}