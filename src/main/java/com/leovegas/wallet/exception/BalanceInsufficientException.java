package com.leovegas.wallet.exception;

/**
 * @author volkanozturk
 */
public class BalanceInsufficientException extends RuntimeException {

	public BalanceInsufficientException(String message) {
		super(message);
	}

}
