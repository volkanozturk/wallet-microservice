package com.leovegas.wallet.exception;

/**
 * @author volkanozturk
 */
public class NonUniqueTransactionException extends RuntimeException{

	public NonUniqueTransactionException(String message) {
		super(message);
	}
}
