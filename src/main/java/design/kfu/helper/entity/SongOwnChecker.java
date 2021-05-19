package design.kfu.helper.entity;

import design.kfu.entity.music.Song;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

@Component
public class SongOwnChecker {

    public void addParamIfInSongRepo(Collection<Song> toFind, Collection<Song> from) {
        for (Song song : toFind) {
            song.setOwn(contains(song, from));
        }
    }

    public Set<Song> addParamIfInSongRepo(Set<Song> collection, boolean own) {
        for (Song song : collection) {
            song.setOwn(own);
        }
        return collection;
    }

    public boolean contains(Song song, Collection<Song> from) {
        for (Song songIn : from) {
            if (songIn.equals(song))
                return true;
        }
        return false;
    }
}
