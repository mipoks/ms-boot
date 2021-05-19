package design.kfu.controller.functionality.publicity;

import design.kfu.entity.music.Person;
import design.kfu.entity.music.Song;
import design.kfu.service.music.MusicService;
import design.kfu.service.PersonGiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Locale;

@Controller
public class PopularController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private MusicService searchService;

    @GetMapping("/actual")
    public String getPopular(ModelMap modelMap, Locale locale) {
        Person person = PersonGiver.get();
        modelMap.put("user", person);

        String title = messageSource.getMessage("popular.title", new Object[]{}, locale);
        modelMap.put("title", title);
        Collection<Song> songs = null;
        try {
            songs = searchService.getPopularSongs(person);
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("songemptytext", "Хьюстон, у нас проблемы, так как ничего не нашлось");
        }
        modelMap.put("songs", songs);
        return "audio-page";
    }
}
