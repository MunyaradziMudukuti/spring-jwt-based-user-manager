package zw.co.munyasys.common.notifications.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import zw.co.munyasys.common.notifications.core.EmailSenderProcessor;
import zw.co.munyasys.common.notifications.dto.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Log4j2
@Component
public class EmailSenderImpl implements EmailSender {

    private final EmailSenderProcessor emailSenderProcessor;

    public EmailSenderImpl(EmailSenderProcessor emailSenderProcessor) {
        this.emailSenderProcessor = emailSenderProcessor;
    }

    @Override
    public void sendEmail(Collection<EmailUser> users, EmailMessageTransporter emailMessageTransporter) {

        SendEmailRequest sendEmailRequest = new SendEmailRequest();

        Set<EmailRecipient> recipients = users.parallelStream()
                .map(user -> {
                    EmailRecipient emailRecipient = new EmailRecipient();
                    emailRecipient.setType(RecipientType.TO);
                    emailRecipient.setEmailAddress(user.getEmail());
                    return emailRecipient;
                }).collect(toSet());

        sendEmailRequest.setEmailRecipients(recipients);

        Body body = new Body();
        body.setMessage(emailMessageTransporter.getMessage());
        sendEmailRequest.setBody(body);

        Subject subject = new Subject();
        subject.setValue(emailMessageTransporter.getSubject());
        sendEmailRequest.setSubject(subject);
        sendEmailRequest.setFrom("no-reply@hrm-system.com");
        emailSenderProcessor.process(sendEmailRequest);

        log.info("------> Email Sent");

    }

    @Override
    public void sendEmail(EmailUser user, EmailMessageTransporter emailMessageTransporter) {
        Set<EmailUser> users = new HashSet<>();
        users.add(user);
        sendEmail(users, emailMessageTransporter);
    }
}
