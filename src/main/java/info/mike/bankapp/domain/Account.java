package info.mike.bankapp.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Account {

    private BigDecimal balance;
    private History history;
    private String number;
    private LocalDateTime updateDate;

    public BigDecimal getBalance() {
        return balance;
    }

    public History getHistory() {
        return history;
    }

    public String getNumber() {
        return number;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }
}
