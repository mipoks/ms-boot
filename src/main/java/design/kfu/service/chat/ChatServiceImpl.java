package design.kfu.service.chat;

import design.kfu.entity.chat.Chat;
import design.kfu.entity.chat.ChatMessage;
import design.kfu.entity.music.Person;
import design.kfu.repository.chat.ChatRepository;
import design.kfu.repository.chat.MessageRepository;
import design.kfu.repository.music.PersonRepository;
import design.kfu.service.ChatService;
import design.kfu.service.PersonGiver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private PersonRepository personRepository;

    @Override
    public List<ChatMessage> getMessages(String chatId) {
        Optional<Chat> chatOptional = chatRepository.findById(chatId);
        if (chatOptional.isPresent()) {
            return messageRepository.findByChatId(chatId);
        } else {
            return null;
        }
    }

    @Override
    public Chat getChatById(String chatId) {
        Optional<Chat> chatOptional = chatRepository.findById(chatId);
        return chatOptional.orElse(null);
    }

    @Override
    public List<ChatMessage> getMessages(Chat chat) {
        if (chat != null)
            return messageRepository.findByChatId(chat.getId());
        return null;
    }

    @Override
    public List<Chat> getAllChats(Person person) {
        return chatRepository.findByPersonId(person.getId());
    }

    @Override
    public void sendMessage(ChatMessage chatMessage, Person person) {
        if (person.getId() != chatMessage.getSenderId()) {
            log.info("stopped handling msg");
            return;
        }
        String chatId = chatMessage.getChatId();
        log.info("CHAT ID: " + chatId);
        Optional<Chat> optionalChat = chatRepository.findById(chatId);

        if (optionalChat.isPresent()) {
            Chat chat = optionalChat.get();
            chatMessage.setChat(chat);
            log.info("CHAT IS : " + chat.toString());
            if (chat.getUsers().contains(person)) {
                log.info("CHAT CONTAINS " + chat.getUsers().size() + " Persons");
                chatMessage.setSenderName(person.getName());
                ChatMessage msg = chatMessageService.save(chatMessage);

                log.info("MSG : " + msg);
                for (Person p : chat.getUsers()) {
                    log.info("Sending msg");
                    messagingTemplate.convertAndSendToUser(
                            String.valueOf(p.getId()), "/queue/messages", msg);
                }
            }
        }
    }

    @Override
    public List<Chat> getOpenChat() {
        return null;
    }

    @Override
    public String getRealChatId(String smallId) {
        log.info("Chat id before format: " + smallId);
        Person person = PersonGiver.get();
        if (smallId.contains("_")) {
            log.info(person.toString());
            log.info(String.valueOf(person.getId()));
            String delId = String.valueOf(person.getId());
            smallId = smallId.replace(delId, "").replace("_", "");
        }
        String chatId = makeId(String.valueOf(person.getId()), smallId);
        Optional<Chat> chatOptional = chatRepository.findById(chatId);
        Chat chat = null;
        if (chatOptional.isEmpty()) {
            Optional<Person> optionalPerson = personRepository.findById(Long.parseLong(smallId));
            if (optionalPerson.isPresent()) {
                chat = new Chat();
                log.info("Creating new chat");
                chat.setUsers(Set.of(person, optionalPerson.get()));
                chat.setId(chatId);
                chat = chatRepository.save(chat);
            }
        } else {
            chat = chatOptional.get();
        }
        return chat == null ? null : chat.getId();
    }

    private String makeId(String id1, String id2) {
        StringBuilder stringBuilder = new StringBuilder();
        if (id1.compareTo(id2) > 0) {
            stringBuilder.append(id1).append("_").append(id2);
        } else {
            stringBuilder.append(id2).append("_").append(id1);
        }
        return stringBuilder.toString();
    }
}
