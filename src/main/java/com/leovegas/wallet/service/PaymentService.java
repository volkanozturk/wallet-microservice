package com.leovegas.wallet.service;

import com.leovegas.wallet.dto.PaymentDto;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author volkanozturk
 */
public interface PaymentService {

	PaymentDto debit(String playerId, String transactionId, BigDecimal amount);

	PaymentDto credit(String playerId, String transactionId, BigDecimal amount);


}
