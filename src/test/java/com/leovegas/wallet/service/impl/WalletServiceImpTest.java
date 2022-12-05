package com.leovegas.wallet.service.impl;

import com.leovegas.wallet.dto.WalletDto;
import com.leovegas.wallet.entity.Transaction;
import com.leovegas.wallet.entity.Wallet;
import com.leovegas.wallet.enums.TransactionType;
import com.leovegas.wallet.exception.WalletNotFoundException;
import com.leovegas.wallet.mapper.WalletMapper;
import com.leovegas.wallet.repository.WalletRepository;
import com.leovegas.wallet.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author volkanozturk
 */
@RunWith(MockitoJUnitRunner.class)
class WalletServiceImpTest {

	@Spy
	private WalletMapper walletMapper = Mappers.getMapper(WalletMapper.class);

	@Mock
	private WalletRepository walletRepository;

	@InjectMocks
	private WalletService walletService;

	private Wallet wallet;

	private Transaction transaction;


	@BeforeEach
	void init() {
		walletMapper = Mockito.mock(WalletMapper.class);
		walletRepository = Mockito.mock(WalletRepository.class);
		walletService = new WalletServiceImp(walletRepository, walletMapper);

		wallet = new Wallet();
		wallet.setId(6L);
		wallet.setPlayerId("234234234");
		wallet.setBalance(BigDecimal.valueOf(500));
		wallet.setLastUpdatedAt(new Date());
		wallet.setCreatedAt(new Date());
		wallet.setVersion(1L);
		transaction = new Transaction(UUID.fromString(UUID.randomUUID().toString()),
				TransactionType.DEBIT, BigDecimal.valueOf(90), wallet);
		wallet.setTransactions(List.of(transaction));
	}

	@Test
	void test_getWalletByPlayerId() {
		when(walletRepository.findWalletByPlayerId(any())).thenReturn(Optional.of(wallet));
		Wallet result = walletService.getWalletByPlayerId("234234234");
		assertAll(
				() -> assertNotNull(result),
				() -> assertFalse(result.getTransactions().isEmpty())
		);
	}

	@Test
	void test_getWalletLockedByPlayerId() {
		when(walletRepository.findWithLockById(any())).thenReturn(Optional.of(wallet));
		Wallet result = walletService.getWalletLockByPlayerId("234234234");
		assertAll(
				() -> assertNotNull(result),
				() -> assertFalse(result.getTransactions().isEmpty())
		);
	}

	@Test
	void test_getAllPlayersBalanceForPage() {
		List<Wallet> expected = new ArrayList<Wallet>();
		Page foundPage = new PageImpl<Wallet>(expected);
		when(walletRepository.findAll(any(Pageable.class))).thenReturn(foundPage);
		List<WalletDto> walletDtoList = walletService.getAllPlayersBalance(0, 10);
		assertAll(
				() -> assertNotNull(walletDtoList)
		);
	}

	@Test
	void test_getWalletTransactionByPlayerId() {
		when(walletRepository.findWalletByPlayerId(any())).thenReturn(Optional.of(wallet));
		List<Transaction> result = walletService.getWalletTransactionByPlayerId("234234234");
		assertAll(
				() -> assertNotNull(result),
				() -> assertEquals(result.get(0).getWallet().getTransactions().size(), 1),
				() -> assertEquals(result.get(0).getTransactionType().name(), "DEBIT")
		);
	}

	@Test
	void test_WalletNotFoundException() {
		when(walletRepository.findWalletByPlayerId(any())).thenReturn(Optional.empty());
		Exception exception = assertThrows(WalletNotFoundException.class, () -> {
			walletService.getWalletByPlayerId("3");
		});
		assertTrue("Wallet not found with Player Id : 3".contains(exception.getMessage()));

	}


	@Test
	void test_save() {
		Wallet mockWallet = new Wallet();
		wallet.setId(6L);
		wallet.setPlayerId("234234234");
		wallet.setBalance(BigDecimal.valueOf(500));
		walletService.save(wallet);
		assertAll(
				() -> assertNotNull(mockWallet)
		);
	}
}
