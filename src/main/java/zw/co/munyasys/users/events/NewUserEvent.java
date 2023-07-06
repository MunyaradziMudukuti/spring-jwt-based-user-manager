package zw.co.munyasys.users.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import zw.co.munyasys.users.model.User;

@Getter
public class NewUserEvent extends ApplicationEvent {

    private final User user;

    public NewUserEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
}
