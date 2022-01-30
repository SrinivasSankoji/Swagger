package com.isuite.rjil.iagent.jiomoney.filters;

import java.io.IOException;
import java.util.Properties;

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

	private static final Logger log = Logger.getLogger(ValidateUrlFilter.class);
	Properties properties = PropertiesUtil.getProperties(PropertiesUtil.platform);

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		boolean someThingFishy = false;
		// checking these tags in address bar URL
		String checkTags = properties.getProperty("CheckTags");
		String browserids = properties.getProperty("UnsupportedBrowser");

		String[] split = checkTags.split(",");
		String[] browser = browserids.split(",");
		String url = ((HttpServletRequest) request).getRequestURL().toString();
		url = url.toLowerCase();
		String userAgent = ((HttpServletRequest) request)
				.getHeader("User-Agent");
		String queryString = ((HttpServletRequest) request).getQueryString();
		if (null != browser && !browser.equals("") && browser.length > 0) {

			for (String str : browser) {
				if (!str.equals("") && str.length() > 0) {
					if (userAgent.contains(str)) {
						((HttpServletResponse) response)
								.sendRedirect("/UnsupportedBrowser.html");
						return;
					}
				}
			}
		}
		for (String tags : split) {

			if (null != url && !url.equals("")) {
				if (url.contains(tags)) {
					someThingFishy = true;
					break;
				}
			}
			if (null != queryString && !queryString.equals("")) {
				if (queryString.contains(tags)) {
					someThingFishy = true;
					break;
				}
			}
		}

		if (someThingFishy) {

			log.debug("Some thing fishy found in address bar Redirecting to :: /invaildUrl.html ");
			((HttpServletResponse) response).sendRedirect("/invaildUrl.html");
			return;
		} else {
			log.debug("Everything is all right in address bar url ");
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

}
