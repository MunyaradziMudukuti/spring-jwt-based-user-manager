package zw.co.munyasys.users.service.create;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.co.munyasys.common.exceptions.InvalidRequestException;
import zw.co.munyasys.users.dao.UserRepository;
import zw.co.munyasys.users.dto.UserDto;
import zw.co.munyasys.users.events.NewUserEvent;
import zw.co.munyasys.users.mapper.UserMapper;
import zw.co.munyasys.users.model.User;
import zw.co.munyasys.users.service.password.PasswordGenerator;


@Service
@Transactional
public class CreateUserService {

    private final PasswordEncoder passwordEncoder;

    private final PasswordGenerator passwordGenerator;

    private final UserRepository userRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final UserMapper userMapper;

    public CreateUserService(PasswordEncoder passwordEncoder, PasswordGenerator passwordGenerator, UserRepository userRepository, ApplicationEventPublisher applicationEventPublisher, UserMapper userMapper) {
        this.passwordEncoder = passwordEncoder;
        this.passwordGenerator = passwordGenerator;
        this.userRepository = userRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.userMapper = userMapper;
    }

    public UserDto execute(CreateUserRequest createUserRequest) {

        if (userRepository.existsByEmail(createUserRequest.email())) {
            throw new InvalidRequestException("User with the same email [" + createUserRequest.email() + "] " + "already exists");
        }

        User user = userMapper.toEntity(createUserRequest);

        String password = passwordGenerator.generate();

        user.setPassword(passwordEncoder.encode(password));

        User persistedUser = userRepository.save(user);

        NewUserEvent newUserEvent = new NewUserEvent(this, persistedUser, password);

        applicationEventPublisher.publishEvent(newUserEvent);

        return userMapper.toDto(persistedUser);
    }

}