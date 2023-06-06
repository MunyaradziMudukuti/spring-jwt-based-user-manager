package zw.co.munyasys.users.service.password;

public record ForgotPasswordCommand(
        String email,
        String username
) {

}
