package com.kj.oneservice.common.integration.config;

import static com.kj.oneservice.common.integration.util.CommonConstants.REQUEST_PATTERN;
import static com.kj.oneservice.common.integration.util.CommonConstants.YES;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletRequestEvent;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;

import com.kj.oneservice.common.integration.util.AppLogger;

/**
 * Context listener class for generating unique Request ID
 * 
 * <!-- This Class DOES NOT require any modification.-->
 * 
 * @author KIRANB
 */
@Configuration
@WebListener
public class RandomIDGenerationContextListener extends RequestContextListener {

	private static final AppLogger LOGGER = new AppLogger(RandomIDGenerationContextListener.class.getName());

	public void requestInitialized(ServletRequestEvent requestEvent) {

		MDC.put(REQUEST_PATTERN, UUID.randomUUID());
		LOGGER.debug("Request Initiated for :: " + MDC.get(REQUEST_PATTERN));
	}

	public void requestDestroyed(ServletRequestEvent requestEvent) {
		LOGGER.debug("Request Completed for :: " + MDC.get(REQUEST_PATTERN));
		MDC.clear();
	}
}