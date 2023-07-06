package zw.co.munyasys.users.events;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import zw.co.munyasys.common.notifications.email.service.EmailMessageNotifierTemplate;
import zw.co.munyasys.common.notifications.email.service.EmailSender;
import zw.co.munyasys.common.notifications.email.service.EmailUserImpl;
import zw.co.munyasys.users.model.User;

import java.util.HashMap;

@Component
@Slf4j
public class NewUserEventEmailSendingListener extends EmailMessageNotifierTemplate
        implements ApplicationListener<NewUserEvent> {

    @Value("${system.name}")
    private String systemName;

    public NewUserEventEmailSendingListener(EmailSender emailSender) {
        super(emailSender);
    }

    @Override
    @Async
    public void onApplicationEvent(NewUserEvent newUserEvent) {

        log.info("################# Reactivation Email Processing");

        User user = newUserEvent.getUser();

        subject = "User account for " + systemName + " created successfully";

        recipients.add(new EmailUserImpl(user.getUsername(), user.getEmail()));

        emailMessageFormatter.addParagraph("Your account was created for " + systemName + ". The " +
                "account credentials that you use to sign in are as follows:");

        val table = new HashMap<String, String>();
        table.put("username", user.getUsername());

        emailMessageFormatter.addTabularHierarchy("Account credentials", table);

        emailMessageFormatter.addParagraph("You can change the password to one that you can easily remember " +
                "when you login");

        sendEmail();

        log.info("################# Reactivation Email Sent");

    }

}
