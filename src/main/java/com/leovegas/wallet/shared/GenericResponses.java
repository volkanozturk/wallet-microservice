package com.leovegas.wallet.shared;

import org.springframework.http.HttpStatus;

/**
 * @author volkanozturk
 */
public final class GenericResponses<T> {

	private static final GenericResponse DEFAULT_SUCCESS = from(null);

	public static GenericResponse successful() {
		return DEFAULT_SUCCESS;
	}

	public static <T> GenericResponse<T> from(T data) {
		GenericResponse<T> genericResponse = new GenericResponse<>();
		genericResponse.setSuccessful(true);
		genericResponse.setStatus(HttpStatus.OK.value());
		genericResponse.setData(data);
		return genericResponse;
	}

	public static <T> GenericResponse<T> failure(Integer status) {
		GenericResponse<T> genericResponse = new GenericResponse<>();
		genericResponse.setSuccessful(false);
		genericResponse.setStatus(status);
		return genericResponse;
	}
}

