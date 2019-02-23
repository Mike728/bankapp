package info.mike.bankapp.router;

import info.mike.bankapp.domain.User;
import info.mike.bankapp.domain.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public Mono<User> getUserByLogin(@RequestParam("login") String login){
        return userRepository.findByLogin(login);
    }
}
