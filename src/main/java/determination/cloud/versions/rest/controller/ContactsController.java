/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.versions.rest.controller;

import determination.cloud.versions.core.model.Contact;
import determination.cloud.versions.core.services.ContactsService;
import determination.cloud.versions.rest.RestConfiguration;
import determination.cloud.versions.rest.assemblers.ContactAssembler;
import determination.cloud.versions.rest.dto.ContactDTO;
import determination.cloud.versions.rest.dto.ErrorResponseDTO;
import determination.cloud.versions.rest.dto.ModifyContactDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utils.PojoBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The example REST controller that users content negotiation to version controller methods.
 *
 * @author Rick Rupp
 */
@SuppressWarnings("JavaDoc")
@RestController
@RequestMapping(value = ContactsController.PATH,
				headers = {RestConfiguration.ACCEPT_CURRENT_VERSION, RestConfiguration.ACCEPT_PREVIOUS_VERSION})
public class ContactsController {

	static final String PATH = "/contacts";

	@Autowired
	private ContactsService contactsService;

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	ResponseEntity delete(@PathVariable final long id) {
		return new ResponseEntity<>(contactsService.delete(id) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
	}

	@RequestMapping(method = RequestMethod.POST, headers = RestConfiguration.ACCEPT_CURRENT_VERSION)
	ResponseEntity create(@RequestBody final ModifyContactDTO dto) {
		return create(dto, ContactAssembler::toModel, ContactAssembler::toDTO);
	}

	@RequestMapping(method = RequestMethod.POST, headers = RestConfiguration.ACCEPT_PREVIOUS_VERSION)
	ResponseEntity createPreviousVersion(@RequestBody final ModifyContactDTO dto) {
		return create(dto, ContactAssembler::previousToModel, ContactAssembler::previousToDTO);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}", headers = RestConfiguration.ACCEPT_CURRENT_VERSION)
	ResponseEntity update(@PathVariable final long id, @NotNull @RequestBody final ModifyContactDTO dto) {
		return update(id, dto, ContactAssembler::toModel, ContactAssembler::toDTO);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}", headers = RestConfiguration.ACCEPT_PREVIOUS_VERSION)
	ResponseEntity updatePreviousVersion(@PathVariable final long id, @NotNull @RequestBody final ModifyContactDTO dto) {
		return update(id, dto, ContactAssembler::previousToModel, ContactAssembler::previousToDTO);
	}

	@RequestMapping(method = RequestMethod.GET, headers = RestConfiguration.ACCEPT_CURRENT_VERSION)
	ResponseEntity findAll(@RequestParam(value = "firstName", required = false) final String firstName,
						   @RequestParam(value = "lastName", required = false) final String lastName) {
		return findAll(firstName, lastName, ContactAssembler::toDTO);
	}

	@RequestMapping(method = RequestMethod.GET, headers = RestConfiguration.ACCEPT_PREVIOUS_VERSION)
	ResponseEntity findAllPreviousVersion() {
		return findAll(null, null, ContactAssembler::previousToDTO);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}", headers = RestConfiguration.ACCEPT_CURRENT_VERSION)
	ResponseEntity find(@PathVariable final long id) {
		return find(id, ContactAssembler::toDTO);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}", headers = RestConfiguration.ACCEPT_PREVIOUS_VERSION)
	ResponseEntity findPreviousVersion(@PathVariable final long id) {
		return find(id, ContactAssembler::previousToDTO);
	}

	private ResponseEntity<?> create(final ModifyContactDTO dto,
									 final Function<ModifyContactDTO, Contact> toModel,
									 final Function<Contact, ContactDTO> toDto) {
		final Contact contact = contactsService.add(toModel.apply(dto));
		final HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.LOCATION, PATH + "/" + contact.getId());
		return new ResponseEntity<>(toDto.apply(contact), headers, HttpStatus.OK);

	}

	private ResponseEntity<?> update(final long id,
									 final ModifyContactDTO dto,
									 final Function<ModifyContactDTO, Contact> toModel,
									 final Function<Contact, ContactDTO> toDTO) {

		// create the contact and try to update it
		final Contact update = toModel.apply(dto);
		update.setId(id);
		final Contact result = contactsService.update(update);

		// return not found if the update didn't happen
		final ResponseEntity<?> response;
		if (null != result) {
			response = new ResponseEntity<>(toDTO.apply(result), HttpStatus.OK);
		} else {
			final HttpStatus errorStatus = HttpStatus.NOT_FOUND;
			final ErrorResponseDTO error = PojoBuilder.of(ErrorResponseDTO::new)
													  .with(ErrorResponseDTO::setHttpStatus, errorStatus)
													  .with(ErrorResponseDTO::setError, "Contact not found")
													  .with(ErrorResponseDTO::setMessage, String.format("Contact ID=%d not found", id))
													  .build();
			response = new ResponseEntity<>(error, errorStatus);
		}
		return response;
	}

	private ResponseEntity<?> findAll(final String firstName, final String lastName, final Function<Contact, ContactDTO> toDTO) {
		final List<ContactDTO> contacts = contactsService.find(firstName, lastName)
														 .stream()
														 .map(toDTO)
														 .collect(Collectors.toList());
		return new ResponseEntity<>(contacts, HttpStatus.OK);
	}

	private ResponseEntity<?> find(final long id, final Function<Contact, ContactDTO> toDTO) {
		final Contact model = contactsService.get(id);
		return new ResponseEntity<>(toDTO.apply(model), (null == model) ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}

}
