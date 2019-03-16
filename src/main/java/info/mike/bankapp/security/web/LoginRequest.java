package info.mike.bankapp.security.web;

public class LoginRequest {

    private String pin;
    private String password;

    public String getPin() {
        return pin;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
            "pin='" + pin + '\'' +
            ", password='" + password + '\'' +
            '}';
    }
}
