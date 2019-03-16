package info.mike.bankapp.service;

import info.mike.bankapp.domain.User;
import info.mike.bankapp.domain.UserRepository;
import info.mike.bankapp.security.TokenService;
import info.mike.bankapp.web.TransferRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Collections;

@Service
public class TransferService {

    private Logger logger = LoggerFactory.getLogger(TransferService.class);

    private UserRepository userRepository;

    public TransferService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<BigDecimal> validateTransfer(TransferRequest transferRequest, String submitterLogin, String submitterNumberAccount){
        BigDecimal amount = transferRequest.getAmount();

        return userRepository.findByLogin(submitterLogin)
            .flatMap(user -> validateThatUserHaveEnoughMoney(user, amount))
            .then(makeTransfer(transferRequest, submitterLogin, submitterNumberAccount));
    }

    private Mono<BigDecimal> makeTransfer(TransferRequest transferRequest, String submitterLogin, String submitterNumberAccount) {
        String targetAccountNumber = transferRequest.getTo();
        BigDecimal amount = transferRequest.getAmount();

        return userRepository.findByAccountNumber(targetAccountNumber)
            .flatMap(user -> user.getAccount().addMoneyToReceiver(transferRequest, submitterNumberAccount))
            .then(subtractMoneyFromSubmitter(submitterLogin, transferRequest, submitterNumberAccount));
    }

    private Mono<BigDecimal> subtractMoneyFromSubmitter(String submitterLogin, TransferRequest transferRequest, String submitterNumberAccount) {
        return userRepository.findByLogin(submitterLogin)
            .flatMap(user -> user.getAccount().subtractMoney(transferRequest, submitterNumberAccount));
    }

    private Mono<User> validateThatUserHaveEnoughMoney(User user, BigDecimal amount){
        return Mono.<User>create(sink -> {
            int comparision = user.getAccount().getBalance().compareTo(amount);
            if(comparision > -1) {
                sink.success(user);
            }
            sink.error(new NotEnoughMoneyException("You don't have enough money"));
        });
    }
}
