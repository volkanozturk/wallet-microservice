package com.leovegas.wallet.controller;

import com.leovegas.wallet.dto.WalletDto;
import com.leovegas.wallet.dto.request.BaseRequest;
import com.leovegas.wallet.entity.Wallet;
import com.leovegas.wallet.mapper.WalletMapper;
import com.leovegas.wallet.service.WalletService;
import com.leovegas.wallet.shared.GenericResponse;
import com.leovegas.wallet.shared.GenericResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * REST Controller for provided wallet related API end-points.
 *
 * @author volkanozturk
 */
@RestController
@RequestMapping(value = "/api/v1/wallets", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class WalletRestController {

	private final WalletService walletService;

	private final WalletMapper walletMapper;

	public WalletRestController(WalletService walletService, WalletMapper walletMapper) {
		this.walletService = walletService;
		this.walletMapper = walletMapper;
	}

	/**
	 * REST end-point in order to retrieve a specific wallet object by player ID.
	 * Details related to API specs can be found in the API Documentation which can be reached as described in README file.
	 *
	 * @param baseRequest object that is going to be created. Please, see the {@link com.leovegas.wallet.dto.request.BaseRequest} class for details.
	 * @return WalletDto Object within GenericResponse. Please, see the {@link com.leovegas.wallet.dto.WalletDto} class for details.
	 */
	@GetMapping("/balance")
	@Operation(summary = "Get Balance By Player")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully Get Wallet By PlayerID"),
			@ApiResponse(responseCode = "404", description = "No Wallet Found With PlayerID"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	public GenericResponse<WalletDto> getBalance(@Valid @RequestBody BaseRequest baseRequest) {
		Wallet wallet = walletService.getWalletByPlayerId(baseRequest.getPlayerId());
		WalletDto walletDto = walletMapper.toDto(wallet);
		return GenericResponses.from(walletDto);
	}

	/**
	 * REST end-point in order to get all wallet objects.
	 * Details related to API specs can be found in the API Documentation which can be reached as described in README file.
	 *
	 * @param page is the determines the starting point.
	 * @param size is the determines the number of rows to be included.
	 * @return WalletDto Object within GenericResponse. Please, see the {@link com.leovegas.wallet.dto.WalletDto} class for details.
	 */
	@GetMapping("/all-balance")
	@Operation(summary = "Get All Balance Details")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully Get All Wallet"),
			@ApiResponse(responseCode = "404", description = "No Wallet Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	public GenericResponse<List<WalletDto>> getAllBalance(@RequestParam(required = false, defaultValue = "0") int page,
														  @RequestParam(required = false, defaultValue = "10") int size) {
		List<WalletDto> allPlayersBalance = walletService.getAllPlayersBalance(page, size);
		return GenericResponses.from(allPlayersBalance);
	}


}
