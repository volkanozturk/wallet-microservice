package com.leovegas.wallet.service;

import com.leovegas.wallet.dto.WalletDto;
import com.leovegas.wallet.entity.Transaction;
import com.leovegas.wallet.entity.Wallet;

import java.util.List;
import java.util.Optional;

/**
 * @author volkanozturk
 */
public interface WalletService {

	Wallet getWalletByPlayerId(String playerId);

	Wallet getWalletLockByPlayerId(String playerId);
	List<WalletDto> getAllPlayersBalance(int page, int size);
	Wallet save(Wallet wallet);
	List<Transaction> getWalletTransactionByPlayerId(String playerId);

}
