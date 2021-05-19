package design.kfu.repository.music;

import design.kfu.entity.music.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    @Query(nativeQuery=true, value="SELECT * FROM songs ORDER BY random() LIMIT 1")
    Song findOneByRandom();
}
