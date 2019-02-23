package info.mike.bankapp.domain;

public class User {

    private String id;
    private String login;
    private String password;
    private Account account;

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
}
