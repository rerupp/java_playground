package spring.microservices.accounts;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.microservices.common.AccountNotFoundException;

import java.util.List;
import java.util.Objects;

@RestController
@SuppressWarnings("JavaDoc")
class AccountsController {

    private static final String PATH_ROOT = "/accounts";
    private final AccountRepository accountRepository;

    public AccountsController(final AccountRepository accountRepository) {
        this.accountRepository = Objects.requireNonNull(accountRepository, "The AccountRepository cannot be null...");
    }

    @RequestMapping(AccountsController.PATH_ROOT + "/{account}")
    public Account byAccount(@PathVariable("account") final String account) {
        final Account accountPDO = accountRepository.findByAccount(account);
        if (null == accountPDO) {
            throw new AccountNotFoundException(account);
        }
        return accountPDO;
    }

    @RequestMapping(AccountsController.PATH_ROOT + "/owner/{partialName}")
    public List<Account> byPartialName(@PathVariable("partialName") final String partialName) {
        final List<Account> accounts = accountRepository.findByOwnerContainingIgnoreCase(partialName);
        if (CollectionUtils.isEmpty(accounts)) {
            throw new AccountNotFoundException(partialName);
        }
        return accounts;
    }
}
