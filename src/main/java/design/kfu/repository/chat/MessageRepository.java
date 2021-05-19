package design.kfu.repository.chat;

import design.kfu.entity.chat.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<ChatMessage, Long> {
    @Query("SELECT msg FROM ChatMessage msg WHERE msg.chat.id = :chatId")
    List<ChatMessage> findByChatId(String chatId);
}
