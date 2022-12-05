package com.leovegas.wallet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leovegas.wallet.dto.request.BaseRequest;
import com.leovegas.wallet.exception.WalletNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author volkanozturk
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(roles = "ADMIN")
class WalletRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private static final String BASE_URL = "/api/v1/wallets";


	@Test
	void getPlayerBalanceIsSuccess() throws Exception {
		String playerID = "8271419890";
		mockMvc.perform(get(BASE_URL + "/balance/")
						.contentType(APPLICATION_JSON_VALUE)
						.accept(APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(new BaseRequest(playerID))))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.balance").value(90.0));
	}

	@Test
	void getAllPlayerBalanceIsSuccess() throws Exception {
		mockMvc.perform(get(BASE_URL + "/all-balance")
						.contentType(APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isArray())
				.andExpect(jsonPath("$.data.[0].playerId").value("8271419890"))
				.andExpect(jsonPath("$.data.[0].balance").value(90.00));
	}

	@Test
	public void getPlayerBalanceThenThrowsWalletNotFoundException() throws Exception {
		String playerID = "123";
		mockMvc.perform(get(BASE_URL + "/balance/")
						.contentType(APPLICATION_JSON_VALUE)
						.accept(APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(new BaseRequest(playerID))))
				.andExpect(status().isOk())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof WalletNotFoundException))
				.andExpect(result -> assertEquals("Wallet not found with Player Id : " + playerID, result.getResolvedException().getMessage()));
	}


}
