package com.leovegas.wallet.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author volkanozturk
 */
public final class CommonUtility{

	private CommonUtility() {
		throw new IllegalStateException("Utility class");
	}
	public static String generatePayloadDetail(JoinPoint joinPoint) {
		CodeSignature signature = (CodeSignature) joinPoint.getSignature();
		ObjectMapper om = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < joinPoint.getArgs().length; i++) {
			if (signature.getParameterNames() != null) {
				stringBuilder.append(signature.getParameterNames()[i]);
			} else {
				stringBuilder.append("parameter").append(i);
			}
			stringBuilder.append(":");
			try {
				stringBuilder.append(om.writer().writeValueAsString(joinPoint.getArgs()[i]));

			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			stringBuilder.append(",");
		}
		return stringBuilder.toString();
	}
	public static String getIpAddress(HttpServletRequest request) {
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress != null)
			return ipAddress;
		else return request.getRemoteAddr();
	}

	public static HttpServletRequest getHttpServletRequest() {
		RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
		if (Objects.nonNull(attributes)) {
			return ((ServletRequestAttributes) attributes).getRequest();
		}
		return null;
	}

	public static String getHeaderValue(String headerName) {
		HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		if (Objects.nonNull(httpServletRequest.getHeader(headerName)))
			return httpServletRequest.getHeader(headerName);
		return null;
	}
}
