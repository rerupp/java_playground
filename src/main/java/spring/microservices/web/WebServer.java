package spring.microservices.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(useDefaultFilters =  false)
@SuppressWarnings("JavaDoc")
public class WebServer {

    public static void main(final String... args) {
        System.setProperty("spring.config.name", "web-server");
        SpringApplication.run(WebServer.class, args);
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public WebAccountsService webAccountsService() {
        return new WebAccountsService("http://ACCOUNTS-SERVICE", restTemplate());
    }

    @Bean
    public WebAccountsController webAccountsController() {
        return new WebAccountsController(webAccountsService());
    }

}
