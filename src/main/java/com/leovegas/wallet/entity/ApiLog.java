package com.leovegas.wallet.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author volkanozturk
 */
@Entity
@Table
public class ApiLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String sourceIpAddress;

	@NotNull
	private String httpRequestMethod;

	@NotNull
	private String endPoint;

	@Column(length = 1024)
	private String requestPayload;

	@Column(length = 1024)
	private String responsePayload;

	@Column
	private Integer httpStatusCode;

	@Column
	private Long totalDuration;

	@Column(length = 1024)
	private String exceptionMessage;

	@Column
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
		return "ApiLog{" +
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
}
