package com.leovegas.wallet.exception;

/**
 * @author volkanozturk
 */
	public class WalletNotFoundException extends RuntimeException {
		public WalletNotFoundException(String message) {
			super(message);
		}
}
