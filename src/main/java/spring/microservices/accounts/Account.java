package spring.microservices.accounts;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@SuppressWarnings({"JavaDoc", "unused"})
@Entity
@Table(name = Account.TABLE)
public class Account implements Serializable {

    private static final long serialVersionUID = 451089507340530419L;
    private static final AtomicLong idGenerator = new AtomicLong(1);
    public static final String TABLE = "ACCOUNT";

    @Id
    private Long id;

    private String owner;
    private String account;
    private BigDecimal balance;

    protected static Long nextId() {
        return idGenerator.incrementAndGet();
    }
    
    // JPA will use this
    protected Account() {
        balance = BigDecimal.ZERO;
    }
    
    public Account(final String account, final String owner) {
        id = Account.idGenerator.incrementAndGet();
        this.owner = owner;
        this.account = account;
        balance = BigDecimal.ZERO;
    }

    public Long getId() {
        return id;
    }

    protected void setId(final Long id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    protected void setOwner(final String owner) {
        this.owner = owner;
    }

    public String getAccount() {
        return account;
    }

    protected void setAccount(final String account) {
        this.account = account;
    }

    public BigDecimal getBalance() {
        return balance.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    public void deposit(final BigDecimal amount) {
        balance = balance.add(amount);
    }

    public void withdraw(final BigDecimal amount) {
        balance = balance.subtract(amount);
    }
}
