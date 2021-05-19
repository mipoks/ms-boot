package design.kfu.entity.music;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "persons")
public class Person {
    private String name, email, password;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "song_count")
    private int songCount = 5;

    public Person(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Person(String name, String email, String password, int id) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.id = id;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.REFRESH)
    private Set<Song> songs = new LinkedHashSet<>();

    public boolean hasSong(Song song) {
        return songs.contains(song);
    }

    public boolean removeSong(Song song) {
        return songs.remove(song);
    }

    public boolean addSong(Song song) {
        return songs.add(song);
    }
}
