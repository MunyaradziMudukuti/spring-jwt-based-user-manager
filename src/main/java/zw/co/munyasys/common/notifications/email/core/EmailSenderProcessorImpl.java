package zw.co.munyasys.common.notifications.email.core;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import zw.co.munyasys.common.notifications.email.dto.Attachment;
import zw.co.munyasys.common.notifications.email.dto.Body;
import zw.co.munyasys.common.notifications.email.dto.EmailRecipient;
import zw.co.munyasys.common.notifications.email.dto.RecipientType;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED;

@Slf4j
@Component
public class EmailSenderProcessorImpl implements EmailSenderProcessor {

    private final EmailHtmlRepresentationBuilder emailHtmlRepresentationBuilder;

    private final JavaMailSender javaMailSender;

    @Value("${email.origin.sender}")
    private InternetAddress emailOriginSender;

    @Value("${file-storage.base-path}")
    private String fileStorageBasePath;

    public EmailSenderProcessorImpl(EmailHtmlRepresentationBuilder emailHtmlRepresentationBuilder,
                                    JavaMailSender javaMailSender) {
        this.emailHtmlRepresentationBuilder = emailHtmlRepresentationBuilder;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void process(EmailContext emailContext) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,
                    MULTIPART_MODE_MIXED_RELATED,
                    UTF_8.name());

            addRecipients(mimeMessageHelper, emailContext);
            addAttachments(mimeMessageHelper, emailContext);
            addSubject(mimeMessageHelper, emailContext);
            addFrom(mimeMessageHelper);
            String htmlText = emailHtmlRepresentationBuilder.buildHtmlRepresentation(emailContext.getBody().getMessage());
            mimeMessageHelper.setText(htmlText, true);

            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            log.error("Failed to send email message due to : {}", e.getMessage());
        }

        log.info("Email sent");


    }

    private void addFrom(MimeMessageHelper mimeMessageHelper) {
        try {
            mimeMessageHelper.setFrom(emailOriginSender);
        } catch (MessagingException e) {
            log.error("Failed to add FROM : {} to the mime message due to : {}", emailOriginSender,
                    e.getMessage());
        }
    }

    private void addSubject(MimeMessageHelper mimeMessageHelper, EmailContext emailContext) {
        try {
            mimeMessageHelper.setSubject(emailContext.getSubject().getValue());
        } catch (MessagingException e) {
            log.error("Failed to add subject to the email; caused by : {}", e.getMessage());
        }
    }

    private void addAttachments(MimeMessageHelper mimeMessageHelper, EmailContext emailContext) {
        Set<Attachment> attachments = emailContext.getAttachments();
        if (isNull(attachments) || attachments.isEmpty())
            return;

        Body body = emailContext.getBody();
        StringBuilder message = new StringBuilder(body.getMessage());
        message.append("<br /><br /> <strong>Please follow the links to download the attachments</strong>").
                append("<br /><br />");
        attachments.forEach(attachment -> {

            if (!attachment.isHasLink())
                attachFile(attachment, mimeMessageHelper);
            else {
                String url =
                        fileStorageBasePath.concat("?").concat("fileName=").concat(attachment.getName());
                message.append(url)
                        .append("<br />");
            }

        });
        body.setMessage(message.toString());
    }

    private void attachFile(Attachment attachment, MimeMessageHelper mimeMessageHelper) {
        String attachmentName = attachment.getName();
        if (isNull(attachmentName) || attachmentName.trim().isEmpty())
            attachmentName = attachment.getFile().getOriginalFilename();
        try {
            mimeMessageHelper.addAttachment(attachmentName, () -> attachment.getFile().getInputStream());
        } catch (MessagingException e) {
            log.error("Failed to attach file : {}; caused by {}", attachmentName, e.getMessage());
        }
    }

    private void addRecipients(MimeMessageHelper mimeMessageHelper, EmailContext emailContext) {
        Set<EmailRecipient> recipients = emailContext.getEmailRecipients();
        if (isNull(recipients) || recipients.isEmpty()) {
            log.error("Recipients should not be null, should be provided for the email to be send");
            throw new UnsupportedOperationException("Recipients should not be null or empty, should be provided for the email to be send");
        }

        Map<RecipientType, List<EmailRecipient>> groupRecipients =
                recipients.stream().collect(Collectors.groupingBy(EmailRecipient::getType));

        List<EmailRecipient> toRecipients = groupRecipients.get(RecipientType.TO);
        if (nonNull(toRecipients))
            toRecipients.stream().map(EmailRecipient::getEmailAddress)
                    .forEach(to -> {
                        try {
                            mimeMessageHelper.addTo(to);
                        } catch (MessagingException e) {
                            log.error("Mime message exception happened in adding {} to the To list, caused by {}", to, e.getMessage());
                        }
                    });

        List<EmailRecipient> ccRecipients = groupRecipients.get(RecipientType.CC);
        if (nonNull(ccRecipients))
            ccRecipients.stream().map(EmailRecipient::getEmailAddress)
                    .forEach(cc -> {
                        try {
                            mimeMessageHelper.addCc(cc);
                        } catch (MessagingException e) {
                            log.error("Mime message exception happened in adding {} to the CC list, caused by {}", cc, e.getMessage());
                        }
                    });

        List<EmailRecipient> bccRecipients = groupRecipients.get(RecipientType.BCC);
        if (nonNull(bccRecipients))
            bccRecipients.stream().map(EmailRecipient::getEmailAddress)
                    .forEach(bcc -> {
                        try {
                            mimeMessageHelper.addBcc(bcc);
                        } catch (MessagingException e) {
                            log.error("Mime message exception happened in adding {} to the BCC list, caused by {}", bcc, e.getMessage());
                        }
                    });

    }
}
