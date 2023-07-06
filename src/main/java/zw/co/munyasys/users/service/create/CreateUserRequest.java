package zw.co.munyasys.users.service.create;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
        @NotBlank(message = "Email should be provided")
        @Email(message = "A valid email should be provided")
        String email,
        @NotBlank(message = "Password should be provided")
        String password,
        @NotBlank(message = "Confirm Password should be provided")
        String confirmPassword
) {

}
