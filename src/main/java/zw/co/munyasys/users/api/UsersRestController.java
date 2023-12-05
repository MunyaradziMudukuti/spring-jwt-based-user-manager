package zw.co.munyasys.users.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zw.co.munyasys.users.model.User;
import zw.co.munyasys.users.service.create.CreateUserRequest;
import zw.co.munyasys.users.service.create.CreateUserService;
import zw.co.munyasys.users.service.read.UserReaderService;
import zw.co.munyasys.users.service.update.UpdateUserCommand;
import zw.co.munyasys.users.service.update.UpdateUserService;

import java.security.Principal;

@RestController
@Api(tags = "Users")
public class UsersRestController {

    private final CreateUserService createUserService;

    private final UserReaderService userReaderService;

    private final UpdateUserService updateUserService;

    public UsersRestController(CreateUserService createUserService, UserReaderService userReaderService,
                               UpdateUserService updateUserService) {
        this.createUserService = createUserService;
        this.userReaderService = userReaderService;
        this.updateUserService = updateUserService;
    }

    @PostMapping("/v1/users/sign-up")
    @ApiOperation("Create User Account/ Sign Up")
    public User createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        return createUserService.execute(createUserRequest);
    }

    @PostMapping("/v1/users/my-account")
    @ApiOperation("Update My User Account")
    public User updateMyUserAccount(@RequestBody @Valid UpdateUserCommand updateUserCommand, Principal principal) {
        return updateUserService.updateMyAccount(principal, updateUserCommand);
    }

    @GetMapping("/v1/users/my-account")
    @ApiOperation("Get My Account")
    public User getOneById(Principal principal) {
        return userReaderService.getMyAccount(principal);
    }


}
