package spring.microservices.accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@EnableAutoConfiguration
@EnableDiscoveryClient
@Import(AccountsConfiguration.class)
@SuppressWarnings("JavaDoc")
public class AccountsServer {

    public static void main(final String... args) {
        System.setProperty("spring.config.name", "accounts-server");
        SpringApplication.run(AccountsServer.class, args);
    }
}
