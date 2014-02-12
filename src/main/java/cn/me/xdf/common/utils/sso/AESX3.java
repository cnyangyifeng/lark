package cn.me.xdf.common.utils.sso;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;


public class AESX3 {

	public static void main(String args[]) throws Exception,
			UnsupportedEncodingException, Exception {
		// org.noe.framework.util.log.Log.debug(AESX3.trim("AppId=15&E=x001%40C%23%E8%AF%9A.com&K=&Token=%25%24_%20%26%23R%2515&","&"));
		String aa = AESX3.aesEncode("Mike$_中文", "e2_test_test_test_test_test_test");
		System.out.println(aa);
	}

	public static String aesEncode(String sourceText, String aesKey) {

		try {
			return SSOBase64
					.encode(AES.encode(sourceText.getBytes("UTF-8"),
							aesKey.getBytes("UTF-8"))).replaceAll("\\n", "")
					.replaceAll("\\r", "");
		} catch (UnsupportedEncodingException e) {
		} catch (Exception e) {
		}
		return null;
	}

	public static String Encode16(String sourceText, String aesKey)
			throws UnsupportedEncodingException, Exception {
		return Hex.encodeHexString(AES.encode(sourceText.getBytes("UTF-8"),
				aesKey.getBytes("UTF-8")));
	}

	public static String Decode16(String encodeText, String aesKey)
			throws Exception, UnsupportedEncodingException, Exception {
		return new String(AES.decode(Hex.decodeHex(encodeText.toCharArray()),
				aesKey.getBytes("UTF-8")), "UTF-8");
	}

	public static String aesDecode(String encodeText, String aesKey) {
		try {
			return new String(AES.decode(
					SSOBase64.decode(encodeText.getBytes("UTF-8")),
					aesKey.getBytes("UTF-8")), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public final static String md5(String s) {

		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			return Hex.encodeHexString(md).toUpperCase();
		} catch (Exception e) {
			return "";
		}
	}

	// php trim($str,$char)
	public static String trim(String src, char c) {
		src = src.replaceAll("^" + c, "");
		src = src.replaceAll(c + "$", "");

		return src.trim();
	}

	// php trim($str,$char)
	public static String trim(String src, String c) {
		src = src.replaceAll("^" + c, "");
		src = src.replaceAll(c + "$", "");
		return src.trim();
	}

	// php trim($str)
	public static String trim(String src) {
		return src.trim();
	}

	// php rawurlencode($url)
	public static String rawurlencode(String url) {
		try {
			return EnCode.rawurlencode(url);
		} catch (Exception e) {
		}
		return "";
	}
}
