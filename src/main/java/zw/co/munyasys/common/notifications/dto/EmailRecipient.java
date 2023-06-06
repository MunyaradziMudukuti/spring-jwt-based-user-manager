package zw.co.munyasys.common.notifications.dto;

import lombok.Data;

@Data
public final class EmailRecipient {

    private RecipientType type;

    private String emailAddress;

}
