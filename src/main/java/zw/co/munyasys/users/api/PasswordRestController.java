package zw.co.munyasys.users.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zw.co.munyasys.users.service.password.ForgotPasswordCommand;
import zw.co.munyasys.users.service.password.UpdatePasswordCommand;
import zw.co.munyasys.users.service.password.UserPasswordServiceImpl;

import java.security.Principal;

@RestController
@Api(tags = "Password Management")
public class PasswordRestController {

    private final UserPasswordServiceImpl userPasswordService;

    public PasswordRestController(UserPasswordServiceImpl userPasswordService) {
        this.userPasswordService = userPasswordService;
    }

    @PostMapping("/v1/users/forgot-password")
    @ApiOperation("Forgot Password")
    public void forgotPassword(@RequestBody @Valid ForgotPasswordCommand forgotPasswordCommand) {
        userPasswordService.forgotPassword(forgotPasswordCommand);
    }

    @PostMapping("/v1/users/update-password")
    @ApiOperation("Update Password")
    public void forgotPassword(@RequestBody @Valid UpdatePasswordCommand updatePasswordCommand, Principal principal) {
        userPasswordService.changePassword(principal, updatePasswordCommand);
    }

}
