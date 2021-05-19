package design.kfu.controller.functionality.privacy;

import design.kfu.entity.chat.Chat;
import design.kfu.entity.chat.ChatMessage;
import design.kfu.entity.dto.ChatForm;
import design.kfu.entity.dto.PersonForm;
import design.kfu.entity.music.Person;
import design.kfu.config.security.details.UserDetailsImpl;
import design.kfu.service.ChatService;
import design.kfu.service.PersonGiver;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/chat")
    public String getChatTemplate(ModelMap modelMap) {
        Person person = PersonGiver.get();
        modelMap.put("user", person);
        return "chat";
    }

    @GetMapping("/chat/{chatIdSmall}")
    public String getChat(ModelMap modelMap, @PathVariable String chatIdSmall, @PathVariable("chatIdSmall") Chat chat) {
        String chatId = chat.getId();
        Person person = PersonGiver.get();
        List<ChatMessage> messageList = chatService.getMessages(chat);

        modelMap.put("chatId", chatId);
        modelMap.put("messages", messageList);
        modelMap.put("user", person);
        modelMap.put("recipientId", chatIdSmall);
        return "chatWith";
    }

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage, Message message) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        Principal userPrincipal = headerAccessor.getUser();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                (UsernamePasswordAuthenticationToken) userPrincipal;
        UserDetailsImpl temp = (UserDetailsImpl) usernamePasswordAuthenticationToken.getPrincipal();
        Person person = temp.getUser();
        log.info("Person: " + person);
        log.info("ChatMsg: " + chatMessage);
        chatService.sendMessage(chatMessage, person);
    }

    @ResponseBody
    @GetMapping("/interlocutors")
    public List<ChatForm> getAllChats() {
        Person person = PersonGiver.get();
        List<Chat> chats = chatService.getAllChats(person);
        List<ChatForm> chatForms = new ArrayList<>();
        for (Chat chat : chats) {
            chatForms.add(ChatForm.builder().id(chat.getId()).chatName(chat.generateName()).build());
        }
        return chatForms;
    }

}

