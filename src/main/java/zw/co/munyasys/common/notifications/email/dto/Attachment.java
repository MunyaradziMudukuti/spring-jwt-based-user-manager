package zw.co.munyasys.common.notifications.email.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class Attachment {

    private String name;

    private MultipartFile file;

    private boolean hasLink;

}
