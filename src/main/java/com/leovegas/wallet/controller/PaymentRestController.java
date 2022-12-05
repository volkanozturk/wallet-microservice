package com.leovegas.wallet.controller;

import com.leovegas.wallet.annotation.ApiLogger;
import com.leovegas.wallet.dto.PaymentDto;
import com.leovegas.wallet.dto.request.PlayerCreditRequest;
import com.leovegas.wallet.dto.request.PlayerDebitRequest;
import com.leovegas.wallet.service.PaymentService;
import com.leovegas.wallet.shared.GenericResponse;
import com.leovegas.wallet.shared.GenericResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * REST Controller for provided payment related API end-points.
 *
 * @author volkanozturk
 */

@RestController
@RequestMapping(value = "/api/v1/payments", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentRestController {
	private final PaymentService paymentService;

	public PaymentRestController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	/**
	 * REST end-point in order to create a new debit transaction
	 *
	 * @param playerDebitRequest object that is going to be created. Please, see the {@link com.leovegas.wallet.dto.request.PlayerDebitRequest} class for details.
	 * @return PaymentDto Object within GenericResponse. Please, see the {@link com.leovegas.wallet.dto.PaymentDto} class for details.
	 */
	@PutMapping("/debit")
	@Operation(summary = "Debit per player")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					description = "Successfully Debit Per Player",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentDto.class))),
			@ApiResponse(responseCode = "404", description = "No wallet Found With PlayerID"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@ApiLogger
	public GenericResponse<PaymentDto> debitPlayer(@Valid @RequestBody PlayerDebitRequest playerDebitRequest) {
		PaymentDto paymentDto = paymentService.debit(playerDebitRequest.getPlayerId(), playerDebitRequest.getTransactionId(), playerDebitRequest.getAmount());
		return GenericResponses.from(paymentDto);
	}

	/**
	 * REST end-point in order to create a new credit transaction
	 *
	 * @param playerCreditRequest object that is going to be created. Please, see the {@link com.leovegas.wallet.dto.request.PlayerCreditRequest} class for details.
	 * @return PaymentDto Object within GenericResponse. Please, see the {@link com.leovegas.wallet.dto.PaymentDto} class for details.
	 */
	@PutMapping("/credit")
	@Operation(summary = "Credit per player")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					description = "Successfully Credit Per Player",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentDto.class))),
			@ApiResponse(responseCode = "404", description = "No wallet Found With PlayerID"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@ApiLogger
	public GenericResponse<PaymentDto> creditPlayer(@Valid @RequestBody PlayerCreditRequest playerCreditRequest) {
		PaymentDto paymentDto = paymentService.credit(playerCreditRequest.getPlayerId(), playerCreditRequest.getTransactionId(), playerCreditRequest.getAmount());
		return GenericResponses.from(paymentDto);
	}
}
