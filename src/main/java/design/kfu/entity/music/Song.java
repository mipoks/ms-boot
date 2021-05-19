package design.kfu.entity.music;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "songs")
public class Song {

    @Id
    private long id;

    @Column(columnDefinition="TEXT")
    private String url;

    @Transient
    private String salt;

    @Column(name = "original_url", columnDefinition="TEXT")
    private String originalUrl;

    @Transient
    private long duration;
    @Transient
    private boolean own = false;

    @Column(name = "updated")
    private long time = new Date().getTime() / 1000;

    @Column(name = "name", columnDefinition="TEXT")
    private String songName;

    public Song(String salt, String url, int id) {
        this.salt = salt;
        this.url = url;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Song song = (Song) o;
        return id == song.id;
    }

    @Override
    public int hashCode() {
        return 31 * (int) id;
    }

}
