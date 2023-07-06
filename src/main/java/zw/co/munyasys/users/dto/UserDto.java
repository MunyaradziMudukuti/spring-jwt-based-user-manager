package zw.co.munyasys.users.dto;

import java.util.UUID;

public record UserDto(
        UUID id,
        String username,
        String email

) {
}
