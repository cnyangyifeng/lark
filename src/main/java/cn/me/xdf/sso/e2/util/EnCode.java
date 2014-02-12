package cn.me.xdf.sso.e2.util;

import java.nio.charset.Charset;


public class EnCode {
	  private static char toHexDigit(int d)
	  {
	    d = d & 0xf;

	    if (d < 10)
	      return (char) ('0' + d);
	    else
	      return (char) ('A' + d - 10);
	  }
		static final int caseDiff = ('a' - 'A');

		public static String EscapeDataString(String str) 
		{
				  return rawurlencode(str);
		 }
	public static String decode(String str) 
	{
			  return rawurlencode(str);
	}
		
	  public static String rawurlencode(String str) 
	  {
	    if (str == null)
	      return "";
    	Charset charset;
 

		  charset = Charset.forName("UTF-8");

		 
		  
	
	    StringBuilder sb = new StringBuilder();

	    for (int i = 0; i < str.length(); i++) {
	      char ch = str.charAt(i);
           
	      if ('a' <= ch && ch <= 'z'
	          || 'A' <= ch && ch <= 'Z'
	          || '0' <= ch && ch <= '9'
	          || ch == '-' || ch == '_' || ch == '.' || ch == '~') {
	        sb.append(ch);
	      }
	      else {
	    	  if(ch>128){
	    		  
	    		 
	  			byte[] ba = (ch+ "").getBytes(charset);
	  			for (int j = 0; j < ba.length; j++) {
	  				sb.append('%');
	  			    char ch0 = Character.forDigit((ba[j] >> 4) & 0xF, 16);
	   
	  			    if (Character.isLetter(ch0)) {
	  				ch0 -= caseDiff;
	  			    }
	  			  sb.append(ch0);
	  			ch0 = Character.forDigit(ba[j] & 0xF, 16);
	  			    if (Character.isLetter(ch0)) {
	  				ch0 -= caseDiff;
	  			    }
	  			  sb.append(ch0);
	  			}
	  			
	    	
	    	  }else{
	    	  sb.append("%");
	        sb.append(toHexDigit(ch >> 4));
	        
	        sb.append(toHexDigit(ch));
	    	  }
	    	}
	    }

	    return sb.toString();
	  }

	  enum ParseUrlState {
	    INIT, USER, PASS, HOST, PORT, PATH, QUERY, FRAGMENT
	  };
	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j)
					|| Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	/**
	 * 解码 说明：本方法保证 不论参数s是否经过escape()编码，均能得到正确的“解码”结果
	 * 
	 * @param s
	 * @return
	 */
	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src
							.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src
							.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}

}
