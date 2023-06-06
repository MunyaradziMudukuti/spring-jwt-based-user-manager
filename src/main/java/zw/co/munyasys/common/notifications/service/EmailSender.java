package zw.co.munyasys.common.notifications.service;

import java.util.Collection;

public interface EmailSender {

    void sendEmail(Collection<EmailUser> users, EmailMessageTransporter emailMessageTransporter);

    void sendEmail(EmailUser user, EmailMessageTransporter emailMessageTransporter);

}
