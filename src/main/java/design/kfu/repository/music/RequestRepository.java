package design.kfu.repository.music;

import design.kfu.entity.music.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    Request findBySearch(String search);
}
