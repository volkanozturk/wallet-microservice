package com.leovegas.wallet.entity;

import com.leovegas.wallet.enums.TransactionType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * @author volkanozturk
 */

@Entity
@Table(name = "TRANSACTION", indexes = {@Index(name = "transaction_transaction_id", columnList = "transaction_id"),
		@Index(name = "transaction_wallet_id", columnList = "wallet_id")},
		uniqueConstraints = {@UniqueConstraint(name = "transaction_transaction_id", columnNames = "transaction_id")})
public class Transaction extends BaseEntity {

	@Column(name = "transaction_id", nullable = false, updatable = false)
	@Type(type = "uuid-char")
	private UUID transactionId;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "type")
	private TransactionType transactionType;

	@Column(name = "amount", nullable = false)
	private BigDecimal amount;

	@ManyToOne
	@JoinColumn(name = "wallet_id")
	private Wallet wallet;


	public Transaction() {
	}

	public Transaction(UUID transactionId, TransactionType transactionType, BigDecimal amount, Wallet wallet) {
		this.transactionId = transactionId;
		this.transactionType = transactionType;
		this.amount = amount;
		this.wallet = wallet;
	}

	public UUID getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(UUID transactionId) {
		this.transactionId = transactionId;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Transaction transaction = (Transaction) o;
		return Objects.equals(transactionId, transaction.transactionId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(transactionId);
	}


	@Override
	public String toString() {
		return "Transaction{" +
				"transactionId='" + transactionId + '\'' +
				", transactionType=" + transactionType +
				", amount=" + amount +
				", wallet=" + wallet +
				'}';
	}
}
