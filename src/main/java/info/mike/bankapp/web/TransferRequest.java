package info.mike.bankapp.web;

import java.math.BigDecimal;

public class TransferRequest {

    private BigDecimal amount;
    private String to;

    public BigDecimal getAmount() {
        return amount;
    }

    public String getTo() {
        return to;
    }
}
