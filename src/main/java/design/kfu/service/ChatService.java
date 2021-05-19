package design.kfu.service;

import design.kfu.entity.chat.Chat;
import design.kfu.entity.chat.ChatMessage;
import design.kfu.entity.music.Person;

import java.util.List;

public interface ChatService {
    List<ChatMessage> getMessages(String chatId);
    Chat getChatById(String chatId);
    List<ChatMessage> getMessages(Chat chat);
    List<Chat> getAllChats(Person person);
    void sendMessage(ChatMessage message, Person person);
    List<Chat> getOpenChat();

    String getRealChatId(String smallId);
}
