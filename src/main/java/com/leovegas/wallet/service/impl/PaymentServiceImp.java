package com.leovegas.wallet.service.impl;

import com.leovegas.wallet.dto.PaymentDto;
import com.leovegas.wallet.entity.Transaction;
import com.leovegas.wallet.entity.Wallet;
import com.leovegas.wallet.enums.TransactionType;
import com.leovegas.wallet.exception.BalanceInsufficientException;
import com.leovegas.wallet.exception.NonUniqueTransactionException;
import com.leovegas.wallet.mapper.PaymentMapper;
import com.leovegas.wallet.service.PaymentService;
import com.leovegas.wallet.service.TransactionService;
import com.leovegas.wallet.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * @author volkanozturk
 */
@Service
public class PaymentServiceImp implements PaymentService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImp.class);
	private final WalletService walletService;
	private final TransactionService transactionService;
	private final PaymentMapper paymentMapper;

	public PaymentServiceImp(WalletService walletService, TransactionService transactionService, PaymentMapper paymentMapper) {
		this.walletService = walletService;
		this.transactionService = transactionService;
		this.paymentMapper = paymentMapper;
	}

	@Override
	@Transactional
	public PaymentDto debit(String playerId, String transactionId, BigDecimal amount) {
		return this.paymentProcess(TransactionType.DEBIT, playerId, UUID.fromString(transactionId), amount);
	}

	@Override
	@Transactional
	public PaymentDto credit(String playerId, String transactionId, BigDecimal amount) {
		return this.paymentProcess(TransactionType.CREDIT, playerId, UUID.fromString(transactionId), amount);

	}

	private PaymentDto paymentProcess(TransactionType transactionType, String playerId, UUID transactionId, BigDecimal amount) {
		Wallet wallet = walletService.getWalletLockByPlayerId(playerId);
		validateTransactionId(transactionId);

		Transaction transaction = new Transaction();
		transaction.setTransactionId(transactionId);
		transaction.setAmount(amount);

		BigDecimal updatedBalance;
		if (TransactionType.DEBIT.equals(transactionType)) {
			transaction.setTransactionType(transactionType);
			updatedBalance = wallet.getBalance().subtract(amount);
			this.checkDebitBalance(updatedBalance);
			wallet.setBalance(updatedBalance);
			wallet.setLastUpdatedAt(new Date());
			transaction.setWallet(wallet);
			this.saveWalletAndTransaction(wallet, transaction);
			return paymentMapper.toDto(wallet);
		} else {
			transaction.setTransactionType(transactionType);
			updatedBalance = wallet.getBalance().add(amount);
			wallet.setBalance(updatedBalance);
			transaction.setWallet(wallet);
			this.saveWalletAndTransaction(wallet, transaction);
			return paymentMapper.toDto(wallet);
		}
	}

	private void validateTransactionId(UUID transactionId) {
		Optional<Transaction> existingTransactionOptional = transactionService.findByTransactionId(transactionId);
		if (existingTransactionOptional.isPresent()) {
			LOGGER.error("Transaction id is not unique " + transactionId.toString());
			throw new NonUniqueTransactionException("Invalid transaction: " + transactionId);
		}
	}

	private void checkDebitBalance(BigDecimal updatedBalance) {
		if (updatedBalance.compareTo(BigDecimal.ZERO) < 0) {
			throw new BalanceInsufficientException("Transaction failed due to insufficient funds");
		}
	}

	private void saveWalletAndTransaction(Wallet wallet, Transaction transaction) {
		walletService.save(wallet);
		LOGGER.info("Wallet is successfully completed : " + transaction.getTransactionId());
		transactionService.save(transaction);
		LOGGER.info("Transaction is successfully completed : " + transaction.getTransactionId());
	}

}
