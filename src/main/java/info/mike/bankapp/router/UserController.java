package info.mike.bankapp.router;

import info.mike.bankapp.domain.User;
import info.mike.bankapp.domain.UserRepository;
import info.mike.bankapp.security.TokenService;
import info.mike.bankapp.service.TransferService;
import info.mike.bankapp.web.TransferRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;
    private final TransferService transferService;

    public UserController(UserRepository userRepository, TransferService transferService) {
        this.userRepository = userRepository;
        this.transferService = transferService;
    }

    @GetMapping
    public Mono<User> getUserByLogin(@RequestParam("login") String login){
        return userRepository.findByLogin(login);
    }

    @GetMapping("/dashboard")
    public Mono<User> dashboard(ServerWebExchange exchange){
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        return userRepository.findByLogin(TokenService.getLoginFromToken(token));
    }

    @PostMapping("/transfer")
    public Mono<BigDecimal> makeTransfer(@RequestBody TransferRequest transferRequest, ServerWebExchange exchange){
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        String submitterLogin = TokenService.getLoginFromToken(token);

        return userRepository
            .findByLogin(submitterLogin)
            .map(user -> user.getAccount().getNumber())
            .flatMap(submitterNumberAccount -> transferService.validateTransfer(transferRequest, submitterLogin, submitterNumberAccount));
    }
}
