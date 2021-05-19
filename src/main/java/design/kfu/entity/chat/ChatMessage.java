package design.kfu.entity.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "message")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Long senderId;
    private Long recipientId;


    @Transient
    private String chatId;

    @ManyToOne(fetch = FetchType.EAGER)
    private Chat chat;
    private String senderName;

    private String content;

    @Enumerated(EnumType.STRING)
    private MessageStatus status;

    public enum MessageStatus {
        RECEIVED, DELIVERED
    }
}

