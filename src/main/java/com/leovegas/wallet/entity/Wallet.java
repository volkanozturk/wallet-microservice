package com.leovegas.wallet.entity;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * @author volkanozturk
 */
@Entity
@Table(name = "WALLET", indexes = {@Index(name = "wallet_player_id", columnList = "player_id")})
public class Wallet extends BaseEntity {
	@Column(length = 190, name = "player_id", nullable = false, updatable = false)
	private String playerId;

	@Column(name = "balance", nullable = false)
	private BigDecimal balance;

	@OneToMany(mappedBy = "wallet")
	private List<Transaction> transactions;

	@Version
	private Long version;


	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}


	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Wallet that = (Wallet) o;
		return Objects.equals(playerId, that.playerId) &&
				Objects.equals(balance, that.balance);
	}

	@Override
	public int hashCode() {
		return Objects.hash(playerId, balance);
	}

	@Override
	public String toString() {
		return "Wallet{" +
				"playerId=" + playerId +
				", balance=" + balance +
				", transactions=" + transactions +
				'}';
	}
}
