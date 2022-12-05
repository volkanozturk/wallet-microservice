package com.leovegas.wallet.service.impl;

import com.leovegas.wallet.entity.Transaction;
import com.leovegas.wallet.enums.TransactionType;
import com.leovegas.wallet.repository.TransactionRepository;
import com.leovegas.wallet.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author volkanozturk
 */
@RunWith(MockitoJUnitRunner.class)
class TransactionServiceImpTest {
	@Mock
	private TransactionRepository transactionRepository;
	@InjectMocks
	private TransactionService transactionService;

	@BeforeEach
	void init() {
		transactionRepository = Mockito.mock(TransactionRepository.class);
		transactionService = new TransactionServiceImp(transactionRepository);
	}

	@Test
	void whenTransactionByTransactionIdThenReturnTransactionIsSuccess() {
		UUID transactionId = UUID.randomUUID();
		Transaction mockTransaction = new Transaction(transactionId, TransactionType.DEBIT, BigDecimal.valueOf(80L), null);

		when(transactionRepository.findTransactionByTransactionId(any())).thenReturn(Optional.of(mockTransaction));
		Optional<Transaction> result = transactionService.findByTransactionId(transactionId);
		assertTrue(result.isPresent());
	}

	@Test
	public void whenTransactionIsSavedThenReturnIsSuccess() {
		UUID transactionId = UUID.randomUUID();
		Transaction mockTransaction = new Transaction(transactionId, TransactionType.DEBIT, BigDecimal.valueOf(80L), null);
		transactionService.save(mockTransaction);
		assertAll(
				() -> assertNotNull(mockTransaction)
		);
	}
}
