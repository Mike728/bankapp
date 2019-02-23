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

@Service
public class TransferService {

    private Logger logger = LoggerFactory.getLogger(TransferService.class);

    private UserRepository userRepository;

    public TransferService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<BigDecimal> makeTransfer(TransferRequest transferRequest, String token){
        String targetAccountNumber = transferRequest.getTo();
        BigDecimal amount = transferRequest.getAmount();

        return userRepository.findByAccountNumber(targetAccountNumber)
            .flatMap(user -> validateThatUserHaveEnoughMoney(user, transferRequest))
            .flatMap(user -> user.getAccount().transferMoney(amount))
            .then(subtractMoney(token, amount));
    }

    private Mono<BigDecimal> subtractMoney(String token, BigDecimal amount) {
        return userRepository.findByLogin(TokenService.getEmailFromToken(token))
            .flatMap(user -> user.getAccount().subtractAmount(amount));
    }

    private Mono<User> validateThatUserHaveEnoughMoney(User user, TransferRequest transferRequest){
        BigDecimal amount = transferRequest.getAmount();

        return Mono.<User>create(sink -> {
            int comparision = user.getAccount().getBalance().compareTo(amount);
            if(comparision > -1) {
                sink.success(user);
            }
            sink.error(new NotEnoughMoneyException("You don't have enough money"));
        });
    }
}
