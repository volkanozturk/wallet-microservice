package com.leovegas.wallet.dto.request;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
/**
 * @author volkanozturk
 */
public class BaseRequest implements Serializable {

	@NotBlank(message = "Player id is required")
	private String playerId;

	public BaseRequest(String playerId) {
		this.playerId = playerId;
	}

	public BaseRequest() {
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
}
