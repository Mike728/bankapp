package info.mike.bankapp.domain;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class User {

    private String id;
    private String login;
    private String password;
    private Account account;

    public User() {
        this.id = UUID.randomUUID().toString();
        this.account = new Account();
        generateLoginAndPassword();
    }

    public String getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Account getAccount() {
        return account;
    }

    private void generateLoginAndPassword(){
        int login = ThreadLocalRandom.current().nextInt(10000000, 99999999);
        this.login = String.valueOf(login);

        int password = ThreadLocalRandom.current().nextInt(10000000, 99999999);
        this.password = String.valueOf(password);
    }
}
