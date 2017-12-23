package spring.microservices.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Random;

@SuppressWarnings({"JavaDoc", "SqlDialectInspection", "SqlNoDataSourceInspection"})
@Configuration
@EntityScan("spring.microservices.accounts")
@EnableJpaRepositories("spring.microservices.accounts")
@PropertySource("classpath:db-config.properties")
public class AccountsConfiguration {

    @Autowired
    @Bean
    public AccountsController accountsController(final AccountRepository accountRepository) {
        return new AccountsController(accountRepository);
    }

    @Bean
    public DataSource dataSource() {
        final DataSource dataSource = new EmbeddedDatabaseBuilder().addScript("classpath:db-schema.sql")
                                                                   .addScript("classpath:db-data.sql")
                                                                   .build();

        // populate the accounts with random balances
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        final List<Map<String, Object>> accounts = jdbcTemplate.queryForList("SELECT id FROM " + Account.TABLE);
        final Random balanceGenerator = new Random();
        for (final Map<String, Object> account : accounts) {
            final String id = account.get("id").toString();
            final BigDecimal balance = new BigDecimal(balanceGenerator.nextInt(10000000) / 100.0).setScale(2, BigDecimal.ROUND_HALF_UP);
            jdbcTemplate.update("UPDATE " + Account.TABLE + " SET balance = ? WHERE id = ?", balance, id);
        }
        
        return dataSource;
    }
}
