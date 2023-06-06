package zw.co.munyasys.common.notifications.service;

import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.HashSet;

@Slf4j
public abstract class EmailMessageNotifierTemplate {

    protected final Collection<EmailUser> recipients = new HashSet<>();
    protected final EmailMessageFormatter emailMessageFormatter = new EmailMessageFormatter();
    private final EmailSender emailSender;
    protected String subject;

    public EmailMessageNotifierTemplate(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    protected void sendEmail() {

        String message = emailMessageFormatter.buildMessage();

        recipients.parallelStream().forEach(user -> {
            EmailMessageTransporter emailMessageTransporter = EmailMessageTransporter.builder()
                    .message(String.format(message, user.getUsername()))
                    .subject(subject)
                    .build();
            emailSender.sendEmail(user, emailMessageTransporter);
        });

        clearRecipients();
        emailMessageFormatter.clearFields();

    }

    private void clearRecipients() {
        recipients.clear();
    }


}
