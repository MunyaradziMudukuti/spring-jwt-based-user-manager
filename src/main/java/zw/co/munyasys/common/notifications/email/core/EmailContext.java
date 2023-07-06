package zw.co.munyasys.common.notifications.email.core;


import zw.co.munyasys.common.notifications.email.dto.Attachment;
import zw.co.munyasys.common.notifications.email.dto.Body;
import zw.co.munyasys.common.notifications.email.dto.EmailRecipient;
import zw.co.munyasys.common.notifications.email.dto.Subject;

import java.util.Set;

public interface EmailContext {

    Set<EmailRecipient> getEmailRecipients();

    Set<Attachment> getAttachments();

    String getFrom();

    Body getBody();

    Subject getSubject();


}
