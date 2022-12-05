package com.leovegas.wallet.service.impl;

import com.leovegas.wallet.dto.PaymentDto;
import com.leovegas.wallet.entity.Transaction;
import com.leovegas.wallet.entity.Wallet;
import com.leovegas.wallet.enums.TransactionType;
import com.leovegas.wallet.exception.BalanceInsufficientException;
import com.leovegas.wallet.exception.NonUniqueTransactionException;
import com.leovegas.wallet.mapper.PaymentMapper;
import com.leovegas.wallet.service.TransactionService;
import com.leovegas.wallet.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author volkanozturk
 */
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class PaymentServiceImpTest {

	@Spy
	private PaymentMapper paymentMapper = Mappers.getMapper(PaymentMapper.class);
	@InjectMocks
	private PaymentServiceImp paymentService;

	@Mock
	private TransactionService transactionService;

	@Mock
	private WalletService walletService;

	private Wallet wallet;

	private Transaction transaction;

	@BeforeEach
	public void init() {
		wallet = new Wallet();
		wallet.setId(6L);
		wallet.setPlayerId("234234234");
		wallet.setBalance(BigDecimal.valueOf(500));
		wallet.setVersion(1L);
		wallet.setLastUpdatedAt(new Date());
		wallet.setCreatedAt(new Date());

		transaction = new Transaction(UUID.fromString(UUID.randomUUID().toString()),
				TransactionType.DEBIT, BigDecimal.valueOf(90), wallet);

	}

	@Test
	void whenDebitPayThenReturnIsCorrect() {
		when(walletService.getWalletLockByPlayerId(any())).thenReturn(wallet);
		PaymentDto result = paymentService.debit("234234234", UUID.randomUUID().toString(), BigDecimal.valueOf(100));
		assertAll(
				() -> assertEquals(new BigDecimal(400), result.getBalance())
		);
	}

	@Test
	void whenCreditPayThenReturnIsCorrect() {
		when(walletService.getWalletLockByPlayerId(any())).thenReturn(wallet);
		PaymentDto result = paymentService.credit("234234234", UUID.randomUUID().toString(), BigDecimal.valueOf(100));
		assertAll(
				() -> assertEquals(new BigDecimal(600), result.getBalance())
		);
	}

	@Test
	void whenPaymentTransactionIsNotUniqueThenThrowsNonUniqueTransactionException() {
		when(walletService.getWalletLockByPlayerId(any())).thenReturn(wallet);
		when(transactionService.findByTransactionId(any())).thenReturn(Optional.of(transaction));
		assertThrows(NonUniqueTransactionException.class, () -> paymentService.debit("234234234", UUID.randomUUID().toString(), BigDecimal.valueOf(100)));

	}

	@Test
	void whenPaymentTransactionInCorrectBalanceThenThrowsBalanceInsufficientException() {
		when(walletService.getWalletLockByPlayerId(any())).thenReturn(wallet);
		when(transactionService.findByTransactionId(any())).thenReturn(Optional.empty());
		assertThrows(BalanceInsufficientException.class, () -> paymentService.debit("234234234", UUID.randomUUID().toString(), BigDecimal.valueOf(550L)));

	}


}
