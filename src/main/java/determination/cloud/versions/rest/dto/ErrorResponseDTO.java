/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.versions.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.Instant;

/**
 * The POJO used to send back an error response.
 *
 * @author Rick Rupp
 */
@SuppressWarnings("unused")
public class ErrorResponseDTO {

	private long timestamp = Instant.now().toEpochMilli();
	@JsonInclude(JsonInclude.Include.NON_EMPTY) private Integer status;
	@JsonInclude(JsonInclude.Include.NON_EMPTY) private String error;
	@JsonInclude(JsonInclude.Include.NON_EMPTY) private String message;

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(final long timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(final int status) {
		this.status = status;
	}

	public void setHttpStatus(final HttpStatus status) {
		this.status = status.value();
	}

	public String getError() {
		return error;
	}

	public void setError(final String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

}
