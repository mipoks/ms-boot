package design.kfu.repository.chat;

import design.kfu.entity.chat.Chat;
import design.kfu.entity.music.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, String> {
    @Query(nativeQuery = true,
            value = "SELECT * FROM chat_persons LEFT JOIN chat ON chat.id = chat_persons.chat_id WHERE chat_persons.users_id = :personId")
    List<Chat> findByPersonId(long personId);
}
