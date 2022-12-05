package com.leovegas.wallet.service.impl;

import com.leovegas.wallet.entity.Transaction;
import com.leovegas.wallet.repository.TransactionRepository;
import com.leovegas.wallet.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * @author volkanozturk
 */
@Service
public class TransactionServiceImp implements TransactionService {

	private final TransactionRepository transactionRepository;

	public TransactionServiceImp(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	@Override
	public Optional<Transaction> findByTransactionId(UUID transactionId) {
		return transactionRepository.findTransactionByTransactionId(transactionId);
	}

	@Override
	public void save(Transaction transaction) {
		configureDefaults(transaction);
		transactionRepository.save(transaction);
	}

	private void configureDefaults(Transaction transaction) {
		Date now = new Date();
		transaction.setCreatedAt(now);
		transaction.setLastUpdatedAt(now);
	}

}
