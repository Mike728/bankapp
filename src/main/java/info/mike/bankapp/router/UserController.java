package info.mike.bankapp.router;

import info.mike.bankapp.domain.User;
import info.mike.bankapp.domain.UserRepository;
import info.mike.bankapp.security.TokenService;
import info.mike.bankapp.service.TransferService;
import info.mike.bankapp.web.TransferRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequestMapping("/user")
public class UserController {

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
        return userRepository.findByLogin(TokenService.getEmailFromToken(token));
    }

    @PostMapping("/transfer")
    public Mono<BigDecimal> makeTransfer(@RequestBody TransferRequest transferRequest, ServerWebExchange exchange){
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");

        return transferService.makeTransfer(transferRequest, token);
    }
}
