package zw.co.munyasys.common.notifications.email.service;

public class EmailUserImpl implements EmailUser {

    private final String username;

    private final String email;

    public EmailUserImpl(String username, String email) {
        this.username = username;
        this.email = email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getEmail() {
        return email;
    }
}
