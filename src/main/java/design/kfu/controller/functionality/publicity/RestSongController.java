package design.kfu.controller.functionality.publicity;

import design.kfu.entity.music.Person;
import design.kfu.entity.music.Song;
import design.kfu.entity.dto.SongText;
import design.kfu.helper.response.Answer;
import design.kfu.repository.music.SongRepository;
import design.kfu.service.PersonGiver;
import design.kfu.service.music.MusicService;
import design.kfu.service.music.PersonMusicService;
import design.kfu.service.music.SongTextService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.Set;

@Slf4j
@org.springframework.web.bind.annotation.RestController
public class RestSongController {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private SongTextService songTextService;

    @Autowired
    private MusicService musicService;

    @Autowired
    private PersonMusicService personMusicService;

    @GetMapping("/song/text/{id}")
    public SongText getSongText(@PathVariable long id, HttpServletResponse response) {
        SongText songText = null;
        Optional<Song> optionalSong = songRepository.findById(id);
        if (optionalSong.isPresent()) {
            Song song = optionalSong.get();
            try {
                String songName = song.getSongName().split("–")[1];
                String artistName = song.getSongName().split("–")[0];
                songName = songName.substring(1);
                log.info("Song Name for search: " + songName);
                songText = songTextService.getText(songName, artistName);
            } catch (Exception ex) {
                String songName = song.getSongName().split("–")[0];
                songText = songTextService.getText(songName);
            }
        } else {
            log.info("Song with id " + id + " not found");
            response.setStatus(404);
        }
        return songText;
    }

    @Secured("ROLE_USER")
    @PostMapping("/song/{id}")
    protected Answer putSong(@PathVariable long id, HttpServletResponse resp) {
        Person person = PersonGiver.get();
        Answer answer = new Answer();
        if (personMusicService.toggleMusic(id, person)) {
            resp.setStatus(202);
            answer.setInfo("Добавлено");
        } else {
            resp.setStatus(203);
            answer.setInfo("Удалено");
        }
        return answer;
    }


    @Secured("ROLE_USER")
    @DeleteMapping("/song/{id}")
    protected Answer deleteSong(@PathVariable long id) {
        Person person = PersonGiver.get();
        Answer answer = new Answer();
        personMusicService.deleteMusic(id, person);
        answer.setInfo("Удалено");
        return answer;
    }

    @Secured("ROLE_USER")
    @GetMapping("/song")
    protected Set<Song> getSongs() {
        Person person = PersonGiver.get();
        return personMusicService.getMusic(person);
    }

    @GetMapping("/song/{id}")
    protected Song getSong(@PathVariable long id, HttpServletResponse response) {
        Optional<Song> optionalSong = musicService.getMusicById(id);
        if (optionalSong.isEmpty()) {
            response.setStatus(404);
        }
        return optionalSong.orElse(null);
    }
}
