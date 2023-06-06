package zw.co.munyasys.common.notifications.core;

@FunctionalInterface
public interface EmailSenderProcessor {

    void process(EmailContext emailContext);

}
