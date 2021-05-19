package design.kfu.service.chat;

import design.kfu.entity.chat.Chat;
import design.kfu.entity.chat.ChatMessage;
import design.kfu.repository.chat.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageService {

    @Autowired
    private MessageRepository messageRepository;

    public ChatMessage save(ChatMessage chatMessage) {
        return messageRepository.save(chatMessage);
    }

    public List<ChatMessage> getMessages(Chat chat) {
        return messageRepository.findByChatId(chat.getId());
    }
}
