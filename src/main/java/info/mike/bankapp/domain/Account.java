package info.mike.bankapp.domain;

import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

public class Account {

    private BigDecimal balance;
    private History history;
    private String number;
    private LocalDateTime updateDate;

    public Account() {
        this.balance = BigDecimal.TEN;
        this.history = new History();
        this.number = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 9999));
        this.updateDate = LocalDateTime.now();
    }

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

    public Mono<BigDecimal> transferMoney(BigDecimal amount){
        this.balance = balance.add(amount);
        return Mono.just(balance);
    }

    public Mono<BigDecimal> subtractAmount(BigDecimal amount){
        this.balance = balance.subtract(amount);
        return Mono.just(balance);
    }
}
