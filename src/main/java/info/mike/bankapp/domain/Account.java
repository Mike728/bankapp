package info.mike.bankapp.domain;

import info.mike.bankapp.web.TransferRequest;
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

    public Mono<BigDecimal> addMoneyToReceiver(TransferRequest transferRequest, String submitterNumberAccount){
        this.balance = balance.add(transferRequest.getAmount());
        this.history.addEntryToHistory(transferRequest, submitterNumberAccount);
        return Mono.just(balance);
    }

    public Mono<BigDecimal> subtractMoney(TransferRequest transferRequest, String submitterNumberAccount){
        this.balance = balance.subtract(transferRequest.getAmount());
        this.history.addEntryToHistory(transferRequest, submitterNumberAccount);
        return Mono.just(balance);
    }
}
