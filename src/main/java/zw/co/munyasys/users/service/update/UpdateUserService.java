package zw.co.munyasys.users.service.update;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.co.munyasys.common.exceptions.InvalidRequestException;
import zw.co.munyasys.users.dao.UserRepository;
import zw.co.munyasys.users.model.User;
import zw.co.munyasys.users.service.read.UserReaderService;

import java.security.Principal;


@Service
@Slf4j
@Transactional
public class UpdateUserService {

    private final UserRepository userRepository;
    private final UserReaderService userReaderService;

    public UpdateUserService(UserRepository userRepository, UserReaderService userReaderService) {
        this.userRepository = userRepository;
        this.userReaderService = userReaderService;
    }

    public User updateMyAccount(Principal loggedInUser, UpdateUserCommand updateUserCommand) {

        String username = loggedInUser.getName();

        User user = userReaderService.findUserByUsernameOrEmail(username, null);

        boolean existsByUsername = userRepository.existsByUsernameAndIdIsNot(updateUserCommand.email(), user.getId());

        if (existsByUsername) {
            throw new InvalidRequestException("Email is already being used by another account");
        }

        user.setEmail(updateUserCommand.email());
        user.setUsername(updateUserCommand.email());

        return userRepository.save(user);
    }
}
