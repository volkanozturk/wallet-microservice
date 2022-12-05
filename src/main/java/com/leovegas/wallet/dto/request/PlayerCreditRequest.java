package com.leovegas.wallet.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author volkanozturk
 */
public class PlayerCreditRequest extends BaseRequest {

	@Min(value = 0, message = "Amount cannot be less than 0")
	@NotNull(message = "Amount cannot be null")
	private BigDecimal amount;

	@NotNull(message = "Transaction id cannot be null")
	@NotBlank(message = "Transaction id is required")
	private String transactionId;


	public PlayerCreditRequest(String playerId, BigDecimal amount, String transactionId) {
		super(playerId);
		this.amount = amount;
		this.transactionId = transactionId;
	}

	public PlayerCreditRequest(BigDecimal amount, String transactionId) {
		this.amount = amount;
		this.transactionId = transactionId;
	}

	public PlayerCreditRequest() {
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	@Override
	public String toString() {
		return "PlayerCreditRequest{" +
				"amount=" + amount +
				", transactionId='" + transactionId + '\'' +
				'}';
	}
}
