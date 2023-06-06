package zw.co.munyasys.users.service.update;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import zw.co.munyasys.users.model.Gender;

import java.util.Date;

public record UpdateUserCommand(

        @NotBlank(message = "Username should be provided")
        String username,

        @NotBlank(message = "First name should be provided")
        String firstName,

        @NotBlank(message = "Last name should be provided")
        String lastName,

        Gender gender,

        @NotBlank(message = "Email should be provided")
        @Email(message = "A valid email should be provided")
        String email,
        @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING, locale = "en_ZW", timezone = "Africa/Harare")
        Date dateOfBirth,
        String phoneNumber
) {

}
