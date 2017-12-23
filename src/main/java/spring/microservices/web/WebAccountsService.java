package spring.microservices.web;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import spring.microservices.common.AccountNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SuppressWarnings({"JavaDoc", "WeakerAccess"})
@Service
public class WebAccountsService {

    private final String serviceUrl;

    @LoadBalanced
    private final RestTemplate restTemplate;

    public WebAccountsService(final String serviceUrl, final RestTemplate restTemplate) {
        this.serviceUrl = Validate.notEmpty(serviceUrl, "The accounts service URL cannot be empty...");
        this.restTemplate = Objects.requireNonNull(restTemplate, "The accounts service REST template cannot be null...");
    }

    public AccountDTO findByNumber(final String accountNumber) {
        return restTemplate.getForObject(serviceUrl + "/accounts/{number}", AccountDTO.class, accountNumber);
    }

    public List<AccountDTO> byOwnerContains(final String partialName) {
        AccountDTO[] accounts;
        try {
            accounts = restTemplate.getForObject(serviceUrl + "/accounts/owner/{name}", AccountDTO[].class, partialName);
        } catch (final HttpClientErrorException ignore) {
            accounts = null;
        }
        return (ArrayUtils.isEmpty(accounts)) ? null : Arrays.asList(accounts);
    }

    public AccountDTO getByNumber(final String accountNumber) {
        final AccountDTO account = restTemplate.getForObject(serviceUrl + "/accounts/{number}", AccountDTO.class, accountNumber);
        if (null == account) {
            throw new AccountNotFoundException(accountNumber);
        }
        return account;
    }

}
