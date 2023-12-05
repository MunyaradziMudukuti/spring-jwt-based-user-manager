package zw.co.munyasys.users.service.create;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.co.munyasys.common.exceptions.DuplicateResourceException;
import zw.co.munyasys.users.dao.UserRepository;
import zw.co.munyasys.users.events.NewUserEvent;
import zw.co.munyasys.users.model.User;
import zw.co.munyasys.users.service.password.PasswordGenerator;

import java.util.Objects;


@Service
@Transactional
public class CreateUserService {

    private final PasswordEncoder passwordEncoder;

    private final PasswordGenerator passwordGenerator;

    private final UserRepository userRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    public CreateUserService(PasswordEncoder passwordEncoder, PasswordGenerator passwordGenerator, UserRepository userRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.passwordEncoder = passwordEncoder;
        this.passwordGenerator = passwordGenerator;
        this.userRepository = userRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public User execute(CreateUserRequest createUserRequest) {

        if (!Objects.equals(createUserRequest.password(), createUserRequest.confirmPassword())) {
            throw new DuplicateResourceException("Password and confirm password does not match");
        }

        if (userRepository.existsByEmail(createUserRequest.email())) {
            throw new DuplicateResourceException("User with the same email [" + createUserRequest.email() + "] " + "already exists");
        }

        User user = User.builder()
                .username(createUserRequest.email())
                .email(createUserRequest.email())
                .build();

        String password = createUserRequest.password();

        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);

        User persistedUser = userRepository.save(user);

        NewUserEvent newUserEvent = new NewUserEvent(this, persistedUser);

        applicationEventPublisher.publishEvent(newUserEvent);

        return persistedUser;
    }

}
