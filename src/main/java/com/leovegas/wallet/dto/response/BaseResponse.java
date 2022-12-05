package com.leovegas.wallet.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Date;

/**
 * @author volkanozturk
 */
public class BaseResponse implements Serializable {

	@Schema(description = "Create Date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date createdAt;

	@Schema(description = "Last Updated Date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date lastUpdatedAt;

	public BaseResponse(Date createdAt, Date lastUpdatedAt) {
		this.createdAt = createdAt;
		this.lastUpdatedAt = lastUpdatedAt;
	}

	public BaseResponse() {
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getLastUpdatedAt() {
		return lastUpdatedAt;
	}

	public void setLastUpdatedAt(Date lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}

	@Override
	public String toString() {
		return "BaseResponse{" +
				"createdAt=" + createdAt +
				", lastUpdatedAt=" + lastUpdatedAt +
				'}';
	}
}
