package spring.microservices.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.apache.commons.lang3.ObjectUtils;

import java.math.BigDecimal;

@SuppressWarnings("JavaDoc")
@JsonRootName("Account")
public class AccountDTO {

    private Long id;
    @JsonProperty("account")
    private String number;
    private String owner;
    private BigDecimal balance;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(final String number) {
        this.number = number;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(final String owner) {
        this.owner = owner;
    }

    public BigDecimal getBalance() {
        return ObjectUtils.defaultIfNull(balance, BigDecimal.ZERO).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    public void setBalance(final BigDecimal balance) {
        this.balance = balance;
    }

}
