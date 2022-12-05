package com.leovegas.wallet.dto;

import com.leovegas.wallet.dto.response.BaseResponse;
import com.leovegas.wallet.enums.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * @author volkanozturk
 */
public class TransactionDto extends BaseResponse {

	@Schema(description = "Unique Transaction UUID")
	private UUID transactionId;
	@Schema(description = "Transaction Amount")
	private BigDecimal amount;
	@Schema(description = "Transaction Type")
	private TransactionType transactionType;


	public TransactionDto(UUID transactionId, BigDecimal amount, TransactionType transactionType) {
		this.transactionId = transactionId;
		this.amount = amount;
		this.transactionType = transactionType;
	}

	public TransactionDto(Date createdAt, Date lastUpdatedAt, UUID transactionId, BigDecimal amount, TransactionType transactionType) {
		super(createdAt, lastUpdatedAt);
		this.transactionId = transactionId;
		this.amount = amount;
		this.transactionType = transactionType;
	}

	public TransactionDto() {
	}

	public UUID getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(UUID transactionId) {
		this.transactionId = transactionId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public String toString() {
		return "TransactionDto{" +
				"transactionId=" + transactionId +
				", amount=" + amount +
				", transactionType=" + transactionType +
				'}';
	}
}
