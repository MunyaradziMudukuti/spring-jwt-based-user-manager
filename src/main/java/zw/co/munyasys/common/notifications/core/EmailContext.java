package zw.co.munyasys.common.notifications.core;


import zw.co.munyasys.common.notifications.dto.Attachment;
import zw.co.munyasys.common.notifications.dto.Body;
import zw.co.munyasys.common.notifications.dto.EmailRecipient;
import zw.co.munyasys.common.notifications.dto.Subject;

import java.util.Set;

public interface EmailContext {

    Set<EmailRecipient> getEmailRecipients();

    Set<Attachment> getAttachments();

    String getFrom();

    Body getBody();

    Subject getSubject();


}
