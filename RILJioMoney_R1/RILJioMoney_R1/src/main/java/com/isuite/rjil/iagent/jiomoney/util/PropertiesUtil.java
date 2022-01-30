/**
 * 
 */
package com.isuite.rjil.iagent.jiomoney.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ist.file.init.UMStartupListener;

/**
 * @author NovelVox
 * 
 */
public final class PropertiesUtil {
	private static Logger logger = Logger.getLogger(PropertiesUtil.class);

	// private static byte[] just_for_lock = new byte[0];
	public static final String platform = "jiomoneyplatform.properties";
	public static final String jiomoneyutil = "jiomoneyutil.properties";

	private static Properties props;
	private static Properties jioMoneyUtilProps;
	private static boolean isLoaded = false;
	private static boolean isJioMoneyUtilLoaded = false;

	// private static PropertiesUtil propertiesUtil = null;
	private PropertiesUtil() {
		super();
	}

	/*
	 * public static PropertiesUtil getInstance() { if (propertiesUtil == null)
	 * { synchronized (just_for_lock) { if (propertiesUtil == null) {
	 * propertiesUtil = new PropertiesUtil(); } } } return propertiesUtil; }
	 */
	public static Properties getProperties(String fileName)
			throws IllegalStateException {
		InputStream input = null;

		try {
			if (fileName.equalsIgnoreCase(platform)) {
				if (!isLoaded) {
					props = new Properties();
					input = new FileInputStream(
							UMStartupListener.getConfFolderPath() + "/"
									+ fileName);
					
					if ("null".equals(input)) {
						throw new IllegalStateException("Either Property file "
								+ fileName + " is missing. It must be at path "
								+ UMStartupListener.getConfFolderPath());
					}
					props.load(input);
					isLoaded = true;
				}
				return props;
			} else if (fileName.equalsIgnoreCase(jiomoneyutil)) {
				if (!isJioMoneyUtilLoaded) {
					jioMoneyUtilProps = new Properties();

					input = new FileInputStream(
							UMStartupListener.getConfFolderPath() + "/"
									+ fileName);
					
					if ("null".equals(input)) {
						throw new IllegalStateException("Either Property file "
								+ fileName + " is missing. It must be at path "
								+ UMStartupListener.getConfFolderPath());
					}
					jioMoneyUtilProps.load(input);
					isJioMoneyUtilLoaded = true;
				}
				return jioMoneyUtilProps;
			} else {
				return props;
			}
		} catch (Exception ex) {
			logger.error("", ex);
			throw new IllegalStateException();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					// e.printStackTrace();
					logger.error("", e);
					throw new IllegalStateException(
							"IO Exception on closing the input");
				}
			}
		}
	}

	/**
	 * This Method is used to get all Key from properties file
	 * @param loadProps : input Properties File
	 * @return Set<Object>
	 */
	public static Set<Object> getAllKeys(Properties loadProps) { 
		Set<Object> keys = new HashSet<Object>();
		if (loadProps != null && !loadProps.isEmpty()) {
			keys = loadProps.keySet();
		}
		return keys;
	}

	/**
	 * This method is used to get the value by key with respect of Properties file
	 * @param key
	 *            : input as Property & Key
	 * @return : String
	 */
	public static String getPropertyValue(Properties properties,String key) {
		if (properties != null && !properties.isEmpty()
				&& key != null && !key.isEmpty()) {
			if(key!=null && !key.isEmpty()){
				return properties.getProperty(key);
			}
		}
		return "";  
	}
	
}
