package com.leovegas.wallet.aspect;

import com.leovegas.wallet.annotation.ApiLogger;
import com.leovegas.wallet.dto.ApiLogDto;
import com.leovegas.wallet.mapper.ApiLogMapper;
import com.leovegas.wallet.mapper.CustomObjectMapper;
import com.leovegas.wallet.repository.ApiLogRepository;
import com.leovegas.wallet.utility.CommonUtility;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

/**
 * @author volkanozturk
 */
@Aspect
@Component
public class ApiLogAspect {

	private final ApiLogRepository apiLogRepository;
	private final ApiLogMapper apiLogMapper;
	@Qualifier(value = "customObjectMapper")
	private final CustomObjectMapper customObjectMapper;


	public ApiLogAspect(ApiLogRepository apiLogRepository, ApiLogMapper apiLogMapper, CustomObjectMapper customObjectMapper) {
		this.apiLogRepository = apiLogRepository;
		this.apiLogMapper = apiLogMapper;
		this.customObjectMapper = customObjectMapper;
	}

	/**
	 * @param joinPoint ProceedingJoinPoint support around advice
	 * @param apiLogger ApiLogger DTO in order to generate the log for API calls
	 * @return Object that is the result of proceed of annotation joint
	 * @throws Throwable throws exception during operations of saving the API call logs
	 */
	@Around("@annotation(apiLogger)")
	public Object log(ProceedingJoinPoint joinPoint, ApiLogger apiLogger) throws Throwable {
		HttpServletRequest request = CommonUtility.getHttpServletRequest();
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		try {
			Object response = joinPoint.proceed();
			stopWatch.stop();
			String responseToJson = this.customObjectMapper.toJson(response);
			this.saveApiLog(this.generateApiLog(joinPoint, request, stopWatch, responseToJson, null));
			return response;
		} catch (Exception e) {
			stopWatch.stop();
			String errorCode = "";
			if (RequestContextHolder.getRequestAttributes() != null) {
				RequestContextHolder.getRequestAttributes().setAttribute("errorCode", errorCode, RequestAttributes.SCOPE_REQUEST);
			}
			this.generateApiLog(joinPoint, request, stopWatch, errorCode, e.getMessage());
			throw e;
		}
	}

	/**
	 * Method for saving ApiLogEntity to ApiLog Repository
	 *
	 * @param apiLogDto object that is generated to be saved into related ApiLogEntity Repository
	 */
	private void saveApiLog(ApiLogDto apiLogDto) {
		this.apiLogRepository.save(this.apiLogMapper.toEntity(apiLogDto));
	}

	/**
	 * Method for generating ApiLog object from API call request, response, duration of API response time from stopwatch and exception message if exists
	 *
	 * @param joinPoint        ProceedingJoinPoint support around advice
	 * @param request          API call request
	 * @param stopWatch        StopWatch object for API response time
	 * @param responseToJson   API response in JSON string
	 * @param exceptionMessage exception message if exists
	 * @return ApiLog object that is generated. Please, see the {@link com.leovegas.wallet.dto} class for details.
	 */
	private ApiLogDto generateApiLog(ProceedingJoinPoint joinPoint, HttpServletRequest request, StopWatch stopWatch, String responseToJson, String exceptionMessage) {
		return ApiLogDto.ApiLogDtoBuilder.builder()
				.sourceIpAddress(CommonUtility.getIpAddress(request))
				.httpRequestMethod(request.getMethod())
				.endPoint(request.getRequestURL().toString())
				.requestPayload(CommonUtility.generatePayloadDetail(joinPoint))
				.responsePayload(responseToJson)
				.httpStatusCode(200)
				.totalDuration(stopWatch.getTotalTimeMillis())
				.exceptionMessage(exceptionMessage)
				.callDate(Calendar.getInstance().getTime())
				.build();
	}
}

