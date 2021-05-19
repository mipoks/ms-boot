package design.kfu.helper.converter;

import design.kfu.entity.chat.Chat;
import design.kfu.repository.chat.ChatRepository;
import design.kfu.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class StringToChatConverter implements Converter<String, Chat> {

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public Chat convert(String id) {
        log.info("We used converter!");
        if (id.equals("") || id.equals("NONE")) {
            return null;
        }
        String realId = chatService.getRealChatId(id);
        Optional<Chat> optional = chatRepository.findById(realId);
        if (optional.isPresent())
            return optional.get();
        else
            return null;
    }
}