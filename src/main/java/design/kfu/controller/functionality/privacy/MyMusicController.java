package design.kfu.controller.functionality.privacy;

import design.kfu.entity.music.Person;
import design.kfu.entity.music.Song;
import design.kfu.entity.dto.SongForm;
import design.kfu.helper.response.Answer;
import design.kfu.service.PersonGiver;
import design.kfu.service.music.PersonMusicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Locale;

@Slf4j
@Controller
public class MyMusicController {

    @Autowired
    private PersonMusicService musicService;
    @Autowired
    private MessageSource messageSource;

    @GetMapping("/me")
    protected String getMyMusic(ModelMap map, Locale locale) {
        Person person = PersonGiver.get();

        String title = messageSource.getMessage("my.music.title", new Object[]{}, locale);
        map.put("title", title);
        map.put("user", person);
        Collection<Song> songs = null;
        try {
            songs = musicService.getMusic(person);
            map.put("songs", songs);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        map.put("songemptytext", "Вы ещё ничего не добавили");
        return "audio-page";
    }

    @ResponseBody
    @PostMapping("/me")
    protected Answer putSong(@RequestBody(required = false) SongForm songForm, HttpServletResponse resp) {
        log.info(songForm.toString());
        Person person = PersonGiver.get();
        Answer answer = new Answer();
        if (musicService.toggleMusic(songForm, person)) {
            resp.setStatus(202);
            answer.setInfo("Добавлено");
        } else {
            resp.setStatus(203);
            answer.setInfo("Удалено");
        }
        return answer;
    }

    @ResponseBody
    @DeleteMapping("/me/{id}")
    protected Answer deleteSong(@PathVariable long id) {
        Person person = PersonGiver.get();
        Answer answer = new Answer();
        musicService.deleteMusic(id, person);
        answer.setInfo("Удалено");
        return answer;
    }
}
