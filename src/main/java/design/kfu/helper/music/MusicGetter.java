package design.kfu.helper.music;

import design.kfu.entity.music.Request;
import design.kfu.entity.music.Song;

import java.io.IOException;
import java.util.Set;

public interface MusicGetter {
    Set<Song> get(int count);
    Set<Song> get(int count, Request request);
    void update(Set<Song> collection) throws IOException;
}
