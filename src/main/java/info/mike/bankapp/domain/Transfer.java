package info.mike.bankapp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import info.mike.bankapp.web.TransferRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transfer {

    private BigDecimal amount;
    private String from;
    private String to;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime date;
    private String type;

    public Transfer(TransferRequest request, String accountNumber, OperationType type) {
        this.amount = request.getAmount();
        this.from = accountNumber;
        this.to = request.getTo();
        this.date = LocalDateTime.now();
        this.type = type.toString();
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

    public String getType() {
        return type;
    }
}
