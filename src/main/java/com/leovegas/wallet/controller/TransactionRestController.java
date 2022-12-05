package com.leovegas.wallet.controller;

import com.leovegas.wallet.dto.TransactionDto;
import com.leovegas.wallet.dto.request.BaseRequest;
import com.leovegas.wallet.entity.Transaction;
import com.leovegas.wallet.mapper.TransactionMapper;
import com.leovegas.wallet.service.WalletService;
import com.leovegas.wallet.shared.GenericResponse;
import com.leovegas.wallet.shared.GenericResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * REST Controller for provided transaction related API end-points.
 *
 * @author volkanozturk
 */
@RestController
@RequestMapping(value = "/api/v1/transactions", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionRestController {

	private final WalletService walletService;

	private final TransactionMapper transactionMapper;

	public TransactionRestController(WalletService walletService, TransactionMapper transactionMapper) {
		this.walletService = walletService;
		this.transactionMapper = transactionMapper;
	}

	/**
	 * REST end-point in order to retrieve a specific transaction object by player ID.
	 * Details related to API specs can be found in the API Documentation which can be reached as described in README file.
	 *
	 * @param baseRequest object that is going to be created. Please, see the {@link com.leovegas.wallet.dto.request.BaseRequest} class for details.
	 * @return TransactionDto Object within GenericResponse. Please, see the {@link com.leovegas.wallet.dto.TransactionDto} class for details.
	 */
	@Operation(summary = "Transaction history per player")
	@GetMapping("/history")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully Get Transaction By PlayerID"),
			@ApiResponse(responseCode = "404", description = "No Transaction Found With PlayerID"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	public GenericResponse<List<TransactionDto>> getTransactionHistory(@Valid @RequestBody BaseRequest baseRequest) {
		List<Transaction> transactionList = walletService.getWalletTransactionByPlayerId(baseRequest.getPlayerId());
		List<TransactionDto> transactionDtos = transactionMapper.toDTOs(transactionList);
		return GenericResponses.from(transactionDtos);
	}
}
