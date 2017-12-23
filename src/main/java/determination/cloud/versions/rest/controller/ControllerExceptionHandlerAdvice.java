/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.versions.rest.controller;

import determination.cloud.versions.rest.dto.ErrorResponseDTO;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import utils.PojoBuilder;

/**
 * The common exception handler for the controllers.
 *
 * @author Rick Rupp
 */
@ControllerAdvice
class ControllerExceptionHandlerAdvice {

	private final Logger log = LoggerFactory.getLogger(ControllerExceptionHandlerAdvice.class);

	/**
	 * Catch type mismatch for method parameters.
	 *
	 * @param e the intercepted exception
	 * @return an error response
	 */
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ErrorResponseDTO handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException e) {
		return PojoBuilder.of(ErrorResponseDTO::new)
						  .with(ErrorResponseDTO::setHttpStatus, HttpStatus.BAD_REQUEST)
						  .with(ErrorResponseDTO::setError, String.format("Type error for the '%s' argument", e.getName()))
						  .with(ErrorResponseDTO::setMessage, ExceptionUtils.getRootCauseMessage(e))
						  .build();
	}

	/**
	 * Catch type mismatch for method parameteres.
	 *
	 * @param e the intercepted exception
	 * @throws HttpMessageNotReadableException the error being caught is simply re-thrown after logging it.
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public void handleMessageNotReadableException(final HttpMessageNotReadableException e) {
		log.error("Missing request body.", e);
		// re-throw it because Spring has better diagnostics
		throw e;
	}

	/**
	 * Safety net to handle any other uncaught exceptions not accounted for here
	 *
	 * @param e the intercepted exception
	 * @throws HttpMediaTypeNotAcceptableException the error being caught is simply re-thrown after logging it.
	 */
	@ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
	public void handleHttpMediaTypeNotAcceptableException(final HttpMediaTypeNotAcceptableException e)
			throws HttpMediaTypeNotAcceptableException {
		log.error("Bad media type", e);
		// there really isn't anything you can do here because the media type isn't supported so let Spring manage it
		throw e;
	}

	/**
	 * Safety net to handle any other uncaught exceptions not accounted for here
	 *
	 * @param e the intercepted exception
	 * @return an error response
	 */
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ErrorResponseDTO handleUncaughtException(final Exception e) {
		log.error("error processing request", e);
		final String error = "Unexpected server error.";
		return PojoBuilder.of(ErrorResponseDTO::new)
						  .with(ErrorResponseDTO::setHttpStatus, HttpStatus.INTERNAL_SERVER_ERROR)
						  .with(ErrorResponseDTO::setError, error)
						  .with(ErrorResponseDTO::setMessage, ExceptionUtils.getRootCauseMessage(e))
						  .build();
	}

}
