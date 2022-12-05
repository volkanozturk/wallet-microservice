package com.leovegas.wallet.dto;

import com.leovegas.wallet.dto.response.BaseResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author volkanozturk
 */
public class WalletDto extends BaseResponse {

	@Schema(description = "Player Id")
	private String playerId;
	@Schema(description = "Player Balance")
	private BigDecimal balance;

	public WalletDto(Date createdAt, Date lastUpdatedAt, String playerId, BigDecimal balance) {
		super(createdAt, lastUpdatedAt);
		this.playerId = playerId;
		this.balance = balance;
	}

	public WalletDto(String playerId, BigDecimal balance) {
		this.playerId = playerId;
		this.balance = balance;
	}

	public WalletDto() {
	}

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

	@Override
	public String toString() {
		return "WalletDto{" +
				"playerId='" + playerId + '\'' +
				", balance=" + balance +
				'}';
	}
}
