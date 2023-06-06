package zw.co.munyasys.users.service.create;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import zw.co.munyasys.users.model.Gender;

import java.util.Date;

public record CreateUserRequest(

        @NotBlank(message = "First name should be provided")
        String firstName,

        @NotBlank(message = "Last name should be provided")
        String lastName,

        @NotNull(message = "Gender should be provided")
        Gender gender,

        @NotBlank(message = "Email should be provided")
        @Email(message = "A valid email should be provided")
        String email,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "en_ZW", timezone = "Africa/Harare")
        Date dateOfBirth,
        String mobileNumber,
        @NotBlank(message = "Password should be provided")
        String password
) {

}
