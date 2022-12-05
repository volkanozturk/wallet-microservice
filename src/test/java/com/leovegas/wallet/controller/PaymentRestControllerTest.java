package com.leovegas.wallet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leovegas.wallet.dto.request.PlayerCreditRequest;
import com.leovegas.wallet.dto.request.PlayerDebitRequest;
import com.leovegas.wallet.entity.Transaction;
import com.leovegas.wallet.entity.Wallet;
import com.leovegas.wallet.enums.TransactionType;
import com.leovegas.wallet.exception.BalanceInsufficientException;
import com.leovegas.wallet.exception.NonUniqueTransactionException;
import com.leovegas.wallet.service.TransactionService;
import com.leovegas.wallet.service.WalletService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author volkanozturk
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PaymentRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WalletService walletService;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private ObjectMapper objectMapper;

	private static final String BASE_URL = "/api/v1/payments/";


	@BeforeAll
	@Transactional
	public void dummyData() {
		Wallet wallet = walletService.getWalletByPlayerId("8271419890");
		Transaction transaction = new Transaction(UUID.fromString("b3af37cc-724b-11ed-a1eb-0242ac120066"), TransactionType.DEBIT, BigDecimal.valueOf(90), wallet);
		transactionService.save(transaction);

		Wallet wallet2 = walletService.getWalletByPlayerId("9751473310");
		Transaction transaction2 = new Transaction(UUID.fromString("b3af3b14-724b-11ed-a1eb-0242ac120044"), TransactionType.CREDIT, BigDecimal.valueOf(200), wallet2);
		transactionService.save(transaction2);
	}

	@Test
	public void whenDebitFromPlayerBalanceIsSuccess() throws Exception {
		String playerID = "8271419890";
		var request = new PlayerDebitRequest(playerID, BigDecimal.valueOf(10), "b3af37cc-724b-11ed-a1eb-0242ac120099");
		var json = objectMapper.writeValueAsString(request);
		mockMvc.perform(put(BASE_URL + "/debit")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.balance").value(80.00));
	}

	@Test
	public void creditToPlayerBalanceSuccess() throws Exception {
		String playerID = "9751473310";
		var request = new PlayerDebitRequest(playerID, BigDecimal.valueOf(40), "b3af37cc-724b-11ed-a1eb-0242ac120097");
		var json = objectMapper.writeValueAsString(request);
		mockMvc.perform(put(BASE_URL + "/credit")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.balance").value(240.00));

	}

	@Test
	public void whenDebitNoUniqueTransactionId() throws Exception {
		String playerID = "8271419890";
		var request = new PlayerDebitRequest(playerID, BigDecimal.valueOf(40), "b3af37cc-724b-11ed-a1eb-0242ac120066");
		var json = objectMapper.writeValueAsString(request);
		mockMvc.perform(put(BASE_URL + "/debit")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof NonUniqueTransactionException))
				.andExpect(result -> assertEquals("Invalid transaction: b3af37cc-724b-11ed-a1eb-0242ac120066",
						result.getResolvedException().getMessage()));
	}

	@Test
	public void whenCreditNoUniqueTransactionId() throws Exception {
		String playerID = "9751473310";
		var request = new PlayerDebitRequest(playerID, BigDecimal.valueOf(40), "b3af3b14-724b-11ed-a1eb-0242ac120044");
		var json = objectMapper.writeValueAsString(request);
		mockMvc.perform(put(BASE_URL + "/credit")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof NonUniqueTransactionException))
				.andExpect(result -> assertEquals("Invalid transaction: b3af3b14-724b-11ed-a1eb-0242ac120044",
						result.getResolvedException().getMessage()));
	}

	@Test
	public void whenDebitBalanceThrowsBalanceInsufficientException() throws Exception {
		String playerID = "8271419890";
		var request = new PlayerDebitRequest(playerID, BigDecimal.valueOf(700), UUID.randomUUID().toString());
		var json = objectMapper.writeValueAsString(request);
		mockMvc.perform(put(BASE_URL + "/debit")
						.contentType(APPLICATION_JSON_VALUE)
						.accept(APPLICATION_JSON_VALUE)
						.content(json))
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof BalanceInsufficientException))
				.andExpect(result -> assertEquals("Transaction failed due to insufficient funds", result.getResolvedException().getMessage()));
	}


	@Test
	public void whenDebitPlayerBalanceHasNegativeAmountThenReturnedMethodArgumentNotValidException() throws Exception {
		String playerID = "8271419890";
		var request = new PlayerDebitRequest(playerID, BigDecimal.valueOf(-10), UUID.randomUUID().toString());
		var json = objectMapper.writeValueAsString(request);
		mockMvc.perform(put(BASE_URL + "/debit")
						.contentType(APPLICATION_JSON_VALUE)
						.accept(APPLICATION_JSON_VALUE)
						.content(json))
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
	}


	@Test
	public void whenCreditAndDebitSameTime() throws Exception {
		String playerID = "9751473310";
		ExecutorService service = Executors.newFixedThreadPool(6);
		for (int i = 0; i < 10; i++) {
			service.submit(() -> {
				try {
					mockMvc.perform(put(BASE_URL + "/credit")
									.contentType(APPLICATION_JSON_VALUE)
									.accept(APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(new PlayerCreditRequest(playerID, BigDecimal.valueOf(10), UUID.randomUUID().toString()))))
							.andExpect(status().isOk())
							.andExpect(jsonPath("$.data.balance").value(80.00));

					mockMvc.perform(put(BASE_URL + "/debit")
									.contentType(APPLICATION_JSON_VALUE)
									.accept(APPLICATION_JSON_VALUE)
									.content(objectMapper.writeValueAsString(new PlayerDebitRequest(playerID, BigDecimal.valueOf(10), UUID.randomUUID().toString()))))
							.andExpect(status().isOk())
							.andExpect(jsonPath("$.data.balance").value(80.00));

				} catch (Exception exception) {
					assertTrue(() -> exception instanceof ObjectOptimisticLockingFailureException);
				}

			});
		}
		service.shutdown();
		if (!service.awaitTermination(1, TimeUnit.MINUTES)) {
			service.shutdown();
		}
	}


}
