package zw.co.munyasys.users.service.password;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.co.munyasys.common.exceptions.InvalidRequestException;
import zw.co.munyasys.users.dao.UserRepository;
import zw.co.munyasys.users.events.ForgotPasswordEvent;
import zw.co.munyasys.users.model.User;
import zw.co.munyasys.users.service.read.UserReaderService;

import java.security.Principal;


@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserPasswordServiceImpl implements UserPasswordService {

    private final UserReaderService userReaderService;

    private final ApplicationEventPublisher applicationEventPublisher;


    private final PasswordGenerator passwordGenerator;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;


    @Override
    public void forgotPassword(ForgotPasswordCommand forgotPasswordCommand) {

        User user = userReaderService.findUserByUsernameOrEmail(forgotPasswordCommand.username(), forgotPasswordCommand.email());

        String password = passwordGenerator.generate();

        user.setPassword(passwordEncoder.encode(password));

        User persistedUser = userRepository.save(user);

        ForgotPasswordEvent forgotPasswordEvent = new ForgotPasswordEvent(this, persistedUser, password);

        applicationEventPublisher.publishEvent(forgotPasswordEvent);

    }

    @Override
    public void changePassword(Principal loggedInUser, UpdatePasswordCommand updatePasswordCommand) {

        if (!updatePasswordCommand.password().equals(updatePasswordCommand.confirmPassword())) {
            throw new InvalidRequestException("Password and confirm password do not match");
        }

        String oldPassword = updatePasswordCommand.oldPassword();

        User user = userReaderService.findUserByUsernameOrEmail(loggedInUser.getName(), null);

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new InvalidRequestException("Old password does not match current password");
        }

        user.setPassword(passwordEncoder.encode(updatePasswordCommand.password()));

        userRepository.save(user);

    }

}
