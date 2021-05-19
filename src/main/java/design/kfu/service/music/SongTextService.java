package design.kfu.service.music;

import design.kfu.entity.dto.SongText;
import design.kfu.service.api.Musix;
import design.kfu.service.api.exception.MusixException;
import design.kfu.service.api.model.Lyrics;
import design.kfu.service.api.model.Track;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SongTextService {

    private final String apiKey = "de314ac2090e3c31bd66db2d9a75f235";
    private final Musix musix = new Musix(apiKey);


    public SongText getText(String songName, String artistName) {

        SongText songText = null;
        log.info("Searching " + songName + "   " + artistName);
        try {
            Track track = musix.getMatchingTrack(songName, artistName);
            Lyrics lyrics = musix.getLyrics(track.getTrack().getTrackId());

            songText = new SongText();
            songText.setName(track.getTrack().getTrackName());
            songText.setText(lyrics.getLyricsBody().substring(0, lyrics.getLyricsBody().length() - 71));
            songText.setId(Long.valueOf(track.getTrack().getTrackId()));

            log.info("Lyrics ID       : " + lyrics.getLyricsId());
            log.info("Lyrics Body     : " + lyrics.getLyricsBody());

        } catch (MusixException e) {
            e.printStackTrace();
        }
        return songText;
    }

    public SongText getText(String songName) {
        return getText(songName, "");
    }
}
