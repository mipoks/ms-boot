package design.kfu.helper.music.implementation.v1;


import com.fasterxml.jackson.databind.ObjectMapper;
import design.kfu.entity.music.Request;
import design.kfu.entity.music.Song;
import design.kfu.helper.music.MusicGetter;
import design.kfu.repository.music.SongRepository;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.*;

@Slf4j
@Component
public class GetZaycevMusic implements MusicGetter {
    @Autowired
    private SongRepository songRepository;
    @Value("${request.timeout}")
    private long TIMEOUT_SONG;
    private final static String urlMusic = "https://zaycev.net";

    private Set<Song> getDoc(String uri, int count) {
        log.info("Connecting to Zaycev Net");
        try {
            Document doc = Jsoup.connect(urlMusic + "/" + uri).get();
            Elements elements = doc.getElementsByAttribute("data-url");
            Set<Song> songs = new LinkedHashSet<>();
            int num = 0;
            for (Element elem : elements) {
                String dataurl = elem.attributes().get("data-url");
                log.info(dataurl);
                log.info(urlMusic + "/" + uri);
                if (dataurl.length() > 5 && dataurl.substring(dataurl.length() - 5).contains("json")) {
                    ObjectMapper mapper = new ObjectMapper();
                    Song songTemp = mapper.readValue(new URL(urlMusic + dataurl), Song.class);
                    songTemp.setSongName(elem.text().substring(0, elem.text().length() - 6));
                    songTemp.setOriginalUrl(dataurl);
                    System.out.println(songTemp);
                    songs.add(songTemp);
                    num++;
                }
                if (num == count)
                    break;
            }
            update(songs);
            return songs;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Set<Song> list) throws IOException {
        Iterator<Song> it = list.iterator();
        List<Song> toRemove = new ArrayList<>();
        List<Song> toAdd = new ArrayList<>();
        while (it.hasNext()) {
            Song oldSong = it.next();
            Optional<Song> optionalSong = songRepository.findById(oldSong.getId());
            if (optionalSong.isPresent()) {
                Song tempSong = optionalSong.get();
                toRemove.add(oldSong);
                toAdd.add(tempSong);
            }
        }

        list.removeAll(toRemove);
        list.addAll(toAdd);
        it = list.iterator();
        while (it.hasNext()) {
            Song oldSong = it.next();
            if (oldSong.getTime() < new Date().getTime() / 1000 - TIMEOUT_SONG) {
                ObjectMapper mapper = new ObjectMapper();
                Song songTemp = mapper.readValue(new URL(urlMusic + oldSong.getOriginalUrl()), Song.class);
                System.out.println(oldSong.getOriginalUrl() + "     " + songTemp.getUrl());
                oldSong.setTime(new Date().getTime() / 1000);
                oldSong.setUrl(songTemp.getUrl());
            }
        }
    }

    public Set<Song> get(int count, Request request) {
        return getDoc("search.html?query_search=" + request.getSearch(), count);
    }

    public Set<Song> get(int count) {
        return getDoc("", count);
    }

}
