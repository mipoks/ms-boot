package design.kfu.entity.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChatForm {
    private String id;
    private String chatName;
}
