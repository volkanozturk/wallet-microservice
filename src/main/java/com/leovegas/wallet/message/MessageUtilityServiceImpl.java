package com.leovegas.wallet.message;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author volkanozturk
 */
public class MessageUtilityServiceImpl {

	private static final Logger logger = LoggerFactory.getLogger(MessageUtilityServiceImpl.class);
	private static final Locale defaultLocale = Locale.ENGLISH;

	/**
	 * Get the bundle for the given locale.
	 *
	 * @param locale locale of the bundle
	 * @return ResourceBundle related to the locale
	 */
	public static ResourceBundle getBundle(Locale locale) {
		if (locale == null) {
			locale = Optional.of(Locale.getDefault()).orElse(defaultLocale);
		}
		return ResourceBundle.getBundle("messages", locale);
	}

	/**
	 * Get the message for the given key.
	 *
	 * @param messageKey key of the message
	 * @return String message
	 */
	public static String getMessage(String messageKey) {
		String message = "messageKey not found !!!";
		try {
			message = getBundle(null).getString(messageKey);
		} catch (Exception e) {
			logger.error(String.format("Error while getting message for messageKey %s", messageKey), e);
		}
		return message;
	}

	/**
	 * Get the message for the given key and locale.
	 *
	 * @param messageKey key of the message
	 * @param locale     locale of the message
	 * @return String message
	 */
	public static String getMessage(String messageKey, Locale locale) {
		return getBundle(locale).getString(messageKey);
	}

	/**
	 * Get the message for the given key and arguments.
	 *
	 * @param messageKey       key of the message
	 * @param messageArguments arguments of the message
	 * @return String message
	 */
	public static String getMessage(String messageKey, Object... messageArguments) {
		return getMessage(messageKey, messageArguments, null);
	}

	/**
	 * Get the message for the given key, locale and arguments.
	 *
	 * @param messageKey       key of the message
	 * @param locale           locale of the message
	 * @param messageArguments arguments of the message
	 * @return String message
	 */
	public static String getMessage(String messageKey, Locale locale, Object... messageArguments) {
		return MessageFormat.format(getBundle(locale).getString(messageKey), messageArguments);
	}

}
