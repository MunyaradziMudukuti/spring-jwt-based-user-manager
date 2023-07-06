package zw.co.munyasys.users.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import zw.co.munyasys.common.notifications.email.service.EmailMessageFormatter;
import zw.co.munyasys.common.notifications.email.service.EmailMessageNotifierTemplate;
import zw.co.munyasys.common.notifications.email.service.EmailSender;
import zw.co.munyasys.common.notifications.email.service.EmailUserImpl;
import zw.co.munyasys.users.model.User;

@Component
@Slf4j
public class ForgotPasswordEventListener extends EmailMessageNotifierTemplate
        implements ApplicationListener<ForgotPasswordEvent> {

    @Value("${system.name}")
    private String systemName;

    public ForgotPasswordEventListener(EmailSender emailSender) {
        super(emailSender);
    }

    @Override
    public void onApplicationEvent(ForgotPasswordEvent forgotPasswordEvent) {

        User user = forgotPasswordEvent.getUser();
        String password = forgotPasswordEvent.getPassword();

        recipients.add(new EmailUserImpl(user.getUsername(), user.getEmail()));

        subject = systemName + " : Forgot password token";

        emailMessageFormatter.addParagraph("Please use the following token to proceed in resetting " +
                "your password");

        emailMessageFormatter.addParagraph(EmailMessageFormatter.boldText("Password : ") + password);

        sendEmail();

    }
}
