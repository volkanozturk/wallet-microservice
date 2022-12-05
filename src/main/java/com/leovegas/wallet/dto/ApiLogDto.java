package com.leovegas.wallet.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author volkanozturk
 */
public class ApiLogDto implements Serializable {

	/**
	 * Id for ApiLog Class in Long type.
	 */
	private Long id;

	/**
	 * Source IP Address in String Type.
	 */
	private String sourceIpAddress;

	/**
	 * HTTP Request Method in String type. This field cannot be null.
	 * Allowed values are POST, PUT, PATCH, GET and DELETE.
	 */
	@NotNull
	private String httpRequestMethod;

	/**
	 * API End-Point value in String type. This field cannot be null.
	 */
	@NotNull
	private String endPoint;

	/**
	 * API Request Payload in String type.
	 */
	private String requestPayload;

	/**
	 * Response Payload in String type.
	 */
	private String responsePayload;

	/**
	 * API HTTP Status Code in Integer type. Values can be checked from <a href="https://httpstatuses.com">HTTP Statuses Web Site</a>.
	 */
	private Integer httpStatusCode;

	/**
	 * Total Duration of API call in MilliSeconds in Long type.
	 */
	private Long totalDuration;

	/**
	 * API Exception Message if there is any in String type.
	 */
	private String exceptionMessage;

	/**
	 * API Call Date in Date type.
	 */
	private Date callDate;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSourceIpAddress() {
		return sourceIpAddress;
	}

	public void setSourceIpAddress(String sourceIpAddress) {
		this.sourceIpAddress = sourceIpAddress;
	}

	public String getHttpRequestMethod() {
		return httpRequestMethod;
	}

	public void setHttpRequestMethod(String httpRequestMethod) {
		this.httpRequestMethod = httpRequestMethod;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public String getRequestPayload() {
		return requestPayload;
	}

	public void setRequestPayload(String requestPayload) {
		this.requestPayload = requestPayload;
	}

	public String getResponsePayload() {
		return responsePayload;
	}

	public void setResponsePayload(String responsePayload) {
		this.responsePayload = responsePayload;
	}

	public Integer getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(Integer httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public Long getTotalDuration() {
		return totalDuration;
	}

	public void setTotalDuration(Long totalDuration) {
		this.totalDuration = totalDuration;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public Date getCallDate() {
		return callDate;
	}

	public void setCallDate(Date callDate) {
		this.callDate = callDate;
	}

	@Override
	public String toString() {
		return "ApiLogDto{" +
				"id=" + id +
				", sourceIpAddress='" + sourceIpAddress + '\'' +
				", httpRequestMethod='" + httpRequestMethod + '\'' +
				", endPoint='" + endPoint + '\'' +
				", requestPayload='" + requestPayload + '\'' +
				", responsePayload='" + responsePayload + '\'' +
				", httpStatusCode=" + httpStatusCode +
				", totalDuration=" + totalDuration +
				", exceptionMessage='" + exceptionMessage + '\'' +
				", callDate=" + callDate +
				'}';
	}

	public static final class ApiLogDtoBuilder {
		private Long id;
		private String sourceIpAddress;
		private String httpRequestMethod;
		private String endPoint;
		private String requestPayload;
		private String responsePayload;
		private Integer httpStatusCode;
		private Long totalDuration;
		private String exceptionMessage;
		private Date callDate;

		private ApiLogDtoBuilder() {
		}

		public static ApiLogDtoBuilder builder() {
			return new ApiLogDtoBuilder();
		}

		public ApiLogDtoBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public ApiLogDtoBuilder sourceIpAddress(String sourceIpAddress) {
			this.sourceIpAddress = sourceIpAddress;
			return this;
		}

		public ApiLogDtoBuilder httpRequestMethod(String httpRequestMethod) {
			this.httpRequestMethod = httpRequestMethod;
			return this;
		}

		public ApiLogDtoBuilder endPoint(String endPoint) {
			this.endPoint = endPoint;
			return this;
		}

		public ApiLogDtoBuilder requestPayload(String requestPayload) {
			this.requestPayload = requestPayload;
			return this;
		}

		public ApiLogDtoBuilder responsePayload(String responsePayload) {
			this.responsePayload = responsePayload;
			return this;
		}

		public ApiLogDtoBuilder httpStatusCode(Integer httpStatusCode) {
			this.httpStatusCode = httpStatusCode;
			return this;
		}

		public ApiLogDtoBuilder totalDuration(Long totalDuration) {
			this.totalDuration = totalDuration;
			return this;
		}

		public ApiLogDtoBuilder exceptionMessage(String exceptionMessage) {
			this.exceptionMessage = exceptionMessage;
			return this;
		}

		public ApiLogDtoBuilder callDate(Date callDate) {
			this.callDate = callDate;
			return this;
		}

		public ApiLogDto build() {
			ApiLogDto apiLogDto = new ApiLogDto();
			apiLogDto.setId(id);
			apiLogDto.setSourceIpAddress(sourceIpAddress);
			apiLogDto.setHttpRequestMethod(httpRequestMethod);
			apiLogDto.setEndPoint(endPoint);
			apiLogDto.setRequestPayload(requestPayload);
			apiLogDto.setResponsePayload(responsePayload);
			apiLogDto.setHttpStatusCode(httpStatusCode);
			apiLogDto.setTotalDuration(totalDuration);
			apiLogDto.setExceptionMessage(exceptionMessage);
			apiLogDto.setCallDate(callDate);
			return apiLogDto;
		}
	}

}
