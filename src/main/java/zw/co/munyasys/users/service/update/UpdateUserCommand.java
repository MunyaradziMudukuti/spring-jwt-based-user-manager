package zw.co.munyasys.users.service.update;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record UpdateUserCommand(
        @NotBlank(message = "Email should be provided")
        @Email(message = "A valid email should be provided")
        String email,
        @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING, locale = "en_ZW", timezone = "Africa/Harare")
        LocalDate dateOfBirth,
        String phoneNumber
) {

}
