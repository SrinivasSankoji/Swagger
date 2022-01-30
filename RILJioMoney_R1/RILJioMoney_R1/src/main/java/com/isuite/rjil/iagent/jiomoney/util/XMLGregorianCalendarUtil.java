/*
 * 
 */
package com.isuite.rjil.iagent.jiomoney.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;

/**
 * 
 * @author NovelVox A non instantiable util class to convert string date or date
 *         object into XMLGregorialCalnder object.
 */
public final class XMLGregorianCalendarUtil {
	private static final Logger logger = Logger
			.getLogger(XMLGregorianCalendarUtil.class);

	private static String dateFormat = "dd/MM/yyyy";
	private static boolean time = false;
	private static boolean timeZone = false;
	private static boolean fractionSeconds = false;

	public static String getDateFormat() {
		return dateFormat;
	}

	public static void setDateFormat(String dateFormat) {
		XMLGregorianCalendarUtil.dateFormat = dateFormat;
	}

	public static boolean isTime() {
		return time;
	}

	public static void setTime(boolean time) {
		XMLGregorianCalendarUtil.time = time;
	}

	public static boolean isTimeZone() {
		return timeZone;
	}

	public static void setTimeZone(boolean timeZone) {
		XMLGregorianCalendarUtil.timeZone = timeZone;
	}

	public static boolean isFractionSeconds() {
		return fractionSeconds;
	}

	public static void setFractionSeconds(boolean fractionSeconds) {
		XMLGregorianCalendarUtil.fractionSeconds = fractionSeconds;
	}

	private XMLGregorianCalendarUtil() {

	}

