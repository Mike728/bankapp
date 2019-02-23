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

    private Mono<User> initTestUsers(){
        return Mono.create(sink -> {
            User user = new User();
            User put = userRepository.put(user.getId(), user);
            logger.info("Generated test user with login: " + user.getLogin() + " and password: " + user.getPassword());
            sink.success(put);
        });
    }
}
