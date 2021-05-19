package design.kfu.service.music;

import design.kfu.entity.music.Person;
import design.kfu.entity.music.Song;
import design.kfu.entity.dto.SongForm;
import design.kfu.helper.entity.SongOwnChecker;
import design.kfu.helper.music.MusicGetter;
import design.kfu.repository.music.PersonRepository;
import design.kfu.repository.music.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

@Service
public class PersonMusicService {

    @Autowired
    private MusicGetter musicGetter;
    @Autowired
    private SongOwnChecker songOwnChecker;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private SongRepository songRepository;

    public Set<Song> getMusic(Person person) {
        person = personRepository.findByEmail(person.getEmail());
        Set<Song> songs = person.getSongs();
        try {
            musicGetter.update(songs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        songs = songOwnChecker.addParamIfInSongRepo(songs, true);
        for (Song song : songs) {
            songRepository.save(song);
        }
        return songs;
    }

    public boolean toggleMusic(SongForm songForm, Person person) {
        return toggleMusic(songForm.getId(), person);
    }

    public boolean toggleMusic(long id, Person person) {
        Optional<Song> optional = songRepository.findById(id);
        person = personRepository.findByEmail(person.getEmail());
        if (optional.isPresent()) {
            boolean ans = false;
            if (person.hasSong(optional.get())) {
                ans = !person.removeSong(optional.get());
            } else {
                ans = person.addSong(optional.get());
            }
            personRepository.saveAndFlush(person);
            return ans;
        }
        return false;
    }

    public boolean deleteMusic(long id, Person person) {
        Optional<Song> optional = songRepository.findById(id);
        person = personRepository.findByEmail(person.getEmail());
        if (optional.isPresent()) {
            Song temp = optional.get();
            boolean ans = person.removeSong(temp);
            personRepository.saveAndFlush(person);
            return ans;
        }
        return false;
    }
}
