package spring.microservices.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * The microservices service registration server.
 *
 * @author Rick Rupp
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServer {

    /**
     * The Eureka server runner that sets up the bootstrap of the Eureka server.
     * @param args the command line arguments passed onto the spring application.
     */
    public static void main(final String... args) {
        System.setProperty("spring.config.name", "eureka-server");
        SpringApplication.run(EurekaServer.class, args);
    }

}
