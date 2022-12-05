package com.leovegas.wallet.repository;

import com.leovegas.wallet.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * @author volkanozturk
 */

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	Optional<Transaction> findTransactionByTransactionId(UUID transactionId);

}
