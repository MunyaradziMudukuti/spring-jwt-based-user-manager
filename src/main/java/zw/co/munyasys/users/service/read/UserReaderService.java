package zw.co.munyasys.users.service.read;

import org.springframework.stereotype.Service;
import zw.co.munyasys.common.exceptions.RecordNotFoundException;
import zw.co.munyasys.users.dao.UserRepository;
import zw.co.munyasys.users.dto.UserDto;
import zw.co.munyasys.users.mapper.UserMapper;
import zw.co.munyasys.users.model.User;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Service
public class UserReaderService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserReaderService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public User findUserByUsernameOrEmail(String username, String email) {

        if (nonNull(username)) {
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new RecordNotFoundException(String.format("User with username %s was not found", username)));
        } else {
            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new RecordNotFoundException(String.format("User with email %s was not found", email)));
        }
    }

    public User findById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RecordNotFoundException("User record was not found"));
    }

    public UserDto getMyAccount(Principal principal) {
        String username = principal.getName();
        return userRepository.findByUsername(username)
                .map(userMapper::toDto)
                .orElseThrow(() -> new RecordNotFoundException(String.format("User with username %s was not found", username)));
    }

    public Optional<User> findActiveUserByUsername(String username) {
        return userRepository.findByUsernameAndEnabledIsTrue(username);
    }
}
