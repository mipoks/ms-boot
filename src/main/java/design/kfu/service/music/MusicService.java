package design.kfu.service.music;

import design.kfu.entity.music.Person;
import design.kfu.entity.music.Request;
import design.kfu.entity.music.Song;
import design.kfu.helper.entity.SongOwnChecker;
import design.kfu.helper.music.MusicGetter;
import design.kfu.repository.music.PersonRepository;
import design.kfu.repository.music.RequestRepository;
import design.kfu.repository.music.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class MusicService {

    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private SongOwnChecker songOwnChecker;

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private MusicGetter musicGetter;

    @Value("${request.timeout}")
    private long TIMEOUT_REQUEST;

    public Optional<Song> getMusicById(long id) {
        return songRepository.findById(id);
    }

    public Song getRandomMusic(){
        return songRepository.findOneByRandom();
    }

    public Set<Song> search(String search, Person person) throws IOException {
        int count = 5;

        Request request = requestRepository.findBySearch(search);
        Set<Song> songs;

        if (person != null) {
            person = personRepository.findByEmail(person.getEmail());
            count = person.getSongCount();
        }

        if (request != null) {
            if (request.getSongs().size() >= count) {
                if (request.getTime() > new Date().getTime() / 1000 - TIMEOUT_REQUEST) {
                    songs = request.getSongs();
                    musicGetter.update(songs);
                } else {
                    if (search.equals(""))
                        songs = musicGetter.get(count);
                    else
                        songs = musicGetter.get(count, request);
                    musicGetter.update(songs);
                    request.getSongs().addAll(songs);
                    request.setTime(new Date().getTime() / 1000);
                    requestRepository.save(request);
                }
            } else {
                request.setTime(new Date().getTime() / 1000);
                if (search.equals(""))
                    songs = musicGetter.get(count);
                else
                    songs = musicGetter.get(count, request);
                musicGetter.update(songs);

                request.getSongs().addAll(songs);
                requestRepository.save(request);
            }
        } else {
            request = new Request(search);
            request.setTime(new Date().getTime() / 1000);
            if (search.equals(""))
                songs = musicGetter.get(count);
            else
                songs = musicGetter.get(count, request);
            musicGetter.update(songs);

            request.getSongs().addAll(songs);
            System.out.println(request);
            requestRepository.save(request);
        }
        if (person != null) {
            songOwnChecker.addParamIfInSongRepo(person.getSongs(), person.getSongs());
        }
        return songs;
    }

    public Collection<Song> getPopularSongs(Person person) throws IOException {
        return search("", person);
    }

}
