package com.leovegas.wallet.repository;

import com.leovegas.wallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

/**
 * @author volkanozturk
 */
@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

	@Lock(LockModeType.PESSIMISTIC_FORCE_INCREMENT)
	@Query("SELECT w FROM Wallet w WHERE w.playerId = ?1")
	Optional<Wallet> findWithLockById(String playerId);

	Optional<Wallet> findWalletByPlayerId(String playerId);



}
