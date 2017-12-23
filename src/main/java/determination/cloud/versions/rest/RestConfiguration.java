/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package determination.cloud.versions.rest;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * The versions example REST core configuration.
 *
 * @author Rick Rupp
 */
@SuppressWarnings({"JavaDoc", "WeakerAccess"})
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = RestConfiguration.CONTROLLER_PACKAGE)
public class RestConfiguration extends WebMvcConfigurerAdapter {

	static final String CONTROLLER_PACKAGE = "determination.cloud.versions.rest.controller";

	private static final String CURRENT_VERSION = "vnd.tr.idt.v2+json";
	private static final String PREVIOUS_VERSION = "vnd.tr.idt.v1+json";

	public static final MediaType CURRENT_VERSION_MEDIA_TYPE = new MediaType("application", CURRENT_VERSION);
	public static final MediaType PREVIOUS_VERSION_MEDIA_TYPE = new MediaType("application", PREVIOUS_VERSION);

	public static final String ACCEPT_HEADER = "Accept";
	public static final String ACCEPT_CURRENT_VERSION = ACCEPT_HEADER + "=application/" + CURRENT_VERSION;
	public static final String ACCEPT_PREVIOUS_VERSION = ACCEPT_HEADER + "=application/" + PREVIOUS_VERSION;

	@Override
	public void configureContentNegotiation(final ContentNegotiationConfigurer configurer) {
		configurer.defaultContentType(CURRENT_VERSION_MEDIA_TYPE);
	}

	@Override
	public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
		final MappingJackson2HttpMessageConverter messageConverterExtensions = new MappingJackson2HttpMessageConverter();
		messageConverterExtensions.setSupportedMediaTypes(Arrays.asList(
				MediaType.APPLICATION_JSON_UTF8,
				CURRENT_VERSION_MEDIA_TYPE,
				PREVIOUS_VERSION_MEDIA_TYPE
		));
		converters.add(messageConverterExtensions);
		super.configureMessageConverters(converters);
	}

}
