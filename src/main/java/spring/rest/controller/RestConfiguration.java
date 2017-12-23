/*
 * Copyright (c) 2016 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package spring.rest.controller;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SuppressWarnings("JavaDoc")
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "spring.rest.controller.endpoints")
public class RestConfiguration {

}
