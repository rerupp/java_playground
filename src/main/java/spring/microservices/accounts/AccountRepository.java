package spring.microservices.accounts;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

@SuppressWarnings("JavaDoc")
public interface AccountRepository extends Repository<Account, Long> {
    
    Account findByAccount(String account);
    
    List<Account> findByOwnerContainingIgnoreCase(String partialName);
    
    @Query("SELECT count(*) from Account")
    int countAccounts();
}
