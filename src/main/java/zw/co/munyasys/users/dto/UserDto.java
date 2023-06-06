package zw.co.munyasys.users.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import zw.co.munyasys.users.model.Gender;

import java.util.Date;
import java.util.UUID;

public record UserDto(
        UUID id,
        String username,
        String firstName,
        String lastName,
        Gender gender,
        String email,
        String mobileNumber,
        @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING, timezone = "Africa/Harare", locale = "en_ZW")
        Date dateOfBirth

) {
}
