package zw.co.munyasys.users.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import zw.co.munyasys.users.model.User;

@Getter
public class ForgotPasswordEvent extends ApplicationEvent {

    private final String password;

    private final User user;

    public ForgotPasswordEvent(Object source, User user, String password) {
        super(source);
        this.password = password;
        this.user = user;
    }
}
