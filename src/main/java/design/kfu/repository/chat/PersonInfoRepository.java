package design.kfu.repository.chat;

import design.kfu.entity.chat.PersonInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonInfoRepository extends JpaRepository<PersonInfo, Long> {
    @Query("SELECT pInfo FROM PersonInfo pInfo WHERE pInfo.person.id = :personId")
    Optional<PersonInfo> findByPersonId(Long personId);
}
