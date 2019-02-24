package info.mike.bankapp.domain;

import info.mike.bankapp.web.TransferRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transfer {

    private BigDecimal amount;
    private String from;
    private String to;
    private LocalDateTime date;

    public Transfer(TransferRequest request, String accountNumber) {
        this.amount = request.getAmount();
        this.from = accountNumber;
        this.to = request.getTo();
        this.date = LocalDateTime.now();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
