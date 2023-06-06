package zw.co.munyasys.users.service.update;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.co.munyasys.common.exceptions.InvalidRequestException;
import zw.co.munyasys.users.dao.UserRepository;
import zw.co.munyasys.users.dto.UserDto;
import zw.co.munyasys.users.mapper.UserMapper;
import zw.co.munyasys.users.model.User;
import zw.co.munyasys.users.service.read.UserReaderService;

import java.security.Principal;


@Service
@Slf4j
@Transactional
public class UpdateUserService {

    private final UserRepository userRepository;
    private final UserReaderService userReaderService;
    private final UserMapper userMapper;

    public UpdateUserService(UserRepository userRepository, UserReaderService userReaderService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userReaderService = userReaderService;
        this.userMapper = userMapper;
    }

    public UserDto updateMyAccount(Principal loggedInUser, UpdateUserCommand updateUserCommand) {

        String username = loggedInUser.getName();

        User user = userReaderService.findUserByUsernameOrEmail(username, null);

        boolean existsByEmail = userRepository.existsByEmail(updateUserCommand.email());

        boolean existsByUsername = userRepository.existsByUsername(username);


        if (!user.getEmail().equals(updateUserCommand.email()) && existsByEmail) {
            throw new InvalidRequestException("Email is already being used by another account");
        }

        if (!user.getUsername().equals(updateUserCommand.username()) && existsByUsername) {
            throw new InvalidRequestException("Username is already being used by another account");
        }

        user.setEmail(updateUserCommand.email());
        user.setFirstName(updateUserCommand.firstName());
        user.setUsername(updateUserCommand.username());
        user.setGender(updateUserCommand.gender());
        user.setLastName(updateUserCommand.lastName());
        user.setDateOfBirth(updateUserCommand.dateOfBirth());
        user.setMobileNumber(updateUserCommand.phoneNumber());

        return userMapper.toDto(userRepository.save(user));
    }
}
