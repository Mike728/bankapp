package info.mike.bankapp.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {

    private Logger logger = LoggerFactory.getLogger(UserRepository.class);

    private final Map<String, User> userRepository;
    private final int TEST_USERS = 2;

    public UserRepository() {
        this.userRepository = new HashMap<>();
        initTestUsers().subscribe();
    }

    public Mono<User> findById(String id) {
        return Mono.justOrEmpty(userRepository.get(id));
    }

    public Mono<User> findByLogin(String login) {
        return Flux.fromStream(userRepository.entrySet().stream())
            .map(Map.Entry::getValue)
            .filter(user -> user.getLogin().equals(login))
            .singleOrEmpty();
    }

    public Mono<User> findByAccountNumber(String number) {
        return Flux.fromStream(userRepository.entrySet().stream())
            .map(Map.Entry::getValue)
            .filter(user -> user.getAccount().getNumber().equals(number))
            .singleOrEmpty();
    }

    private Flux<User> initTestUsers(){
        return Flux.<User, User>generate(
            User::new,
            (state, sink) -> {
                User user = new User();
                userRepository.put(user.getId(), user);
                sink.next(user);

                logger.info("Generated test user with login: " + user.getLogin() + " password: " + user.getPassword()
                    + " and account number: " + user.getAccount().getNumber());

                if (userRepository.size() == TEST_USERS)
                    sink.complete();

                return user;
            });
    }
}
