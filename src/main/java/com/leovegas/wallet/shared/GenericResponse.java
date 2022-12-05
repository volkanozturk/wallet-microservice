package com.leovegas.wallet.shared;

/**
 * @author volkanozturk
 */
public class GenericResponse<T>  {

	private Boolean successful;
	private Integer status;
	private String message;
	private String errorCode;
	private String errorDetail;
	private T data;

	public Boolean getSuccessful() {
		return successful;
	}

	public void setSuccessful(Boolean successful) {
		this.successful = successful;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDetail() {
		return errorDetail;
	}

	public void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "GenericResponse{" +
				"successful=" + successful +
				", status=" + status +
				", message='" + message + '\'' +
				", errorCode='" + errorCode + '\'' +
				", errorDetail='" + errorDetail + '\'' +
				", data=" + data +
				'}';
	}
}
