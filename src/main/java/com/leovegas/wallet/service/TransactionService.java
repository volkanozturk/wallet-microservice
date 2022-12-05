package com.leovegas.wallet.service;

import com.leovegas.wallet.entity.Transaction;

import java.util.Optional;
import java.util.UUID;

/**
 * @author volkanozturk
 */
public interface TransactionService {
	Optional<Transaction> findByTransactionId(UUID transactionId);

	void save(Transaction transaction);


}
