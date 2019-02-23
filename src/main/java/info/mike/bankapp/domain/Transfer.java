package info.mike.bankapp.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transfer {

    private BigDecimal amount;
    private String from;
    private String to;
    private LocalDateTime date;

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
