package zw.co.munyasys.users.service.password;

import jakarta.validation.constraints.NotBlank;

public record UpdatePasswordCommand(
        @NotBlank(message = "Old password should be provided")
        String oldPassword,

        @NotBlank(message = "New Password should be provided")
        String password,

        @NotBlank(message = "Confirm new Password should be provided")
        String confirmPassword) {

}
