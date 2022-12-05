package com.leovegas.wallet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leovegas.wallet.dto.request.BaseRequest;
import com.leovegas.wallet.entity.Transaction;
import com.leovegas.wallet.entity.Wallet;
import com.leovegas.wallet.enums.TransactionType;
import com.leovegas.wallet.exception.WalletNotFoundException;
import com.leovegas.wallet.service.TransactionService;
import com.leovegas.wallet.service.WalletService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author volkanozturk
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(roles = "ADMIN")
class TransactionRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private WalletService walletService;

	@Autowired
	private TransactionService transactionService;

	private static final String BASE_URL = "/api/v1/transactions/";


	@Test
	void getTransactionHistoryByPlayerIsSuccess() throws Exception {
		String playerID = "8271419890";
		Wallet wallet = walletService.getWalletByPlayerId(playerID);
		Transaction transaction = new Transaction(UUID.fromString("b3af37cc-724b-11ed-a1eb-0242ac120006"), TransactionType.DEBIT, BigDecimal.valueOf(100), wallet);
		transactionService.save(transaction);

		mockMvc.perform(get(BASE_URL + "/history")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(new BaseRequest(playerID))))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isArray());
	}

	@Test
	void whenGetTransactionHistoryByPlayerThenReturnedEmptyCollection() throws Exception {
		mockMvc.perform(get(BASE_URL + "/history")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(new BaseRequest("1201424602"))))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isEmpty());
	}

	@Test
	void whenGetTransactionHistoryByPlayerWalletNotFoundThenReturnedPlayerNotFoundException() throws Exception {
		mockMvc.perform(get(BASE_URL + "/history")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(new BaseRequest("8888"))))
				.andDo(print())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof WalletNotFoundException))
				.andExpect(result -> assertEquals("Wallet not found with Player Id : 8888",
						result.getResolvedException().getMessage()));
	}
}
