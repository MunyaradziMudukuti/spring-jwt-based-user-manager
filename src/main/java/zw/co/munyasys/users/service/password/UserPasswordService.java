package zw.co.munyasys.users.service.password;

import java.security.Principal;

interface UserPasswordService {

    void forgotPassword(ForgotPasswordCommand forgotPasswordCommand);

    void changePassword(Principal loggedInUser, UpdatePasswordCommand updatePasswordCommand);

}
