package com.leovegas.wallet.repository;

import com.leovegas.wallet.entity.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author volkanozturk
 */
@DataJpaTest
@Sql(scripts = {"/ddl/ddl_h2_wallet_transaction.sql", "/data/auto_wallet_transaction_test_data.sql"})
class TransactionRepositoryTest {

	@Autowired
	private TransactionRepository transactionRepository;

	@Test
	void whenInitialized_findAll() {
		Iterable<Transaction> transactions = transactionRepository.findAll();
		assertThat(transactions).hasSize(5);
	}

	@Test
	void findTransactionByTransactionId() {
		Optional<Transaction> transaction = transactionRepository.findTransactionByTransactionId(UUID.fromString("b3af37cc-724b-11ed-a1eb-0242ac120002"));
		assertThat(transaction.orElse(null)).isNotNull();
	}

	@Test
	void whenInitialized_findInvalid() {
		Optional<Transaction> transactions = transactionRepository.findById(66L);
		assertThat(transactions.orElse(null)).isNull();
	}

}