	public static String getSystemTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(cal.getTime());
	}

	public static XMLGregorianCalendar convertStringToXmlGregorian(
			String dateString) throws IllegalStateException {
		try {
			DateFormat df = new SimpleDateFormat(dateFormat);
			Date date = df.parse(dateString);
			return convertDateToXmlGregorian(date);
		} catch (ParseException e) {
			logger.error("", e);
			throw new IllegalStateException(e.getMessage());
		}
	}

	public static XMLGregorianCalendar convertDateToXmlGregorian(Date date)
			throws IllegalStateException {
		XMLGregorianCalendar xmlGregorianCalendar = null;
		try {
			GregorianCalendar gregorianCalendar = (GregorianCalendar) GregorianCalendar
					.getInstance();
			gregorianCalendar.setTime(date);

			xmlGregorianCalendar = setTime(gregorianCalendar, time);
			if (timeZone) {
				int offsetInMinutes = (gregorianCalendar
						.get(Calendar.ZONE_OFFSET) + gregorianCalendar
						.get(Calendar.DST_OFFSET))
						/ (60 * 1000);
				xmlGregorianCalendar.setTimezone(offsetInMinutes);
			}
			if (!fractionSeconds) {
				xmlGregorianCalendar.setFractionalSecond(null);
			}

		} catch (DatatypeConfigurationException e) {
			logger.error(
					"Exception in converting date into XMLGregorialCalendar object",
					e);
			throw new IllegalStateException(e.getMessage());
		}

		return xmlGregorianCalendar;
	}

	private static XMLGregorianCalendar setTime(
			GregorianCalendar gregorianCalendar, boolean time)
			throws DatatypeConfigurationException {
		if (time)
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(
					gregorianCalendar.get(Calendar.YEAR),
					gregorianCalendar.get(Calendar.MONTH) + 1,
					gregorianCalendar.get(Calendar.DAY_OF_MONTH),
					gregorianCalendar.get(Calendar.HOUR),
					gregorianCalendar.get(Calendar.MINUTE),
					gregorianCalendar.get(Calendar.SECOND),
					gregorianCalendar.get(Calendar.MILLISECOND),
					DatatypeConstants.FIELD_UNDEFINED);
		return DatatypeFactory.newInstance().newXMLGregorianCalendar(
				gregorianCalendar.get(Calendar.YEAR),
				gregorianCalendar.get(Calendar.MONTH) + 1,
				gregorianCalendar.get(Calendar.DAY_OF_MONTH),
				DatatypeConstants.FIELD_UNDEFINED,
				DatatypeConstants.FIELD_UNDEFINED,
				DatatypeConstants.FIELD_UNDEFINED,
				DatatypeConstants.FIELD_UNDEFINED,
				DatatypeConstants.FIELD_UNDEFINED);
	}

	/**
	 * 
	 * @param xmlDate
	 * @return java.util.Date object in dd/MM/yyyy format
	 */
	public static String toDate(XMLGregorianCalendar xmlDate) {
		return toDate(xmlDate, new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"));
	}

	public static String toDate(XMLGregorianCalendar xmlDate,
			SimpleDateFormat dateFormat) {
		return dateFormat.format(xmlDate.toGregorianCalendar().getTime());
	}

	public static String getProperDateFormat(String inputDate) {
		try {
			DateFormat originalFormat = new SimpleDateFormat("yyyymmddhhmmss");
			DateFormat targetFormat = new SimpleDateFormat(
					"dd-MM-yyyy HH:mm:ss");
			Date date = originalFormat.parse(inputDate);
			String formattedDate = targetFormat.format(date);
			return formattedDate;
		} catch (Exception e) {
			logger.error("", e);
			throw new IllegalStateException(e.getMessage());
		}
	}

	public static XMLGregorianCalendar getDateTime() throws Exception {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss");
		XMLGregorianCalendar xmlCalender = DatatypeFactory.newInstance()
				.newXMLGregorianCalendar(sdf.format(cal.getTime()).toString());
		return xmlCalender;
	}

	public static String parseDateFormat(XMLGregorianCalendar xmlDate)
			throws Exception {
		try {
			//xmlDate.add(DatatypeFactory.newInstance().newDuration(xmlDate.getTimezone()));
			DateFormat originalFormat = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss");
			DateFormat targetFormat = new SimpleDateFormat(
					"dd-MM-yyyy HH:mm:ss");
			Date date = originalFormat.parse(xmlDate.toString());
			
			String formattedDate = targetFormat.format(date);
			return formattedDate;
		} catch (Exception e) {
			logger.error("", e);
			throw new IllegalStateException(e.getMessage());
		}

	}
	/**
	 * This method is used to convert the date into required date format
	 * 
	 * @param inputDate
	 *            : Input date
	 * @param originalDateFormat
	 *            : Original date Format
	 * @param expectedDateFormat
	 *            : Expected date Format
	 * @return : String
	 * @throws ParseException
	 */
	public static String convertDateFormat(String inputDate,
			String originalDateFormat, String expectedDateFormat)
			throws ParseException {
		if (inputDate != null && !inputDate.equals("0")) {
			SimpleDateFormat originalFormat = new SimpleDateFormat(
					originalDateFormat);
			SimpleDateFormat expectedFormat = new SimpleDateFormat(
					expectedDateFormat);
			Date date = originalFormat.parse(inputDate);
			return expectedFormat.format(date).toString();
		} else {
			return "";
		}
	}
/*	public static String paseDateFormatwithmillisecond(XMLGregorianCalendar xmlDate)
			throws Exception {
		try {
			DateFormat originalFormat = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss");
			DateFormat targetFormat = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss:SSSZ");
			Date date = originalFormat.parse(xmlDate.toString());
			String formattedDate = targetFormat.format(date);
			return formattedDate;
		} catch (Exception e) {
			logger.error("", e);
			throw new IllegalStateException(e.getMessage());
		}

	}*/
	
	public static XMLGregorianCalendar getDateTimewithmillisecond() throws Exception {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss:SSSZ");
		XMLGregorianCalendar xmlCalender = DatatypeFactory.newInstance()
				.newXMLGregorianCalendar(sdf.format(cal.getTime()).toString());
		return xmlCalender;
	}
	
		
	}
