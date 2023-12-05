package zw.co.munyasys.users.service.read;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import zw.co.munyasys.common.exceptions.ResourceNotFoundException;
import zw.co.munyasys.users.dao.UserRepository;
import zw.co.munyasys.users.model.User;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Service
public class UserReaderService {

    private final UserRepository userRepository;

    public UserReaderService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByUsernameOrEmail(String username, String email) {

        if (nonNull(username)) {
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException(String.format("User with username %s was not found", username)));
        } else {
            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException(String.format("User with email %s was not found", email)));
        }
    }

    public User findById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User record was not found"));
    }

    public User getMyAccount(Principal principal) {
        String username = principal.getName();
        return userRepository.findByUsername(username)
                .map(userMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with username %s was not found", username)));
    }

    public Optional<User> findActiveUserByUsername(String username) {
        return userRepository.findByUsernameAndEnabledIsTrue(username);
    }

    public User getLoggedInUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsernameAndEnabledIsTrue(username)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with username %s not found", username)));
    }
}
