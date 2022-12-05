package com.leovegas.wallet.repository;

import com.leovegas.wallet.entity.Wallet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author volkanozturk
 */
@DataJpaTest
@Sql(scripts = {"/ddl/ddl_h2_wallet_transaction.sql", "/data/auto_wallet_transaction_test_data.sql"})
class WalletRepositoryTest {

	@Autowired
	private WalletRepository walletRepository;

	@Test
	void findWalletByPlayerId() {
		Optional<Wallet> walletOptional = walletRepository.findWalletByPlayerId("8271419890");
		assertThat(walletOptional.orElse(null)).isNotNull();
	}

	@Test
	void whenInitialized_findInvalid() {
		Optional<Wallet> walletOptional = walletRepository.findWalletByPlayerId("66");
		assertThat(walletOptional.orElse(null)).isNull();
	}
}
