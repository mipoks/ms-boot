package design.kfu.controller.functionality.publicity;

import design.kfu.entity.music.Person;
import design.kfu.entity.music.Song;
import design.kfu.service.music.MusicService;
import design.kfu.service.PersonGiver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.Locale;

@Slf4j
@Controller
@PropertySource("classpath:i18n/messages.properties")
public class SearchController {

    @Autowired
    private MusicService searchService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping({"/search", "/"})
    protected String search(ModelMap map, @RequestParam(required = false) String search, Locale locale) {
        Person person = PersonGiver.get();

        String title = messageSource.getMessage("search.title", new Object[]{}, locale);
        map.put("title", title);
        map.put("user", person);

        if (search == null) {
            Song music = searchService.getRandomMusic();
            if (music != null)
                map.put("somemusic", music.getSongName());
            return "search";
        } else {
            Collection<Song> songs = null;
            try {
                songs = searchService.search(search, person);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            map.put("songemptytext", "Ничего не нашлось");
            map.put("songs", songs);
            return "audio-page";
        }
    }

}
