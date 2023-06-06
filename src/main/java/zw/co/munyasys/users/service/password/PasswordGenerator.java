package zw.co.munyasys.users.service.password;

import lombok.val;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PasswordGenerator {

    public String generate() {
        val uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "").toUpperCase().substring(0, 7);
    }

}
