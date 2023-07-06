package zw.co.munyasys.common.notifications.email.core;

@FunctionalInterface
public interface EmailSenderProcessor {

    void process(EmailContext emailContext);

}
