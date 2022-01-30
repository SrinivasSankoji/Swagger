package com.isuite.rjil.iagent.sso;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.util.PropertiesUtil;
import com.isuite.rjil.iagent.jiomoney.util.Util;
import com.isuite.util.crypto.ISuiteCryptoUtil;

/**
 * This filter will apply on all address bar url to validate url eg java script
 * < etc...> if contains the java script tag then will redirect to some default
 * page For use this url filter pls add this filter in client-web.xml file
 * 
 * @author BAL VIKAS NIRALA
 * 
 *         * @author Modified By Rubal
 * 
 */

public class ValidateUrlFilter implements Filter {

	private static final Logger LOGGER = Logger.getLogger(ValidateUrlFilter.class);
	private Properties properties = PropertiesUtil.getProperties(PropertiesUtil.platform);

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		// checking these tags in address bar URL
		String checkTags = properties.getProperty("CheckTags");
		String browserids = properties.getProperty("UnsupportedBrowser");
		String uniqueKey = request.getParameter("st");
		LOGGER.info("xxxxxxxx uniqueKey:"+uniqueKey);
		String UniqueSessionFlag = properties.getProperty("UniqueSessionFlag");
		if("true".equalsIgnoreCase(UniqueSessionFlag) )
		{

			if ( null != uniqueKey && uniqueKey.length() > 30) 
			{
				if (null==properties.getProperty("uniqueSessionLoc")) 
				{
					((HttpServletResponse) response).sendRedirect("/notFound.html");
					return;
				}
				String fileName = properties.getProperty("uniqueSessionLoc");
				LOGGER.info("File Location from NV Context:" + fileName);
				File file = ObjectSerializationUtils.fileExistance(new File(
						fileName));
				LOGGER.info("File from NV Context:" + file);
				Set<String> data = ObjectSerializationUtils.readObject(file);
				boolean isExpired = false;
				LOGGER.info("NV Context - SIZE:" + data.size() + ",DATA:" + data
						+ ", Unique Key:" + uniqueKey);
				
		
				if (data.contains(uniqueKey)) 
				{
					LOGGER.info("data contains unique key called");
					isExpired = checkSessionTimeOut(uniqueKey);
					data.remove(uniqueKey);
					ObjectSerializationUtils.writeObject(file, data);
				} else 
				{
					isExpired = true;
				}
				printData(ObjectSerializationUtils.readObject(file), uniqueKey);
				if (isExpired) 
				{
					((HttpServletResponse) response)
							.sendRedirect("/urlExpired.html");
					return;
				}
	
			} else 
			{
				((HttpServletResponse) response).sendRedirect("/notFound.html");
				return;
			}
		}
		// Here we are checking unsupported browser

		String[] split = checkTags.split(",");
		String[] browser = browserids.split(",");
		String url = ((HttpServletRequest) request).getRequestURL().toString();
		url = url.toLowerCase();
		String userAgent = ((HttpServletRequest) request).getHeader("User-Agent");
		String queryString = ((HttpServletRequest) request).getQueryString();
		if (null != browser && !browser.equals("") && browser.length > 0) {

			for (String str : browser) {
				if (!str.equals("") && str.length() > 0) {
					if (userAgent.contains(str)) 
					{
						redirectToUrl(response, "/UnsupportedBrowser.html");
						return;
					}
				}
			}
		}

		// Here we are checking java scripting tag
		for (String tags : split) {

			if (null != url && !url.equals("")) {
				if (url.contains(tags)) 
				{

					redirectToUrl(response, "/invaildUrl.html");
					return;
				}
			}
			if (null != queryString && !queryString.equals("")) {
				if (queryString.contains(tags)) {

					redirectToUrl(response, "/invaildUrl.html");
					return;
				}
			}
		}
		String lModuleName = ((HttpServletRequest) request).getParameter("module");
		String lstrEnv = properties.getProperty("SetupEnv");
		if("production".equalsIgnoreCase(lstrEnv) && Util.isEmptyString(lModuleName) && !lModuleName.equals(properties.getProperty("moduleName")))
		{

			//redirectToUrl(response, "/invaildUrl.html");
			//return;
		}
		
		Enumeration lParams = request.getParameterNames();
		while(lParams.hasMoreElements())
		{
			String lParamValue = request.getParameter((String)lParams.nextElement());
			for (String tags : split) {
				
				if (lParamValue.contains(tags)) 
				{

					redirectToUrl(response, "/invaildUrl.html");
					return;
				}
			}
		}
		chain.doFilter(request, response);
	}
	
	private void redirectToUrl(ServletResponse pResponse , String pURL) throws IOException
	{
		((HttpServletResponse) pResponse).sendRedirect(pURL);
	}
	public static void printData(Set<String> data, String uniqueKey) {
		LOGGER.info("start---------Active Session Data");
		try {
			for (String str : data) {
				LOGGER.info(ISuiteCryptoUtil.decode(str));
			}
			//LOGGER.debug(ISuiteCryptoUtil.decode(uniqueKey));
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("end----Active Session Data");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	/**
	 * This method is taking session time out from url or blank , if it is blank
	 * it will redirect to no page found , if it is expired will redirect to url
	 * expired page
	 * 
	 * @param sessionTime
	 */

	private boolean checkSessionTimeOut(String uniqueKey) {

		boolean isExpired = false;

		try {
			uniqueKey = ISuiteCryptoUtil.decode(uniqueKey);
			String split[] = uniqueKey.split("#");
			String sessionTime = split[1];

			long urlSessionTime = Long.parseLong(sessionTime);
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(urlSessionTime);

			LOGGER.info("Decoded URL session time out __________________"
					+ calendar.getTime());

			long now = System.currentTimeMillis();
			long diff = now - urlSessionTime;

			LOGGER.info("Diff in url session time is " + diff);

			long diffTimeOut = 10000; // default 10 seconds
			diffTimeOut = Long.parseLong(properties.get("urlSessionTimeOut")
					+ "") * 1000;
			LOGGER.info("diffTimeOut in url session time is " + diffTimeOut);
			if (diff > diffTimeOut) {
				LOGGER.info("URL session has been time out redrecting to new page.....");
				isExpired = true;
			}

		} catch (Throwable e) {
			LOGGER.error("Exception catched while decoding URL Parameters", e);

		}

		return isExpired;

	}

	
}