package cn.me.xdf.common.utils;

import java.util.ResourceBundle;

public class ResourceBundleReader {
	public final static Object initLock = new Object();

	private final static String PROPERTIES_FILE_NAME = "prop";

	private static ResourceBundle bundle = null;

	static {
		try {
			if (bundle == null) {
				synchronized (initLock) {
					if (bundle == null)
						bundle = ResourceBundle.getBundle(PROPERTIES_FILE_NAME);
				}
			}
		} catch (Exception e) {
			System.out.println("read prop.properties error!");
		}
	}

	public static ResourceBundle getBundle() {
		return bundle;
	}

	public static void setBundle(ResourceBundle bundle) {
		ResourceBundleReader.bundle = bundle;
	}
}
